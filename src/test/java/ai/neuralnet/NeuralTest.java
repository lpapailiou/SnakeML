package ai.neuralnet;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

public class NeuralTest {

  @Test
  public void blindTest() {
    NeuralNetwork net = new NeuralNetwork(3, 4, 7, 2);
    List<Double> out = net.predict(new double[]{1, 2, 3});

    assertTrue(out.get(0) > 0 && out.get(0) < 1);
    assertTrue(out.get(1) > 0 && out.get(1) < 1);
  }

// *********************** commented out because not in used in this project ***********************
//    @Test
//    public void xOrTest() {
//        double[][] in = {{0,0}, {1,0}, {0,1}, {1,1}};
//        double[][] out = {{0}, {1}, {1}, {0}};
//        NeuralNetwork net = new NeuralNetwork(2, 15, 15, 1);
//        net.train(in, out, 4000);
//
//        for (int i = 0; i < 4; i++) {
//          double val = net.predict(in[i]).get(0);
//          double expected = val < 0.5 ? 0.0 : 1.0;
//          assertEquals(expected, val, 0.1);
//        }
//    }
}
