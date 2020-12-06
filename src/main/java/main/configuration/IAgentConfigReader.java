package main.configuration;

import game.Direction;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * is used in the Agent class.
 */
public interface IAgentConfigReader {

  /**
   * This method allows access to all methods provided in the IAgentConfigReader interface.
   *
   * @return IAgentConfigReader
   */
  static IAgentConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Gets the initial direction the Snake will move towards to.
   *
   * @return the direction to-go-to
   */
  Direction getInitialDirection();

  /**
   * Returns the layer configuration as immutable integer array.
   *
   * @return the layer configuration
   */
  int[] getLayerConfiguration();

  /**
   * Gets the currently set randomization rate.
   *
   * @return the randomization rate
   */
  double getRandomizationRate();

}
