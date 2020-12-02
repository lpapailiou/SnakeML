package ui.painter.impl;

import ai.data.GenerationEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.configuration.Config;
import main.configuration.IPainterConfigReader;
import main.configuration.Theme;
import ui.painter.IGameInformationPainter;

public class GameInformationPainter implements IGameInformationPainter {

  private GraphicsContext context;
  private IPainterConfigReader config = IPainterConfigReader.getInstance();
  private Theme colors = config.getTheme();
  private final double canvasWidth = 800;     // must match fxml
  private double paddingWidth;
  private double paddingHeight;

  public GameInformationPainter(GraphicsContext context) {
    this.context = context;
    initializeDimensions();
  }

  public void paintGameInformation(GenerationEntity entity, int snakeLength, int position) {
    Color background = config.getTheme().isDarkTheme() ? colors.getBackgroundColor().brighter() : colors.getBackgroundColor().brighter();
    background = background.deriveColor(1,1,1,0.8);
    context.setFill(background);
    double deathWall = entity.getNumber_of_wall_deaths();
    double deathBody = entity.getNumber_of_body_deaths();
    double deathTimeout = entity.getNumber_of_timeout_deaths();
    double deathCount = deathBody+deathWall+deathTimeout;
    int generation = entity.getId()+1;       // start counting from 1?
    double fontSizeLarge = 32;
    double fontSize = 12;
    double padding = 10;
    double offsetX = 20;
    double height = 70;
    double offsetY = (canvasWidth - offsetX) - height;
    double width = 570;
    // TODO: refactor into generic associative array: text -> position ?
    switch (position) {
      case 0:
        context.setFont(new Font("", fontSize));
        context.fillRoundRect(offsetX, offsetY, width, height, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, offsetX + padding, offsetY +20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), offsetX + padding, offsetY
            +40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), offsetX + padding, offsetY +60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathCount*deathWall) + " %", offsetX
            + padding
            +200, offsetY +20);
        context.fillText("death by body:   \t\t" + (int) (100/deathCount*deathBody) + " %", offsetX
            + padding
            +200, offsetY +40);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathCount*deathTimeout) + " %", offsetX
            + padding
            +200, offsetY +60);
        context.fillText("generation:", offsetX + padding +400, offsetY +60);
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", offsetX + padding +400+70, offsetY +60);    // starts counting from 1
        break;
      case 1:
        context.setFont(new Font("", fontSize));
        double x = offsetX;
        double y = offsetY -120;
        context.fillRoundRect(offsetX, y, 230, 190, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, offsetX + padding, y+20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), offsetX + padding, y+40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), offsetX + padding, y+60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathCount*deathWall) + " %", offsetX
            + padding, y+90);
        context.fillText("death by body:   \t\t" + (int) (100/deathCount*deathBody) + " %", offsetX
            + padding, y+110);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathCount*deathTimeout) + " %", offsetX
            + padding, y+130);
        context.fillText("generation:", offsetX + padding, y+180);
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", offsetX + padding +130, y+180);    // starts counting from 1
        break;
      case 2:
        x = offsetX;
        y = offsetY + height -50;
        context.fillRoundRect(x, y, 100, 50, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", x+ padding, y+40);    // starts counting from 1
        break;
      case 3:
        context.setFont(new Font("", fontSize));
        x = paddingWidth+ offsetX - padding;
        y = offsetY -paddingHeight+ padding;
        context.fillRoundRect(x, y, width, height, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, x+ padding, y+20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), x+ padding, y+40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), x+ padding, y+60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathCount*deathWall) + " %", x+ padding
            +200, y+20);
        context.fillText("death by body:   \t\t" + (int) (100/deathCount*deathBody) + " %", x+ padding
            +200, y+40);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathCount*deathTimeout) + " %", x+ padding
            +200, y+60);
        context.fillText("generation:", x+ padding +400, y+60);
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", x+ padding +400+70, y+60);    // starts counting from 1
        break;
      case 4:
        context.setFont(new Font("", fontSize));
        x = paddingWidth+ offsetX - padding;
        y = offsetY -paddingHeight+ padding -120;
        context.fillRoundRect(x, y, 230, 190, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, x+ padding, y+20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), x+ padding, y+40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), x+ padding, y+60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathCount*deathWall) + " %", x+ padding, y+90);
        context.fillText("death by body:   \t\t" + (int) (100/deathCount*deathBody) + " %", x+ padding, y+110);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathCount*deathTimeout) + " %", x+ padding, y+130);
        context.fillText("generation:", x+ padding, y+180);
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", x+ padding +130, y+180);    // starts counting from 1
        break;
      case 5:
        x = paddingWidth+ offsetX - padding;
        y = offsetY -paddingHeight- padding - padding +50;
        context.fillRoundRect(x, y, 100, 50, 6, 6);
        context.setFill(colors.getSnakeBodyColor());
        context.setFont(new Font("", fontSizeLarge));
        context.fillText(generation+"", x+ padding, y+40);    // starts counting from 1
        break;

    }

  }

  private void initializeDimensions() {
    double basePadding = 20;
    int width = config.getBoardWidth();
    int height = config.getBoardHeight();

    double cellWidth;
    if (width >= height) {
      cellWidth = (canvasWidth-basePadding*2)/width;
    } else {
      cellWidth = (canvasWidth-basePadding*2)/height;
    }
    paddingWidth = (canvasWidth-(cellWidth *width))/2;
    paddingHeight = (canvasWidth-(cellWidth *height))/2;
  }

}
