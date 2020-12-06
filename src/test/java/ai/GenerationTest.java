//package ai;
//
//import ai.neuralnet.NeuralNetwork;
//
//import java.util.ArrayList;
//
//import main.configuration.ITestConfig;
//import main.configuration.Mode;
//import org.junit.Test;
//
//public class GenerationTest {
//
//  @Test
//  public void generationTest() {
//    ITestConfig config = ITestConfig.getInstance();
//    config.setMode(Mode.NEURAL_NETWORK);
//    NeuralNetwork net = new NeuralNetwork(12,8,8,4);
//    Generation gen = new Generation(1,100, new ArrayList<>());
//    gen.run(net);
//    System.out.println("the end");
//  }
//}
