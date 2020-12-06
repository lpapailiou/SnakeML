//package ai;
//
//import ai.neuralnet.NeuralNetwork;
//import main.configuration.ITestConfig;
//import main.configuration.Mode;
//import org.junit.Test;
//
//public class GameBatchTest {
//
//  @Test
//  public void integrationTest() {
//    ITestConfig config = ITestConfig.getInstance();
//    int generations = config.getGenerationCount();
//    int population = config.getPopulationSize();
//    config.setMode(Mode.NEURAL_NETWORK);
//    config.setBoardWidth(16);
//    config.setBoardHeight(16);
//    config.setGenerationCount(1000);
//    config.setPopulationSize(2000);
//    GameBatch batch = new GameBatch(new NeuralNetwork(0.6,12,16,4));
////    batch.run();
//
//    /*
//    generations: 1000
//    population : 800
//    net: new GameBatch(new NeuralNetwork(0.6,12,32,32,4));
//    max fitness for gen #0: 	314 	(snake length: 3)
//    max fitness for gen #21: 	374232 	(snake length: 32)
//    max fitness for gen #213: 	6719464 	(snake length: 70)
//    max fitness for gen #231: 	17729424 	(snake length: 91)
//    ----------------------------------------------> perfect game!!!
//    max fitness for gen #243: 	813761003 	(snake length: 256)
//    from gen #310: perfect games only
//     */
//
//    config.setGenerationCount(generations);
//    config.setPopulationSize(population);
//  }
//
//}
