package ui.painter.impl;

import ai.data.GenerationEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.configuration.IPainterConfigReader;
import main.configuration.Theme;
import ui.painter.IGameInformationPainter;

/**
 * This is a helper class which isolates the visualization of the real time statistics on the user
 * interface. It is applicable only when the application is run in NeuralNetwork mode.
 */
public class GameInformationPainter implements IGameInformationPainter {

  private static final String FONT_NAME = "Monospace Regular";
  private static final double CANVAS_WIDTH = 800;     // must match fxml

  private GraphicsContext context;
  private IPainterConfigReader config = IPainterConfigReader.getInstance();
  private Theme colors = config.getTheme();
  private double paddingWidth;
  private double paddingHeight;

  /**
   * @param context the graphics context of the panel to be painted on
   */
  public GameInformationPainter(GraphicsContext context) {
    this.context = context;
    initializeDimensions();
  }

  /**
   * This method triggers the visualisation of aggregated realitme statistics.
   *
   * @param entity      the aggregated GenerationEntity data wrapper instance
   * @param snakeLength the length of the current snake
   * @param position    the ID of the position the statistics should be displayed at
   */
  public void paintGameInformation(GenerationEntity entity, int snakeLength, int position) {
    Color background = config.getTheme().isDarkTheme() ? colors.getBackgroundColor().brighter()
        : colors.getBackgroundColor().brighter();
    background = background.deriveColor(1, 1, 1, 0.8);
    context.setFill(background);
    double deathWall = entity.getNumberOfWallDeaths();
    double deathBody = entity.getNumberOfBodyDeaths();
    double deathTimeout = entity.getNumberOfTimeoutDeaths();
    double deathCount = deathBody + deathWall + deathTimeout;
    int maxSnakeLength = entity.getMaxSnakeLength();
    int generation = entity.getId() + 1;       // start counting from 1?
    double fontSizeLarge = 32;
    double fontSize = 12;
    double padding = 10;
    double offsetX = 20;
    double height = 70;
    double offsetY = (CANVAS_WIDTH - offsetX) - height;
    double width = 570;

    double x;
    double y;

    switch (position) {
      case 0:
        context.setFont(new Font(FONT_NAME, fontSize));
        context.fillRoundRect(offsetX, offsetY, width, height, 6, 6);
        context.setFill(colors.getSnakeBodyColor());

        writeStatisticsLine("current snake length", snakeLength, false, offsetX + padding, offsetY + 20);
        writeStatisticsLine("max. snake length", maxSnakeLength, false, offsetX + padding, offsetY + 40);
        writeStatisticsLine("max. steps",  entity.getMaxSteps(), false, offsetX + padding, offsetY + 60);

        writeStatisticsLine("death by wall", (int) (100 / deathCount * deathWall), true, offsetX + padding + 200, offsetY + 20);
        writeStatisticsLine("death by body", (int) (100 / deathCount * deathBody), true, offsetX + padding + 200, offsetY + 40);
        writeStatisticsLine("death by timeout", (int) (100 / deathCount * deathTimeout), true, offsetX + padding + 200, offsetY + 60);

        context.fillText("generation:", offsetX + padding + 400, offsetY + 60);
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), offsetX + padding + 400 +100, offsetY + 60);
        break;

      case 1:
        context.setFont(new Font(FONT_NAME, fontSize));
        y = offsetY - 120;
        context.fillRoundRect(offsetX, y, 230, 190, 6, 6);
        context.setFill(colors.getSnakeBodyColor());

        writeStatisticsLine("current snake length", snakeLength, false, offsetX + padding, y + 20);
        writeStatisticsLine("max. snake length", maxSnakeLength, false, offsetX + padding, y + 40);
        writeStatisticsLine("max. steps",  entity.getMaxSteps(), false, offsetX + padding, y + 60);

