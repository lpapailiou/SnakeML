package ai;

import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.ITestConfig;
import main.configuration.Mode;
import org.junit.Test;
import java.util.ArrayList;

public class GenerationTest {

  @Test
  public void generationTest() {
    ITestConfig config = Config.getTestConfig();
    config.setMode(Mode.NEURAL_NETWORK);
    NeuralNetwork net = new NeuralNetwork(12,8,8,4);
    Generation gen = new Generation(1,100, new ArrayList<>());
    gen.run(net);
    System.out.println("the end");
  }
}
