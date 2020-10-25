package ui.painter.impl;

import main.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import ui.painter.IGameInformationPainter;

public class GameInformationPainter implements IGameInformationPainter {

  public void paintGameOver(GraphicsContext gc) {
    gc.setFill(Config.GAME_OVER_FONT_COLOR);
    gc.setFont(new Font("", Config.GAME_OVER_FONT_SIZE));
    gc.fillText(Config.GAME_OVER_TEXT, 100, 250);
  }

  private void paintScore(GraphicsContext gc) {
    gc.setFill(Config.SCORE_FONT_COLOR);
    gc.setFont(new Font("", Config.SCORE_FONT_SIZE_IN_PT));
    gc.fillText("Score: -5", 10, Config.SCORE_FONT_SIZE_IN_PT);
  }

}
