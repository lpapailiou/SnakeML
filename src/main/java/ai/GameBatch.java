package ai;

import ai.data.BatchEntity;
import ai.data.ConfigurationEntity;
import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.configuration.IGameBatchConfigReader;

/**
 * This class is the 'outer loop' of processing the machine learning algorithm. It allows to process
 * batches of Generations coordinated and stepwise, so the intermediate data can be used.
 * Additionally, it coordinates the collections of statistics about the processed games.
 */
public class GameBatch {

  private final int generationCount;
  private int currentGeneration;
  private final int populationSize;
  private final BatchEntity batchEntity = new BatchEntity();
  private final List<GenerationEntity> generationEntities = new ArrayList<>();
  private final ConfigurationEntity configurationEntity;
  private NeuralNetwork neuralNetwork;

  /**
   * The constructor takes the NeuralNetwork, which will be the seed for the generations to-be-run.
   * During the initialization, it will record the currently used algorithm and include it to the
   * according statistics record.
   *
   * @param neuralNetwork the NeuralNetwork to be seeded. Must not be null
   */
  public GameBatch(NeuralNetwork neuralNetwork) {
    if (neuralNetwork == null) {
      throw new IllegalArgumentException("The neural network must not be null");
    }
    IGameBatchConfigReader config = IGameBatchConfigReader.getInstance();
    this.generationCount = config.getGenerationCount();
    this.populationSize = config.getPopulationSize();
    this.neuralNetwork = neuralNetwork;
    configurationEntity = new ConfigurationEntity();
    configurationEntity.setBoardWidth(config.getBoardWidth());
    configurationEntity.setBoardHeight(config.getBoardHeight());
    configurationEntity.setPopulationSize(populationSize);

    String algorithmDescription = String.format(
        "Neural Network; randomization rate: %s; layer configuration: %s",
        config.getRandomizationRate(),
        Arrays.toString(config.getLayerConfiguration())
    );
    configurationEntity.setAlgorithm(algorithmDescription);
  }

  /**
   * This method triggers a single run of a generation. Additionally, it will collect the according
   * data.
   *
   * @return the NeuralNetwork which is considered best for reproduction for the next generation or
   * null, if the generation count has been reached.
   */
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

  /**
   * This method extracts the aggregated data of the current generation.
   *
   * @return aggregated data of current generation
   */
  public GenerationEntity getCurrentGenerationEntity() {
    return generationEntities.isEmpty() ? null
        : generationEntities.get(generationEntities.size() - 1);
  }

  /**
   * This method returns the wrapped statistics data for this batch.
   *
   * @return the wrapped statistics data of this instance
   */
  public BatchEntity getBatchEntity() {
    return batchEntity;
  }
}
