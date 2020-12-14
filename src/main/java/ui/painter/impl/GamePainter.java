package ui.painter.impl;

import game.Cell;
import game.Snake;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.configuration.IPainterConfigReader;
import main.configuration.Theme;
import ui.painter.IGamePainter;

/**
 * This is a helper class which isolates the visualization of the snake game on the user interface.
 */
public class GamePainter implements IGamePainter {

  private GraphicsContext context;
  private IPainterConfigReader config = IPainterConfigReader.getInstance();
  private Theme colors = config.getTheme();

  private static final double CANVAS_WIDTH = 800;     // must match fxml
  private double cellWidth;
  private double strokeWidth;
  private double paddingWidth;
  private double paddingHeight;

  /**
   * @param context the graphics context of the panel to be painted on
   */
  public GamePainter(GraphicsContext context) {
    this.context = context;
    initializeDimensions();
  }

  /**
   * This method triggers the visualisation of the snake.
   */
  public void paintSnake(Snake snake) {
    List<Cell> body = snake.getBody();

    if (body.size() <= 1) {
      drawCell(body.get(0).x, body.get(0).y, colors.getSnakeBodyColor());
      return;
    }

    for (int i = 0; i < body.size() - 1; i++) {
      Cell part = body.get(i);
      Cell partNext;
      if (i + 1 < body.size()) {
        partNext = body.get(i + 1);
      } else {
        partNext = body.get(i);
      }
      drawLine(part.x, part.y, partNext.x, partNext.y, colors.getSnakeBodyColor());
    }
  }

  /**
   * This method triggers the visualisation of the food or 'apple'.
   */
  public void paintFood(Cell foodPosition) {
    if (foodPosition != null) {
      drawCell(foodPosition.x, foodPosition.y, colors.getFoodColor());
    }
  }

  /**
   * This method triggers the visualisation of the game board.
   */
  public void paintBoard(boolean isActive) {
    int width = config.getBoardWidth();
    int height = config.getBoardHeight();
    context.clearRect(0, 0, CANVAS_WIDTH, CANVAS_WIDTH);
    context.setFill(colors.getBackgroundColor());
    context.fillRect(0, 0, CANVAS_WIDTH, CANVAS_WIDTH);
    double offset = (Math.min(paddingWidth, paddingHeight)) / 6;
    context.setFill(isActive ? colors.getFrameActiveColor() : colors.getFrameInactiveColor());
    context
        .fillRect(paddingWidth - offset, paddingHeight - offset, (cellWidth * width) + offset * 2,
            (cellWidth * height) + offset * 2);
    context.setFill(colors.getBackgroundColor());
    context.fillRect(paddingWidth, paddingHeight, (cellWidth * width), (cellWidth * height));
  }

  private void drawLine(int x1, int y1, int x2, int y2, Color color) {
    context.setStroke(color);
    context.setLineWidth(strokeWidth);
    context.strokeLine(x1 * cellWidth + cellWidth / 2 + paddingWidth,
        y1 * cellWidth + cellWidth / 2 + paddingHeight,
        x2 * cellWidth + cellWidth / 2 + paddingWidth,
        y2 * cellWidth + cellWidth / 2 + paddingHeight);
  }

  private void drawCell(int x, int y, Color color) {
    context.setFill(color);
    context.fillRect(x * cellWidth + ((cellWidth - strokeWidth) / 2) + paddingWidth,
        y * cellWidth + ((cellWidth - strokeWidth) / 2) + paddingHeight, strokeWidth, strokeWidth);
  }

  private void initializeDimensions() {
    double basePadding = 20;
    int width = config.getBoardWidth();
    int height = config.getBoardHeight();

    if (width >= height) {
      cellWidth = (CANVAS_WIDTH - basePadding * 2) / width;
    } else {
      cellWidth = (CANVAS_WIDTH - basePadding * 2) / height;
    }
    strokeWidth = cellWidth / 5 * 4;
    if (strokeWidth < 2) {
      strokeWidth = 2;
    }
    paddingWidth = (CANVAS_WIDTH - (cellWidth * width)) / 2;
    paddingHeight = (CANVAS_WIDTH - (cellWidth * height)) / 2;
  }

}
