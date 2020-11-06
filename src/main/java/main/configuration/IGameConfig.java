package main.configuration;

import game.Direction;
import game.element.Cell;

public interface IGameConfig {

  int getBoardWidth();

  int getBoardHeight();

  void setBoardWidth(int boardWidth);

  void setBoardHeight(int boardHeight);

  int getInitialSnakeSize();

  Direction getInitialDirection();

  Cell getInitialStartingPosition();
}
