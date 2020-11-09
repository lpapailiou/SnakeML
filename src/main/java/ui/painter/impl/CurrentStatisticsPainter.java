package ui.painter.impl;

import ai.data.GenerationEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.configuration.Config;
import main.configuration.IColorConfig;
import main.configuration.Theme;

public class CurrentStatisticsPainter {

  private GraphicsContext context;
  private IColorConfig config = Config.getInstance();
  private final double canvasWidth = 800;     // must match fxml
  private final double width = 570; // 220
  private final double height = 70;  //140
  private final double offsetX = 20;
  private final double offsetY = (canvasWidth-offsetX)-height;
  private final double padding = 10;
  private double cellWidth;
  private double paddingWidth;
  private double paddingHeight;

  public CurrentStatisticsPainter(GraphicsContext context) {
    this.context = context;
    initializeDimensions();
  }


  public void paint(GenerationEntity entity, int snakeLength, int position) {
    Color background = config.getTheme().isDarkTheme() ? config.getBackgroundColor().brighter() : config.getBackgroundColor().brighter();
    background = background.deriveColor(1,1,1,0.8);
    context.setFill(background);
    double deathWall = entity.getNumber_of_wall_deaths();
    double deathBody = entity.getNumber_of_body_deaths();
    double deathTimeout = entity.getNumber_of_timeout_deaths();
    double deathcount = deathBody+deathWall+deathTimeout;
    switch (position) {
      case 0:
        context.setFont(new Font("", 10));
        context.fillRoundRect(offsetX, offsetY, width, height, 6, 6);
        context.setFill(config.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, offsetX+10, offsetY+20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), offsetX+padding, offsetY+40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), offsetX+padding, offsetY+60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathcount*deathWall) + " %", offsetX+padding+200, offsetY+20);
        context.fillText("death by body:   \t\t" + (int) (100/deathcount*deathBody) + " %", offsetX+padding+200, offsetY+40);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathcount*deathTimeout) + " %", offsetX+padding+200, offsetY+60);
        context.fillText("generation:", offsetX+padding+400, offsetY+60);
        context.setFont(new Font("", 32));
        context.fillText(entity.getId()+1+"", offsetX+padding+400+70, offsetY+60);    // starts counting from 1
        break;
      case 1:
        context.setFont(new Font("", 10));
        double x = paddingWidth+offsetX-padding;
        double y = offsetY-paddingHeight+padding;
        context.fillRoundRect(x, y, width, height, 6, 6);
        context.setFill(config.getSnakeBodyColor());
        context.fillText("current snake length:   \t" + snakeLength, x+10, y+20);
        context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), x+padding, y+40);
        context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), x+padding, y+60);
        context.fillText("death by wall:   \t\t" + (int) (100/deathcount*deathWall) + " %", x+padding+200, y+20);
        context.fillText("death by body:   \t\t" + (int) (100/deathcount*deathBody) + " %", x+padding+200, y+40);
        context.fillText("death by timeout:   \t\t" + (int) (100/deathcount*deathTimeout) + " %", x+padding+200, y+60);
        context.fillText("generation:", x+padding+400, y+60);
        context.setFont(new Font("", 32));
        context.fillText(entity.getId()+1+"", x+padding+400+70, y+60);    // starts counting from 1
        break;
      case 2:
        context.setFont(new Font("", 10));
        x = paddingWidth+offsetX-padding;
        y = offsetY-paddingHeight-padding-padding+50;

        context.fillRoundRect(x, y, 100, 50, 6, 6);
        context.setFill(config.getSnakeBodyColor());
        context.setFont(new Font("", 32));
        context.fillText(entity.getId()+1+"", x+padding, y+35);    // starts counting from 1
        break;
    }



  }


  public void paintBoard(boolean isActive) {
    double offset = (Math.min(paddingWidth, paddingHeight))/6;
    context.fillRect(paddingWidth-offset, paddingHeight-offset, (cellWidth*width)+offset*2, (cellWidth*height)+offset*2);
    context.fillRect(paddingWidth, paddingHeight, (cellWidth*config.getBoardWidth()), (cellWidth*config.getBoardHeight()));
  }




/*
  public void paint(GenerationEntity entity) {
    Color background = config.getTheme().isDarkTheme() ? config.getBackgroundColor().brighter() : config.getBackgroundColor().brighter();
    background = background.deriveColor(1,1,1,0.8);
    context.setFill(background);
    context.fillRoundRect(offsetX, offsetY, width, height, 6, 6);
    context.setFill(config.getSnakeBodyColor());
    context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), offsetX+10, offsetY+20);
    context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), offsetX+10, offsetY+40);
    double deathWall = entity.getNumber_of_wall_deaths();
    double deathBody = entity.getNumber_of_body_deaths();
    double deathTimeout = entity.getNumber_of_timeout_deaths();
    double deathcount = deathBody+deathWall+deathTimeout;
    context.fillText("death by wall:   \t\t" + (int) (100/deathcount*deathWall) + " %", offsetX+10, offsetY+70);
    context.fillText("death by body:   \t\t" + (int) (100/deathcount*deathBody) + " %", offsetX+10, offsetY+90);
    context.fillText("death by timeout:   \t\t" + (int) (100/deathcount*deathTimeout) + " %", offsetX+10, offsetY+110);
    context.setFill(config.getSnakeBodyColor().deriveColor(1,1,1,0.5));
    context.fillText("hide/show realtime statistics by pressing V key", offsetX*2 + width, offsetY+110);
    //context.fillText("hide/show realtime statistics by pressing V key", offsetX+10, offsetY+130);
  }*/

  private void initializeDimensions() {
    double basePadding = 20;
    int width = config.getBoardWidth();
    int height = config.getBoardHeight();

    if (width >= height) {
      cellWidth = (canvasWidth-basePadding*2)/width;
    } else {
      cellWidth = (canvasWidth-basePadding*2)/height;
    }
    paddingWidth = (canvasWidth-(cellWidth*width))/2;
    paddingHeight = (canvasWidth-(cellWidth*height))/2;
  }

}
