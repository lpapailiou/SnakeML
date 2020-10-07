package ai;

import ai.data.storage.JsonFileHandler;
import ai.neuralnet.NeuralNetwork;
import org.junit.Test;

public class JsonTest {


  @Test
  public void jsonTest() {
    GameBatch batch = new GameBatch(10,10,new NeuralNetwork(0.999,12,32,32,32,4));
    batch.run();
    //System.out.println(batch.getJsonString());
    batch.saveJsonData();
  }

  @Test
  public void integrationTest() {
    GameBatch batch = new GameBatch(60,1000,new NeuralNetwork(0.999,12,32,32,32,4));
    //batch.run();
  }
}
