package main.configuration;

import game.Direction;
import game.element.Cell;

public interface IGameConfigReader {

  int getBoardWidth();

  int getBoardHeight();

  int getInitialSnakeSize();

  Direction getInitialDirection();

  Cell getInitialStartingPosition();
}
