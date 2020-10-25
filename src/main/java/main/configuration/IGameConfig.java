package main.configuration;

import game.Direction;
import game.element.Cell;

public interface IGameConfig {

  int getBoardWidth();

  int getBoardHeight();

  void setBoardWith(int boardWith);

  void setBoardHeight(int boardHeight);

  double getCellWidth();

  double getManualSpeedFactor();

  double getBotSpeedFactor();

  int getInitialSnakeSize();

  Direction getInitialDirection();

  Cell getInitialStartingPosition();
}
