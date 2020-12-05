package main.configuration;

import game.Direction;

public interface IAgentConfigReader {

  static IAgentConfigReader getInstance() {
    return Config.getInstance();
  }

  Direction getInitialDirection();

  int[] getLayerConfiguration();

  double getRandomizationRate();

}
