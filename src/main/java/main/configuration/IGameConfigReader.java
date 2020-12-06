package main.configuration;

import game.Cell;
import game.Direction;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the Game class.
 */
public interface IGameConfigReader {

  /**
   * This method allows access to all methods provided in the IGameConfigReader interface.
   * @return IGameConfigReader
   */
  static IGameConfigReader getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the current board width.
   * @return the board width
   */
  int getBoardWidth();

  /**
   * Returns the current board height.
   * @return the board height
   */
  int getBoardHeight();

  /**
   * Gets number of segments the Snake should be initialized with.
   * @return the size of the initial Snake
   */
  int getInitialSnakeSize();

  /**
   * Gets the initial direction the Snake will move towards to.
   * @return the direction to-go-to
   */
  Direction getInitialDirection();

  /**
   * Gets the initial starting coordinates for a new Snake (head).
   * @return the initial starting coordinates
   */
  Cell getInitialStartingPosition();
}
