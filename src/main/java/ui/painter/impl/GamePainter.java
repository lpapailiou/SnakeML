package ui.painter.impl;

import game.element.Cell;
import main.configuration.Config;
import game.element.Snake;
import javafx.scene.canvas.GraphicsContext;
import main.configuration.IColorConfig;
import main.configuration.IGameConfig;
import ui.painter.IGamePainter;

public class GamePainter implements IGamePainter {

  public void paintSnake(GraphicsContext gc, Snake snake) {
    IColorConfig colorConfig = Config.getInstance();
    IGameConfig gameConfig = (IGameConfig) colorConfig;
    double cellDimension = gameConfig.getCellWidth();
    snake.getBody().forEach(cell -> {
      gc.setFill(colorConfig.getSnakeBodyColor());
      gc.fillRect(cell.x * cellDimension, cell.y * cellDimension, cellDimension - 1, cellDimension
          - 1);
      gc.setFill(colorConfig.getSnakeBodyColor());
      gc.fillRect(cell.x * cellDimension, cell.y * cellDimension, cellDimension - 2, cellDimension
          - 2);
    });
  }

  public void paintFood(GraphicsContext gc, Cell foodPosition) {
    IColorConfig colorConfig = Config.getInstance();
    IGameConfig gameConfig = (IGameConfig) colorConfig;
    double cellDimension = gameConfig.getCellWidth();
    gc.setFill(colorConfig.getFoodColor());
    gc.fillOval(foodPosition.x * cellDimension, foodPosition.y * cellDimension, cellDimension,
        cellDimension);
  }

  public void paintBoard(GraphicsContext gc) {
    IColorConfig colorConfig = Config.getInstance();
    IGameConfig gameConfig = (IGameConfig) colorConfig;
    double cellDimension = gameConfig.getCellWidth();
    gc.setFill(colorConfig.getBackgroundColor());
    gc.fillRect(0, 0, gameConfig.getBoardWidth() * cellDimension, gameConfig.getBoardHeight() * cellDimension);
  }
}
