package ai;

import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import org.junit.Test;

public class GameBatchTest {

  @Test
  public void integrationTest() {
    INeuralNetworkConfig config = Config.getInstance();
    int generations = config.getGenerationCount();
    int population = config.getPopulationSize();

    config.setGenerationCount(400);
    config.setPopulationSize(800);
    GameBatch batch = new GameBatch(new NeuralNetwork(0.999,12,32,32,32,4));
    batch.run();

    config.setGenerationCount(generations);
    config.setPopulationSize(population);
  }

}
