package main.configuration;

import game.Direction;
import game.Cell;

public interface IGameConfigReader {

  int getBoardWidth();

  int getBoardHeight();

  int getInitialSnakeSize();

  Direction getInitialDirection();

  Cell getInitialStartingPosition();
}
