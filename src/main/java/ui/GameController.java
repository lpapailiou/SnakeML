package ui;

import ai.GameAdapter;
import ai.data.GenerationEntity;
import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import ui.painter.IGameTicker;
import ui.painter.impl.CurrentStatisticsPainter;
import ui.painter.impl.GamePainter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, IGameTicker {

  @FXML
  private Canvas gamePane;

  private static GameController instance;
  private GamePainter gamePainter;
  private CurrentStatisticsPainter statisticsPainter;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    statisticsPainter = new CurrentStatisticsPainter(gamePane.getGraphicsContext2D());
    onTick(null);
  }

  @Override
  public void onTick(Game game) {
    boolean isActive = game == null ? true : !game.snake.isDead();
    if (game == null) {
      gamePainter.paintBoard(isActive);
      statisticsPainter = new CurrentStatisticsPainter(gamePane.getGraphicsContext2D());
    } else {
      gamePainter.paintBoard(isActive);
      gamePainter.paintFood(game.food);
      gamePainter.paintSnake(game.snake);
    }
  }

  static void display(Game game) {
    if (instance != null) {
      instance.onTick(game);
    }
  }

  static void displayStats(GenerationEntity entity, int snakeLength, int position) {
    instance.statisticsPainter.paint(entity, snakeLength, position);
  }

  private void reset() {
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    onTick(null);
  }

  static void resetGamePanel() {
    if (instance != null) {
      instance.reset();
    }
  }

}
