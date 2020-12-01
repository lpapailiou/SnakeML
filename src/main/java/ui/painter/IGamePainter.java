package ui.painter;

import game.Cell;
import game.Snake;

public interface IGamePainter {

  void paintBoard(boolean isActive);

  void paintFood(Cell foodPosition);

  void paintSnake(Snake snake);

}
