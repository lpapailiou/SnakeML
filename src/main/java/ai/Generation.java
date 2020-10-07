package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Generation {

  private int populationSize;
  private final static int THREAD_POOL = 12;
  private HashMap<GameAdapter, Long> scoreList = new HashMap<>();
  private int winnersToMerge = 2;
  private GenerationEntity generationEntity = new GenerationEntity();
  private List<GenerationEntity> generationEntites;

  public Generation(int id, int populationSize, List<GenerationEntity> generationEntites) {
    if (populationSize < 1) {
      throw new IllegalArgumentException("minimum size of a population is 1.");
    }

    generationEntity.setId(id);
    this.populationSize = populationSize;
    this.generationEntites = generationEntites;
  }

  public NeuralNetwork run(NeuralNetwork net) {
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

    for (int i = 0; i < populationSize; i++) {

      Runnable worker = new BackgroundGame(i == 0 ? net : net.clone(), scoreList, generationEntity);
      executor.execute(worker);
    }
    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("executor service interrupted unexpectedly!");
    }

    generationEntites.add(generationEntity);
    NeuralNetwork best = getBest(scoreList);
    for (int i = 1; i < winnersToMerge; i++) {
      NeuralNetwork next = getBest(scoreList);
      if (next != null) {
        best = NeuralNetwork.merge(best, next);
      }
    }
    return best;
  }

  private NeuralNetwork getBest(HashMap<GameAdapter, Long> map) {
    if (map.isEmpty() || scoreList.isEmpty()) {
      return null;
    }
    //System.out.println(scoreList.size());
    Long max = 0l;
    try {
      max = Collections.max(scoreList.values());
    } catch (NoSuchElementException e) {
      System.out.println("no max found");   // TODO: could be avoided
    }
    Long finalMax = max;
    List<GameAdapter> ad = map.entrySet().stream().filter(e -> e.getValue().equals(finalMax)).map(Map.Entry::getKey).collect(
        Collectors.toList());
    if (!ad.isEmpty()) {
      System.out.println("fitness of generation: " + ad.get(0).getFitness() + " / snake length: " + ad.get(0).getSnakeLength());
      map.remove(ad.get(0));
    }
    return ad.isEmpty() ? null : ad.get(0).getNeuralNetwork();
  }

  static class BackgroundGame implements Runnable {
    NeuralNetwork net;
    HashMap<GameAdapter, Long> scores;
    GenerationEntity generationEntity;
    BackgroundGame(NeuralNetwork net, HashMap<GameAdapter, Long> scoreList, GenerationEntity generationEntity) {
      this.net = net;
      this.generationEntity = generationEntity;
      this.scores = scoreList;
    }

    @Override
    public void run() {
      GameAdapter adapter = new GameAdapter(net, generationEntity);
      boolean running = true;
      while (running) {
        running = adapter.moveSnake();
      }
      scores.put(adapter, adapter.getFitness());
    }
  }
}
