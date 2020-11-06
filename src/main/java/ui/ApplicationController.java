package ui;

import game.Direction;
import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.configuration.Config;
import main.configuration.Mode;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

  @FXML
  private HBox rootElement;

  private static ApplicationController instance;
  private Timeline timeline = null;
  private boolean isTimerRunning = false;
  private Direction direction = Config.getInstance().getInitialDirection();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    rootElement.sceneProperty().addListener(n -> {
      if (n != null) {
        listenToKeyboardEvents(rootElement.getScene());
      }
    });
  }

  public static void start() {
    if (instance != null) {
      instance.setUpTimer();
    }
  }

  public static void stop() {
    if (instance != null) {
      instance.stopTimer();
    }
  }

  private void setUpTimer() {
    isTimerRunning = true;
    ConfigController.disableInputs(true);
    direction = Config.getInstance().getInitialDirection();
    int speed = Config.getInstance().getMode().getSpeed();
    Game game = new Game();
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      manualGame(game);
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void manualGame(Game game) {
    game.changeDirection(direction);
    game.onTick();
    GameController.display(game);
    game.onGameOver(this::stopTimer);
  }

  private void stopTimer() {
    isTimerRunning = false;
    ConfigController.disableInputs(false);
    Platform.runLater(() -> {
      if (timeline != null) {
        timeline.stop();
      }
    });
  }

  private void listenToKeyboardEvents(Scene scene) {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
      switch (key.getCode()) {
        case W:
        case UP:
          direction = Direction.UP;
          break;

        case A:
        case LEFT:
          direction = Direction.LEFT;
          break;

        case S:
        case DOWN:
          direction = Direction.DOWN;
          break;

        case D:
        case RIGHT:
          direction = Direction.RIGHT;
          break;

        case SPACE:
        case ENTER:
          triggerGame();
          break;
      }
    });
  }

  private void triggerGame() {
    if (!isTimerRunning) {
      start();
    } else {
      stop();
    }
  }



}
