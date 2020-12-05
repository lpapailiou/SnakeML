package ai;

import ai.data.BatchEntity;
import ai.data.ConfigurationEntity;
import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import main.configuration.IGameBatchConfigReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBatch {

  private IGameBatchConfigReader config = IGameBatchConfigReader.getInstance();
  private int generationCount;
  private int currentGeneration;
  private int populationSize;
  private BatchEntity batchEntity = new BatchEntity();
  private List<GenerationEntity> generationEntities = new ArrayList<>();
  private ConfigurationEntity configurationEntity;
  private NeuralNetwork neuralNetwork;
//  private String jsonData;

  public GameBatch(NeuralNetwork neuralNetwork) {
    this.generationCount = config.getGenerationCount();
    this.populationSize = config.getPopulationSize();
    this.neuralNetwork = neuralNetwork;
    configurationEntity = new ConfigurationEntity();
    configurationEntity.setBoardWidth(config.getBoardWidth());
    configurationEntity.setBoardHeight(config.getBoardHeight());
    String sb = "Neural Network"
        + "; randomization rate: "
        + config.getRandomizationRate()
        + "; layer configuration: "
        + Arrays.toString(config.getLayerConfiguration());
    configurationEntity.setAlgorithm(sb);
    configurationEntity.setPopulationSize(populationSize);
  }

  public NeuralNetwork processNewGeneration() {
    Generation gen = new Generation(currentGeneration, populationSize, generationEntities);
    neuralNetwork = gen.run(neuralNetwork);
    batchEntity.setConfigurationEntity(configurationEntity);
    batchEntity.setGenerationEntities(generationEntities);
    if (currentGeneration == generationCount) {
      return null;
    }
    currentGeneration++;
    return neuralNetwork;
  }

  public GenerationEntity getCurrentGenerationEntity() {
    return generationEntities.isEmpty() ? null : generationEntities.get(generationEntities.size()-1);
  }

  public BatchEntity getBatchEntity() {
    return batchEntity;
  }
}
