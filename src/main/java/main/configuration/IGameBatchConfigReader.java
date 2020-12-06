package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the GameBatch class.
 */
public interface IGameBatchConfigReader {

  /**
   * This method allows access to all methods provided in the IGameBatchConfigReader interface.
   * @return IGameBatchConfigReader
   */
  static IGameBatchConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the current board width.
   * @return the board width
   */
  int getBoardWidth();

  /**
   * Returns the current board height.
   * @return the board height
   */
  int getBoardHeight();

  /**
   * Returns the layer configuration as immutable integer array.
   * @return the layer configuration
   */
  int[] getLayerConfiguration();

  /**
   * Gets the currently set generation count.
   * @return the generation count.
   */
  int getGenerationCount();

  /**
   * Gets the currently set population size.
   * @return the population size
   */
  int getPopulationSize();

  /**
   * Gets the currently set randomization rate.
   * @return the randomization rate
   */
  double getRandomizationRate();

}
