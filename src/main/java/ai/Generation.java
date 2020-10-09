package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Generation {

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

  public List<GameAdapter> getPopulationList() {    // could be used to visualize running game
    return new ArrayList<>(populationList);
  }

  private NeuralNetwork evolve() {
    final int bound = 3;
    Random random = new Random();

    populationList.sort(Comparator.nullsLast(Collections.reverseOrder()));

    NeuralNetwork best = populationList.get(0).getNeuralNetwork();

    System.out.println("max fitness for gen #" + generationEntity.getId() + ": \t" + populationList.get(0).getFitness() + " \t(snake length: " + populationList.get(0).getSnakeLength()+")");

    if (populationList.size() > 1) {
      best = NeuralNetwork.merge(best, populationList.get(1)
          .getNeuralNetwork());     // best option at the moment, below alternative would be kind of nicer though
    }

    /*  // TODO: improve or remove
    // merge together some of the top scorers
    for (int i = 0; i < bound; i++) {
      best = NeuralNetwork.merge(best, populationList.get(random.nextInt(bound)).getNeuralNetwork());
    }

    // seed in some random scorers to break 'eaten in' patterns and avoid local maima
    for (int i = 0; i < bound/2; i++) {
      best = NeuralNetwork.merge(best, populationList.get(random.nextInt(populationSize)).getNeuralNetwork());
    }
    */

    return best;
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
      while (running) {
        running = adapter.moveSnake();
      }
    }
  }
}
