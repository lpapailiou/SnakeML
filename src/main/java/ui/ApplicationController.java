package ui;

import ai.GameAdapter;
import ai.GameBatch;
import ai.data.storage.Serializer;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import main.configuration.Config;
import main.configuration.IGameConfig;
import main.configuration.INeuralNetworkConfig;
import main.configuration.Mode;
import webserver.ItemHolder;
import webserver.WebServer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationController implements Initializable {

  @FXML
  private HBox rootElement;

  private INeuralNetworkConfig config = Config.getInstance();
  private static ApplicationController instance;
  private Timeline timeline = null;
  private boolean isTimerRunning = false;
  private Direction direction = ((IGameConfig) config).getInitialDirection();
  private GameAdapter adapter;
  ItemHolder itemHolder = new ItemHolder();

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
    direction = ((IGameConfig) config).getInitialDirection();
    int speed = Config.getInstance().getMode().getSpeed();    // TODO: with interface
    Game game = new Game();
    adapter = null;
    if (Config.getInstance().getMode() == Mode.NEURAL_NETWORK_DEMO) {
      adapter = new GameAdapter(Serializer.load(), null);
    }
    GameBatch batch = new GameBatch(new NeuralNetwork(config.getRandomizationRate(), config.getLayerConfiguration()));
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      switch (Config.getInstance().getMode()) {
        case MANUAL:
          manualGame(game);
          break;
        case NEURAL_NETWORK:
          neuralGame(batch);
          break;
        case NEURAL_NETWORK_DEMO:
          demoGame(adapter);
          break;
      }

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void manualGame(Game game) {
    if (isTimerRunning) {
      game.changeDirection(direction);
      game.onTick();
      GameController.display(game);
      game.onGameOver(this::stopTimer);
    }
  }

  private void neuralGame(GameBatch batch) {
    if (isTimerRunning) {
      if (adapter == null) {
        NeuralNetwork neuralNet = batch.processGeneration();
        if (neuralNet != null) {
          adapter = new GameAdapter(neuralNet, null);
        }
      }
      if (adapter != null) {
        boolean success = adapter.moveSnake();
        GameController.display(adapter.getGame());
        ConfigController.display(batch.getCurrentGeneration(), adapter.getGame().getDirection());

        if (!success) {
          adapter = null;
        }
      } else {
        stopTimer();
      }
    }
  }

  private void demoGame(GameAdapter adapter) {
    if (isTimerRunning) {
      if (adapter != null) {
        boolean success = adapter.moveSnake();
        GameController.display(adapter.getGame());
        if (!success) {
          stopTimer();
        }
      } else {
        stopTimer();
      }
    }
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
