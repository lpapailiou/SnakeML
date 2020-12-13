package ai;

import ai.neuralnet.NeuralNetwork;
import main.configuration.ITestConfig;
import main.configuration.Mode;
import org.junit.Test;

public class JsonTest {


  @Test
  public void jsonTest() {
    ITestConfig config = ITestConfig.getInstance();
    int generations = config.getGenerationCount();
    int population = config.getPopulationSize();
    config.setMode(Mode.NEURAL_NETWORK);
    config.setBoardWidth(12);
    config.setBoardHeight(12);
    config.setGenerationCount(10);
    config.setPopulationSize(10);
    GameBatch batch = new GameBatch(new NeuralNetwork(0.999,12,32,32,32,4));

    config.setGenerationCount(generations);
    config.setPopulationSize(population);
  }

}
