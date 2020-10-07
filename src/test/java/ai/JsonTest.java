package ai;

import ai.neuralnet.NeuralNetwork;
import org.junit.Test;

public class JsonTest {


  @Test
  public void jsonTest() {
    GameBatch batch = new GameBatch(5,20,new NeuralNetwork(0.999,12,16,16,4));
    batch.run();
    System.out.println(batch.getJsonData());
  }

  @Test
  public void integrationTest() {
    GameBatch batch = new GameBatch(60,1000,new NeuralNetwork(0.999,12,16,16,4));
    //batch.run();
  }
}
