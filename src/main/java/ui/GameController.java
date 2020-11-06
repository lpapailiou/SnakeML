package ui;

import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import ui.painter.IGameTicker;
import ui.painter.impl.GamePainter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, IGameTicker {

  @FXML
  private Canvas gamePane;

  private static GameController instance;
  private GamePainter gamePainter;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    onTick(new Game());   // TODO: remove
  }

  @Override
  public void onTick(Game game) {
    boolean isActive = game == null ? true : !game.snake.isDead();
    if (game == null) {
      gamePainter.paintBoard(isActive);
    } else {
      gamePainter.paintBoard(isActive);
      gamePainter.paintFood(game.food);
      gamePainter.paintSnake(game.snake);
    }
  }

  public static void display(Game game) {
    if (instance != null) {
      instance.onTick(game);
    }
  }

  void reset() {
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    onTick(null);
  }

  static void resetGamePanel() {
    if (instance != null) {
      instance.reset();
    }
  }

}
