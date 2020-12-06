package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the Snake class.
 */
public interface ISnakeConfigReader {

  /**
   * This method allows access to all methods provided in the ISnakeConfigReader interface.
   * @return ISnakeConfigReader
   */
  static ISnakeConfigReader getInstance() {
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
   * Returns the timeout for the snake. If the steps of the snake exceed this number, the according game will be terminated.
   * This allows to avoid infinite loops in automated games.
   * @return the timeout for snake steps
   */
  int getSnakeTimeout();

}
