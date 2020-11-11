package ui;

import ai.GameAdapter;
import ai.GameBatch;
import ai.data.GenerationEntity;
import ai.data.storage.Serializer;
import ai.neuralnet.NeuralNetwork;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import main.configuration.Config;
import main.configuration.IApplicationConfigReader;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

  @FXML
  private HBox rootElement;

  private Stage stage;
  private IApplicationConfigReader config = Config.getApplicationConfigReader();
  private static ApplicationController instance;
  private Timeline timeline;
  private boolean isTimerRunning = false;
  private Direction direction = config.getInitialDirection();
  private GameAdapter adapter;
  private Scene scene;
  private boolean isRealtimeStatisticsVerbose = true;
  private int statisticsPosition;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    rootElement.sceneProperty().addListener(n -> {
      if (n != null) {
        this.scene = rootElement.getScene();
        listenToKeyboardEvents(rootElement.getScene());
      }
    });
  }

  static void start() {
    if (instance != null) {
      switch (instance.config.getMode()) {
        case MANUAL:
          instance.setupManualTimer();
          break;
        case NEURAL_NETWORK:
          instance.setupNeuralNetworkTimer();
          break;
        case NEURAL_NETWORK_DEMO:
          instance.setupNeuralNetworkDemoTimer();
          break;
      }
    }
  }

  static void stop() {
    if (instance != null) {
      instance.stopTimer();
    }
  }

  private void setupManualTimer() {
    isTimerRunning = true;
    ConfigController.disableInputs(true);
    direction = config.getInitialDirection();
    int speed = config.getMode().getSpeed();
    Game game = new Game();
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (isTimerRunning) {
        game.changeDirection(direction);
        game.onTick();
        GameController.display(game);
        game.onGameOver(this::stopTimer);
      }

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void setupNeuralNetworkTimer() {
    isTimerRunning = true;
    ConfigController.disableInputs(true);
    int speed = config.getMode().getSpeed();
    adapter = null;
    GameBatch batch = new GameBatch(new NeuralNetwork(config.getRandomizationRate(), config.getLayerConfiguration()));
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (isTimerRunning) {
        if (adapter == null) {
          NeuralNetwork neuralNet = batch.processGeneration();
          if (neuralNet != null) {
            adapter = new GameAdapter(neuralNet, null);
          }
        }
        if (adapter != null) {
          boolean success = adapter.moveSnake();
          GenerationEntity entity;
          entity = batch.getCurrentGenerationEntity();
          GameController.display(adapter.getGame());
          if (isRealtimeStatisticsVerbose) {
            GameController.displayStats(entity, adapter.getSnakeLength(), statisticsPosition);
          }
          ConfigController.display( adapter.getGame().getDirection());
          if (!success) {
            adapter = null;
          }
        } else {
          ConfigController.enableStatistics();
          stopTimer();
        }
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void setupNeuralNetworkDemoTimer() {
    isTimerRunning = true;
    ConfigController.disableInputs(true);
    ConfigController.selectAllRadioButtons();           // TODO: fix if possible; necessary as otherwise neural net exception would be thrown
    int speed = config.getMode().getSpeed();
    adapter = new GameAdapter(Serializer.load(), null);
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
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
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
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

        case V:
          isRealtimeStatisticsVerbose = !isRealtimeStatisticsVerbose;
          break;

        case P:
          statisticsPosition = statisticsPosition > 4 ? 0 : ++statisticsPosition;
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

  static Scene getScene() {
    if (instance != null) {
      return instance.scene;
    }
    return null;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  static Stage getStage() {
    if (instance != null) {
      return instance.stage;
    }
    return null;
  }

}
