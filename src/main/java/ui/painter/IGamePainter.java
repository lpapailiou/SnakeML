package ui.painter;

import ai.GameAdapter;
import game.element.Cell;
import game.element.Snake;

public interface IGamePainter {

  void paintBoard(boolean isActive);

  void paintFood(Cell foodPosition);

  void paintSnake(Snake snake);

}
