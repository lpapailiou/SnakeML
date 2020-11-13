package ai;

import ai.data.BatchEntity;
import ai.data.ConfigurationEntity;
import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBatch {

  private INeuralNetworkConfig config = Config.getInstance();
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


//  public void run() {
//    batchEntity.setConfigurationEntity(configurationEntity);
//    batchEntity.setGenerationEntities(generationEntities);
//
//
//    for (int i = 0; i < generationCount; i++) {
//      Generation gen = new Generation(i, populationnSize, generationEntities);
//      neuralNetwork = gen.run(neuralNetwork);
//    }
//    saveJsonData();
//  }

  public int getCurrentGeneration() {
    return currentGeneration;
  }

  public NeuralNetwork processGeneration() {
    Generation gen = new Generation(currentGeneration, populationSize, generationEntities);
    neuralNetwork = gen.run(neuralNetwork);
    batchEntity.setConfigurationEntity(configurationEntity);
    batchEntity.setGenerationEntities(generationEntities);
    if (currentGeneration == generationCount) {
//      saveJsonData();
      return null;
    }
    currentGeneration++;
    return neuralNetwork;
  }

  public GenerationEntity getCurrentGenerationEntity() {
    return generationEntities.isEmpty() ? null : generationEntities.get(generationEntities.size()-1);
  }

//  public void generateJson() {
//    ObjectMapper mapper = new ObjectMapper();
//    try {
//      jsonData = mapper.writeValueAsString(batchEntity);
//    } catch (JsonProcessingException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public String getJsonString() {
//    if (jsonData == null) {
//      generateJson();
//    }
//    return jsonData;
//  }

//  public void saveJsonData() {
//    JsonFileHandler jfh = new JsonFileHandler(getJsonString());
//    jfh.saveToTempStorage();
//  }


  public BatchEntity getBatchEntity() {
    return batchEntity;
  }
}
