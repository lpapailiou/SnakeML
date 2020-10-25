package ai;

import ai.neuralnet.NeuralNetwork;
import org.junit.Test;
import java.util.ArrayList;

public class GenerationTest {

  @Test
  public void generationTest() {
    NeuralNetwork net = new NeuralNetwork(12,8,8,4);
    Generation gen = new Generation(1,100, new ArrayList<>());
    gen.run(net);
    System.out.println("the end");
  }
}
