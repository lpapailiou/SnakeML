package ai;

import static org.junit.Assert.*;

import ai.data.BatchEntity;
import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import main.configuration.ITestConfig;
import main.configuration.Mode;
import org.junit.Test;

public class GameBatchTest {

  @Test
  public void integrationTest() {
    // Setup
    ITestConfig config = ITestConfig.getInstance();
    config.setMode(Mode.NEURAL_NETWORK);
    config.setBoardWidth(16);
    config.setBoardHeight(16);
    config.setGenerationCount(1000);
    config.setPopulationSize(2000);

    GameBatch batch = new GameBatch(new NeuralNetwork(0.6,12,16,4));
    BatchEntity batchEntity = batch.getBatchEntity();

    // run & test
    batch.processNewGeneration();

    assertEquals(1, batchEntity.getGenerations().size());

    GenerationEntity generationEntity = batchEntity.getGenerations().get(0);

    assertTrue(generationEntity.getMinSnakeLength() > 0);
    assertTrue(generationEntity.getAvgSnakeLength() > 0);
    assertTrue(generationEntity.getMaxSnakeLength() > 0);

    assertTrue(generationEntity.getAvgSteps() > 0);
    assertTrue(generationEntity.getMaxSteps() > 0);

    assertTrue(generationEntity.getNumberOfWallDeaths() > 0);
    assertTrue(generationEntity.getNumberOfBodyDeaths() > 0);

    assertTrue(generationEntity.getMinFitness() > 0);
    assertTrue(generationEntity.getAvgFitness() > 0);
    assertTrue(generationEntity.getMaxFitness() > 0);
  }
}
