package main.configuration;

import game.Direction;

public interface IApplicationConfigReader {

  int getBoardWidth();

  int getBoardHeight();

  Theme getTheme();

  Mode getMode();

  Direction getInitialDirection();

  int[] getLayerConfiguration();

  double getRandomizationRate();

}
