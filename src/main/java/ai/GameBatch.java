package ai;

import ai.data.BatchEntity;
import ai.data.ConfigurationEntity;
import ai.data.GenerationEntity;
import ai.data.storage.JsonFileHandler;
import ai.neuralnet.NeuralNetwork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBatch {

  private INeuralNetworkConfig config = Config.getInstance();
  private int generationCount;
  private int currentGeneration;
  private int populationnSize;
  private BatchEntity batchEntity = new BatchEntity();
  private List<GenerationEntity> generationEntities = new ArrayList<>();
  private ConfigurationEntity configurationEntity;
  private NeuralNetwork neuralNetwork;
  private String jsonData;

  public GameBatch(NeuralNetwork neuralNetwork) {
    this.generationCount = config.getGenerationCount();
    this.populationnSize = config.getPopulationSize();
    this.neuralNetwork = neuralNetwork;
    configurationEntity = new ConfigurationEntity();
    configurationEntity.setBoardWidth(config.getBoardWidth());
    configurationEntity.setBoardHeight(config.getBoardHeight());
    StringBuilder sb = new StringBuilder("Neural Network");
    sb.append("; randomization rate: " + config.getRandomizationRate());
    sb.append("; layer configuration: " + Arrays
        .toString(config.getLayerConfiguration()));
    configurationEntity.setAlgorithm(sb.toString());
    configurationEntity.setPopulationSize(populationnSize);
  }


  public void run() {
    for (int i = 0; i < generationCount; i++) {
      Generation gen = new Generation(i, populationnSize, generationEntities);
      neuralNetwork = gen.run(neuralNetwork);
    }
    batchEntity.setConfigurationEntity(configurationEntity);
    batchEntity.setGenerationEntities(generationEntities);
    saveJsonData();
  }

  public int getCurrentGeneration() {
    return currentGeneration;
  }

  public NeuralNetwork processGeneration() {
    Generation gen = new Generation(currentGeneration, populationnSize, generationEntities);
    neuralNetwork = gen.run(neuralNetwork);
    currentGeneration++;
    if (currentGeneration == generationCount) {
      batchEntity.setConfigurationEntity(configurationEntity);
      batchEntity.setGenerationEntities(generationEntities);
      saveJsonData();
      return null;
    }
    return neuralNetwork;
  }

  public void generateJson() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonData = mapper.writeValueAsString(batchEntity);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public String getJsonString() {
    if (jsonData == null) {
      generateJson();
    }
    return jsonData;
  }

  public void saveJsonData() {
    JsonFileHandler jfh = new JsonFileHandler(getJsonString());
    jfh.saveToTempStorage();
  }

}
