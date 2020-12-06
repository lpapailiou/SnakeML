//package ai.neuralnet;
//
//import java.util.List;
//
//import org.junit.Test;
//
//public class NeuralTest {
//
//// *********************** commented out because not in used in this project ***********************
////    @Test
////    public void xOrTest() {
////        // testing xor function
////        double[][] in = {{0,0}, {1,0}, {0,1}, {1,1}};
////        double[][] out = {{0}, {1}, {1}, {0}};
////        NeuralNetwork net = new NeuralNetwork(2, 15, 15, 1);
////        net.train(in, out, 4000);
////
////        for (int i = 0; i < 4; i++) {
////          double val = net.predict(in[i]).get(0);
////          double expected = val < 0.5 ? 0.0 : 1.0;
////          System.out.println("testing: " + expected + " with " + val);
////          assertEquals(expected, val, 0.1);
////        }
////    }
//
//    @Test
//    public void blindTest() {
//        NeuralNetwork net = new NeuralNetwork(3, 4, 7, 2);
//        List<Double> out = net.predict(new double[] {1, 2, 3});
//        System.out.println(out);
//    }
//}
