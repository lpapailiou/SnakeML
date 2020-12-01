package ui;

import ai.data.GenerationEntity;
import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import ui.painter.impl.GameInformationPainter;
import ui.painter.impl.GamePainter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, IGameTicker {

  @FXML
  private Canvas gamePane;

  private static GameController instance;
  private GamePainter gamePainter;
  private GameInformationPainter statisticsPainter;
  private Game displayedGame;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    statisticsPainter = new GameInformationPainter(gamePane.getGraphicsContext2D());
    onTick();
  }

  @Override
  public void onTick() {
    display(displayedGame);
  }

  void display(Game game) {
    boolean isActive = game == null || !game.getSnake().isDead();
    if (game == null) {
      gamePainter.paintBoard(true);
    } else {
      gamePainter.paintBoard(isActive);
      gamePainter.paintFood(game.getFood());
      gamePainter.paintSnake(game.getSnake());
    }
  }

  void displayStats(GenerationEntity entity, int snakeLength, int position) {
    statisticsPainter.paint(entity, snakeLength, position);
  }

  private void reset() {
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    onTick();
  }

  static void resetGamePanel() {
    if (instance != null) {
      instance.reset();
      instance.statisticsPainter = new GameInformationPainter(instance.gamePane.getGraphicsContext2D());
    }
  }

  public void setDisplayedGame(Game displayedGame) {
    this.displayedGame = displayedGame;
  }
}
