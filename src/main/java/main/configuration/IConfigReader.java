package main.configuration;

import java.util.List;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the ConfigController class for reading access.
 */
public interface IConfigReader {

  /**
   * This method allows access to all methods provided in the IConfigReader interface.
   * @return IConfigReader
   */
  static IConfigReader getInstance() {
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
   * Provides the currently selected theme.
   * @return the current theme
   */
  Theme getTheme();

  /**
   * Returns the currently selected mode (in terms of game logic).
   * @return the selected game mode
   */
  Mode getMode();

  /**
   * Returns the layer configuration as immutable integer array.
   * @return the layer configuration
   */
  int[] getLayerConfiguration();

  /**
   * Returns the layer configuration as Integer List.
   * @return the layer configuration
   */
  List<Integer> getLayerConfigurationAsList();

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
