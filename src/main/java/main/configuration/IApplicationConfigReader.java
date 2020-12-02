package main.configuration;

import game.Direction;

public interface IApplicationConfigReader {

  static IApplicationConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  Theme getTheme();

  Mode getMode();

  Direction getInitialDirection();

  int[] getLayerConfiguration();

  double getRandomizationRate();

}
