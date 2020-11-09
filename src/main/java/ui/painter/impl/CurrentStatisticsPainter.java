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

  public CurrentStatisticsPainter(GraphicsContext context) {
    this.context = context;
  }


  public void paint(GenerationEntity entity, int snakeLength) {
    Color background = config.getTheme().isDarkTheme() ? config.getBackgroundColor().brighter() : config.getBackgroundColor().brighter();
    background = background.deriveColor(1,1,1,0.8);
    context.setFill(background);
    context.fillRoundRect(offsetX, offsetY, width, height, 6, 6);
    context.setFill(config.getSnakeBodyColor());
    context.fillText("current snake length:   \t" + snakeLength, offsetX+10, offsetY+20);
    context.fillText("max. snake length:   \t" + entity.getMax_snake_length(), offsetX+padding, offsetY+40);
    context.fillText("max. steps:   \t\t\t" + entity.getMax_steps(), offsetX+padding, offsetY+60);
    double deathWall = entity.getNumber_of_wall_deaths();
    double deathBody = entity.getNumber_of_body_deaths();
    double deathTimeout = entity.getNumber_of_timeout_deaths();
    double deathcount = deathBody+deathWall+deathTimeout;
    context.fillText("death by wall:   \t\t" + (int) (100/deathcount*deathWall) + " %", offsetX+padding+200, offsetY+20);
    context.fillText("death by body:   \t\t" + (int) (100/deathcount*deathBody) + " %", offsetX+padding+200, offsetY+40);
    context.fillText("death by timeout:   \t\t" + (int) (100/deathcount*deathTimeout) + " %", offsetX+padding+200, offsetY+60);

    context.fillText("generation:", offsetX+padding+400, offsetY+60);
    context.setFont(new Font("", 32));
    context.fillText(entity.getId()+1+"", offsetX+padding+400+70, offsetY+60);    // starts counting from 1
    context.setFont(new Font("", 10));
    context.setFill(config.getSnakeBodyColor().deriveColor(1,1,1,0.5));
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

}
