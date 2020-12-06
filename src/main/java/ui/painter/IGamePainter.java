package ui.painter;

import game.Cell;
import game.Snake;

/**
 * This interface provides all methods to visualize a game on the user interface.
 */
public interface IGamePainter {

  /**
   * This method triggers the visualisation of the game board.
   */
  void paintBoard(boolean isActive);

  /**
   * This method triggers the visualisation of the food or 'apple'.
   */
  void paintFood(Cell foodPosition);

  /**
   * This method triggers the visualisation of the snake.
   */
  void paintSnake(Snake snake);

}
