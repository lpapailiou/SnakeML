package ui.painter.impl;

import ai.data.GenerationEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.configuration.Config;
import main.configuration.IColorConfig;
import main.configuration.Theme;

public class CurrentStatisticsPainter {

  private GraphicsContext context;
  private IColorConfig config = Config.getInstance();
  private final double canvasWidth = 800;     // must match fxml
  private final double height = 120;
  private final double offsetX = 20;
  private final double offsetY = (canvasWidth-offsetX)-height;

  public CurrentStatisticsPainter(GraphicsContext context) {
    this.context = context;
  }

  public void paint(GenerationEntity entity) {
    Color background = config.getTheme().isDarkTheme() ? config.getBackgroundColor().brighter() : config.getBackgroundColor().brighter();
    background = background.deriveColor(1,1,1,0.8);
    context.setFill(background);
    context.fillRoundRect(offsetX, offsetY, 220, height, 6, 6);
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
  }

}
