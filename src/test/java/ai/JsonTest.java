package ai;

import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import main.configuration.Mode;
import org.junit.Test;

public class JsonTest {


  @Test
  public void jsonTest() {
    INeuralNetworkConfig config = Config.getInstance();
    int generations = config.getGenerationCount();
    int population = config.getPopulationSize();
    Config.getInstance().setMode(Mode.NEURAL_NETWORK);
    Config.getInstance().setBoardWidth(12);
    Config.getInstance().setBoardHeight(12);
    config.setGenerationCount(10);
    config.setPopulationSize(10);
    GameBatch batch = new GameBatch(new NeuralNetwork(0.999,12,32,32,32,4));
//    batch.run();
//    System.out.println(batch.getJsonString().replaceAll(",", ",\n"));
//    batch.saveJsonData();

    config.setGenerationCount(generations);
    config.setPopulationSize(population);
  }

}
