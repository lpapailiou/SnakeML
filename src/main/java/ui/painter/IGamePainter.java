package ui.painter;

import game.element.Cell;
import game.element.Snake;
import javafx.scene.canvas.GraphicsContext;

public interface IGamePainter {

  void paintBoard(GraphicsContext gc);

  void paintFood(GraphicsContext gc, Cell foodPosition);

  void paintSnake(GraphicsContext gc, Snake snake);
}
