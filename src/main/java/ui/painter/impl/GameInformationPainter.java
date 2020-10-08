package ui.painter.impl;

import main.configuration.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import main.configuration.IColorConfig;
import main.configuration.IFontConfig;
import ui.painter.IGameInformationPainter;

public class GameInformationPainter implements IGameInformationPainter {

  public void paintGameOver(GraphicsContext gc) {
    IColorConfig colorConfig = Config.getInstance();
    IFontConfig fontConfig = (IFontConfig) colorConfig;
    gc.setFill(colorConfig.getGameOverFontColor());
    gc.setFont(new Font("", fontConfig.getGameOverFontSize()));
    gc.fillText(fontConfig.getGameOverText(), 100, 250);
  }

  private void paintScore(GraphicsContext gc) {
    IColorConfig colorConfig = Config.getInstance();
    IFontConfig fontConfig = (IFontConfig) colorConfig;
    gc.setFill(colorConfig.getScoreFontColor());
    gc.setFont(new Font("", fontConfig.getScoreFontSize()));
    gc.fillText("Score: -5", 10, fontConfig.getScoreFontSize());
  }

}
