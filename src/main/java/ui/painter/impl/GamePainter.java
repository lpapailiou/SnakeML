package ui.painter.impl;

import static main.Config.CELL_SIZE_IN_PX;
import static main.Config.NUMBER_OF_CELL_COLUMNS;
import static main.Config.NUMBER_OF_CELL_ROWS;
import static main.Config.SNAKE_BODY_COLOR;

import game.element.Cell;
import main.Config;
import game.element.Snake;
import javafx.scene.canvas.GraphicsContext;
import ui.painter.IGamePainter;

public class GamePainter implements IGamePainter {


  public void paintSnake(GraphicsContext gc, Snake snake) {
    snake.getBody().forEach(cell -> {
      gc.setFill(SNAKE_BODY_COLOR);
      gc.fillRect(cell.x * CELL_SIZE_IN_PX, cell.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX - 1, CELL_SIZE_IN_PX
          - 1);
      gc.setFill(SNAKE_BODY_COLOR);
      gc.fillRect(cell.x * CELL_SIZE_IN_PX, cell.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX - 2, CELL_SIZE_IN_PX
          - 2);
    });
  }

  public void paintFood(GraphicsContext gc, Cell foodPosition) {
    gc.setFill(Config.FOOD_COLOR);
    gc.fillOval(foodPosition.x * CELL_SIZE_IN_PX, foodPosition.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX,
        CELL_SIZE_IN_PX);
  }

  public void paintBoard(GraphicsContext gc) {
    gc.setFill(Config.BACKGROUND_COLOR);
    gc.fillRect(0, 0, NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX, NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX);
  }
}
