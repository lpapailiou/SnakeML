package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.configuration.IGenerationConfigReader;

/**
 * This class will run multiple background games, partially in parallel. This process is one step of
 * batch processing a game series with a NeuralNetwork. Additionally, this class will evaluate
 * processed games. Based on the according ratings, it will provide an 'evolved' NeuralNetwork,
 * which can be used in further generations.
 */
class Generation {

  private static final Logger LOG = Logger.getLogger("generation logger");
  private static final int THREAD_POOL = 4;

  private IGenerationConfigReader config = IGenerationConfigReader.getInstance();
  private int populationSize;
  private List<GameAdapter> populationList = new ArrayList<>();
  private GenerationEntity generationEntity = new GenerationEntity();
  private List<GenerationEntity> generationEntities;

  /**
   * The constructor will initialize a whole generation of Snake games to be processed in the
   * background.
   *
   * @param id                the ID of the generation. As generations are played in series, the ID
   *                          will allow to track the runs
   * @param populationSize    the number of games to be processed in this generation. Must not be
   *                          below 1
   * @param generationEntites the according data wrapper class for statistics purposes
   */
  Generation(int id, int populationSize, List<GenerationEntity> generationEntites) {
    if (populationSize < 1) {
      throw new IllegalArgumentException("minimum size of a population is 1.");
    }
    generationEntity.setId(id);
    this.populationSize = populationSize;
    this.generationEntities = generationEntites;
  }

  /**
   * This method will create the required background games, then run it in parallel.
   *
   * @param net the seeded NeuralNetwork
   * @return the best NeuralNetwork chosen for reproduction
   */
  NeuralNetwork run(NeuralNetwork net) {
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

    List<Runnable> tasks = new ArrayList<>();
    for (int i = 0; i < populationSize; i++) {        // create and prepare background games
      tasks.add(new BackgroundGame(i == 0 ? net : net.getRandomizedClone(), populationList,
          generationEntity));
    }

    CompletableFuture<?>[] futures = tasks.stream()   // run background games in parallel
        .map(task -> CompletableFuture.runAsync(task, executor))
        .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
    executor.shutdown();

    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOG.log(Level.WARNING, "executor service interrupted unexpectedly!", e);
    }

    generationEntities.add(generationEntity);

    return evolve();
  }

  /**
   * This method will analyze all processed games and return a NeuralNetwork, which is designed to
   * be the best choice for reproduction for the following generation. This method is a crucial part
   * of the machine learning algorithm.
   *
   * @return the best NeuralNetwork for reproduction
   */
  private NeuralNetwork evolve() {
    populationList.sort(Comparator.nullsLast(Collections.reverseOrder()));
    NeuralNetwork best = populationList.get(0).getNeuralNetwork();

    LOG.log(Level.INFO,
            () -> String.format("max fitness for gen #%d: \t %d \t(snake length: %d)", generationEntity.getId(),
                populationList.get(0).getFitness(), populationList.get(0).getSnakeLength())
    );

    if (populationList.size() < 2) {
      return best;
    } else if (populationList.size() < 20 || populationList.get(0).getSnakeLength() < 10) {
      return NeuralNetwork.merge(best, populationList.get(1)
          .getNeuralNetwork());
    }

    // preparation for roulette wheel algorithm
    int numberOfSnakesForReproduction = 8;
    if (populationList.size() >= 100 && populationList.size() < 200) {
      numberOfSnakesForReproduction = (int) (config.getPopulationSize() * 0.2);
    } else if (populationList.size() >= 200 && populationList.size() < 1000) {
      numberOfSnakesForReproduction = (int) (config.getPopulationSize() * 0.1);
    } else if (populationList.size() >= 1000) {
      numberOfSnakesForReproduction = (int) (config.getPopulationSize() * 0.01);
    }
    int choice = 2;         // choice is the number of snakes which will be additionally selected to reproduce with the best snake

    Map<Integer, Long> map = new HashMap<>();
    double sum = 0;         // the sum of fitness values of all pre-selected snakes
    for (int i = 0; i < numberOfSnakesForReproduction; i++) {
      GameAdapter adapter = populationList.get(i);
      sum += adapter.getFitness();
      map.put(i, adapter.getFitness());   // map fitness to index
    }

    for (int i = 0; i < choice; i++) {
      best = NeuralNetwork.merge(best,
          spinRouletteWheel(populationList, map, numberOfSnakesForReproduction,
              sum));   // selection by roulette wheel algorithm
    }

    return best;
  }

  /**
   * This is the implementation of the roulette wheel algorithm, allowing to pick specific
   * NeuralNetworks for reproduction. The best rated NeuralNetworks are preferred, but there is a
   * probability lower rated NeuralNetworks are considered for reproduction, to avoid local maxima.
   *
   * @param generations the list of games-to-be-considered
   * @param map         mapping of rating to sorted index of rating
   * @param bound       the pool of Snakes to be considered for reproduction
   * @param sum         the sum of fitness values
   * @return the chosen NeuralNetwork for reproduction
   */
  private NeuralNetwork spinRouletteWheel(List<GameAdapter> generations, Map<Integer, Long> map,
      int bound, double sum) {
    long checksum = 0;
    NeuralNetwork chosen = null;
    for (int i = 0; i < bound; i++) {
      checksum += map.get(i);
      if (checksum > new Random()
          .nextInt((int) sum)) {     // double randomization! (occurs in every loop)
        chosen = generations.get(i).getNeuralNetwork();
        break;
      }
    }
    return chosen;
  }

  /**
   * This inner class will process the background games as Runnable, in order to run it in parallel.
   * Additionally, it will take care statistics data is recorded.
   */
  static class BackgroundGame implements Runnable {

    NeuralNetwork net;
    List<GameAdapter> populationList;
    GenerationEntity generationEntity;
    GameAdapter adapter;

    BackgroundGame(NeuralNetwork net, List<GameAdapter> populationList,
        GenerationEntity generationEntity) {
      this.net = net;
      this.generationEntity = generationEntity;
      this.populationList = populationList;
      adapter = new GameAdapter(net, generationEntity);
      populationList.add(adapter);
    }

    /**
     * This method processes the actual game within a thread.
     */
    @Override
    public void run() {
      while (!adapter.isGameOver()) {
        adapter.moveSnake();
      }
    }
  }
}
