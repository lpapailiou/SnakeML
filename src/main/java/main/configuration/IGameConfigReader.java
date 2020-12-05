package main.configuration;

import game.Cell;
import game.Direction;

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