        writeStatisticsLine("death by wall", (int) (100 / deathCount * deathWall), true, offsetX + padding, y + 90);
        writeStatisticsLine("death by body", (int) (100 / deathCount * deathBody), true, offsetX + padding, y + 110);
        writeStatisticsLine("death by timeout", (int) (100 / deathCount * deathTimeout), true, offsetX + padding, y + 130);

        context.fillText("generation:", offsetX + padding, y + 180);
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), offsetX + padding + 150, y + 180);
        break;

      case 2:
        x = offsetX;
        y = offsetY + height - 50;
        context.fillRoundRect(x, y, 100, 50, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), x + padding, y + 40);
        break;

      case 3:
        context.setFont(new Font(FONT_NAME, fontSize));
        x = paddingWidth + offsetX - padding;
        y = offsetY - paddingHeight + padding;
        context.fillRoundRect(x, y, width, height, 6, 6);
        context.setFill(colors.getSnakeBodyColor());

        writeStatisticsLine("current snake length", snakeLength, false, x + padding, y + 20);
        writeStatisticsLine("max. snake length", maxSnakeLength, false, x + padding, y + 40);
        writeStatisticsLine("max. steps",  entity.getMaxSteps(), false, x + padding, y + 60);

        writeStatisticsLine("death by wall", (int) (100 / deathCount * deathWall), true, x + padding + 200, y + 20);
        writeStatisticsLine("death by body", (int) (100 / deathCount * deathBody), true, x + padding + 200, y + 40);
        writeStatisticsLine("death by timeout", (int) (100 / deathCount * deathTimeout), true, x + padding + 200, y + 60);

        context.fillText("generation:", x + padding + 400, y + 60);
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), x + padding + 400 + 100, y + 60);
        break;

      case 4:
        context.setFont(new Font(FONT_NAME, fontSize));
        x = paddingWidth + offsetX - padding;
        y = offsetY - paddingHeight + padding - 120;
        context.fillRoundRect(x, y, 230, 190, 6, 6);
        context.setFill(colors.getSnakeBodyColor());

        writeStatisticsLine("current snake length", snakeLength, false, x + padding, y + 20);
        writeStatisticsLine("max. snake length", maxSnakeLength, false, x + padding, y + 40);
        writeStatisticsLine("max. steps",  entity.getMaxSteps(), false, x + padding, y + 60);


        writeStatisticsLine("death by wall", (int) (100 / deathCount * deathWall), true, x + padding, y + 90);
        writeStatisticsLine("death by body", (int) (100 / deathCount * deathBody), true, x + padding, y + 110);
        writeStatisticsLine("death by timeout", (int) (100 / deathCount * deathTimeout), true, x + padding, y + 130);

        context.fillText("generation:", x + padding, y + 180);
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), x + padding + 150, y + 180);
        break;

      case 5:
        x = paddingWidth + offsetX - padding;
        y = offsetY - paddingHeight - padding - padding + 50;
        context.fillRoundRect(x, y, 100, 50, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.setFont(new Font(FONT_NAME, fontSizeLarge));
        context.fillText(String.format("%2d", generation), x + padding, y + 40);
        break;
    }
  }

  private void writeStatisticsLine(String propertyText, int value, boolean isPercentile, double x, double y) {
    propertyText += ":";
    final int stringLength = 22;
    final String pattern = "%-" + stringLength + "s%3d%s";
    final String text = String.format(pattern, propertyText, value, isPercentile ? "%" : "");
    context.fillText(text, x, y);
  }

  private void initializeDimensions() {
    double basePadding = 20;
    int width = config.getBoardWidth();
    int height = config.getBoardHeight();

    double cellWidth;
    if (width >= height) {
      cellWidth = (CANVAS_WIDTH - basePadding * 2) / width;
    } else {
      cellWidth = (CANVAS_WIDTH - basePadding * 2) / height;
    }
    paddingWidth = (CANVAS_WIDTH - (cellWidth * width)) / 2;
    paddingHeight = (CANVAS_WIDTH - (cellWidth * height)) / 2;
  }

}
