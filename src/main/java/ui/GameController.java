package ui;

import ai.data.GenerationEntity;
import game.Game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import ui.painter.impl.GameInformationPainter;
import ui.painter.impl.GamePainter;

/**
 * This is a child controller of the Application controller. Its purpose is to visualize one snake
 * game at a time. Additionally, it will indicate specific events (e.g. game over or statistics
 * updates) by different visualisation.
 */
public class GameController implements Initializable {

  @FXML
  private Canvas gamePane;

  private GamePainter gamePainter;
  private GameInformationPainter statisticsPainter;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resetGameDisplay();
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
    statisticsPainter.paintGameInformation(entity, snakeLength, position);
  }

  void resetGameDisplay() {
    gamePainter = new GamePainter(gamePane.getGraphicsContext2D());
    statisticsPainter = new GameInformationPainter(gamePane.getGraphicsContext2D());
    display(null);
  }

}
