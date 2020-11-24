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

import main.configuration.Config;
import main.configuration.IGenerationConfigReader;

class Generation {

  private static final Logger LOG = Logger.getLogger("generation logger");
  private IGenerationConfigReader config = Config.getGenerationConfigReader();
  private int populationSize;
  private List<GameAdapter> populationList = new ArrayList<>();
  private final static int THREAD_POOL = 4;
  private GenerationEntity generationEntity = new GenerationEntity();
  private List<GenerationEntity> generationEntities;

  public Generation(int id, int populationSize, List<GenerationEntity> generationEntites) {
    if (populationSize < 1) {
      throw new IllegalArgumentException("minimum size of a population is 1.");
    }
    generationEntity.setId(id);
    this.populationSize = populationSize;
    this.generationEntities = generationEntites;
  }

  public NeuralNetwork run(NeuralNetwork net) {
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

    List<Runnable> tasks = new ArrayList<>();
    for (int i = 0; i < populationSize; i++) {
      tasks.add(new BackgroundGame(i == 0 ? net : net.clone(), populationList, generationEntity));
    }

    CompletableFuture<?>[] futures = tasks.stream()
        .map(task -> CompletableFuture.runAsync(task, executor))
        .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
    executor.shutdown();

    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("executor service interrupted unexpectedly!");
    }

    generationEntities.add(generationEntity);

    return evolve();
  }

  private NeuralNetwork evolve() {
    populationList.sort(Comparator.nullsLast(Collections.reverseOrder()));
    NeuralNetwork best = populationList.get(0).getNeuralNetwork();

    LOG.log(Level.INFO, "max fitness for gen #" + generationEntity.getId() + ": \t" + populationList.get(0).getFitness() + " \t(snake length: " + populationList.get(0).getSnakeLength()+")");

    //if (populationList.get(0).getSnakeLength() == config.getBoardWidth()*config.getBoardHeight()) {
      //Serializer.save(best);    // TODO: uncomment to generate new serialized NeuralNetworks
    //}

    if (populationList.size() < 2) {
      return best;
    } else if (populationList.size() < 20 || populationList.get(0).getSnakeLength() < 10) {
      return NeuralNetwork.merge(best, populationList.get(1)
          .getNeuralNetwork());
    }

    // do roulette wheel selection algorithm

    int numberOfSnakesForReproduction = 8;
    if (populationList.size() > 100 && populationList.size() < 200) {
      numberOfSnakesForReproduction = (int) (config.getPopulationSize()*0.2);
    } else if (populationList.size() > 1000) {
      numberOfSnakesForReproduction = (int) (config.getPopulationSize()*0.01);
    }
    int choice = 2;  // choice is the number of snakes which will be additionally selected to reproduce with the best snake

    Map<Integer, Long> map = new HashMap();
    double sum = 0;   // sum is the sum of fitness of all pre-selected snakes
    for (int i = 0; i < numberOfSnakesForReproduction; i++) {
      GameAdapter adapter = populationList.get(i);
      sum += adapter.getFitness();
      map.put(i, adapter.getFitness());
    }

    for (int i = 0; i < choice; i++) {
      best = NeuralNetwork.merge(best, spinRouletteWheel(populationList, map, numberOfSnakesForReproduction, sum));   // selection after roulette wheel principle
    }

    return best;
  }

  private NeuralNetwork spinRouletteWheel(List<GameAdapter> generations, Map<Integer, Long> map, int bound, double sum) {
    long checksum = 0;
    NeuralNetwork chosen = null;
    for (int i = 0; i < bound; i++) {
      checksum += map.get(i);
      if (checksum > new Random().nextInt((int) sum)) {
        chosen = generations.get(i).getNeuralNetwork();
        break;
      }
    }
    return chosen;
  }

  static class BackgroundGame implements Runnable {
    NeuralNetwork net;
    List<GameAdapter> populationList;
    GenerationEntity generationEntity;
    GameAdapter adapter;

    BackgroundGame(NeuralNetwork net, List<GameAdapter> populationList, GenerationEntity generationEntity) {
      this.net = net;
      this.generationEntity = generationEntity;
      this.populationList = populationList;
      adapter = new GameAdapter(net, generationEntity);
      populationList.add(adapter);
    }

    @Override
    public void run() {
      boolean running = true;
      while (!adapter.isGameOver()) {
        adapter.moveSnake();
      }
    }
  }
}
