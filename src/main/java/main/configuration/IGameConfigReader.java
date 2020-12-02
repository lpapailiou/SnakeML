package main.configuration;

import game.Direction;
import game.Cell;

public interface IGameConfigReader {

  static IGameConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  int getInitialSnakeSize();

  Direction getInitialDirection();

  Cell getInitialStartingPosition();
}
