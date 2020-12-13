package ai.neuralnet;

import java.util.List;

import org.junit.Test;

public class NeuralTest {

  @Test
  public void blindTest() {
    NeuralNetwork net = new NeuralNetwork(3, 4, 7, 2);
    List<Double> out = net.predict(new double[]{1, 2, 3});
    System.out.println(out);
  }
}
