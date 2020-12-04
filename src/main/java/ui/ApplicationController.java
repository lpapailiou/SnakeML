package ui;

import ai.GameAdapter;
import ai.GameBatch;
import ai.data.GenerationEntity;
import ai.data.storage.Serializer;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import java.net.URL;
import java.util.ResourceBundle;
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

public class ApplicationController implements Initializable {

  @FXML
  private HBox rootElement;

  @FXML
  private GameController gameController;

  @FXML
  private ConfigController configController;

  private Stage stage;

  private IApplicationConfigReader configuration = IApplicationConfigReader.getInstance();
  private static ApplicationController instance;
  private Timeline timeline;
  private boolean isTimerRunning = false;
  private Direction direction = configuration.getInitialDirection();
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
      switch (instance.configuration.getMode()) {
        case MANUAL:
          instance.startManualGame();
          break;
        case NEURAL_NETWORK:
          instance.startNewNeuralNetwork();
          break;
        case NEURAL_NETWORK_DEMO:
          instance.startTrainedNeuralNetworkDemo();
          break;
      }
    }
  }

  static void stop() {
    if (instance != null) {
      instance.stopTimer();
    }
  }

  private void startManualGame() {
    isTimerRunning = true;
    direction = configuration.getInitialDirection();
    int speed = configuration.getMode().getSpeed();
    Game game = new Game();

    configController.setDisable(true);
    game.onGameOver(this::stopTimer);
    gameController.setDisplayedGame(game);

    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (!isTimerRunning) {
        return;
      }

      game.changeDirection(direction);
      game.onTick();
      gameController.display(game);

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void startNewNeuralNetwork() {
    isTimerRunning = true;
    int speed = configuration.getMode().getSpeed();
    adapter = null;
    GameBatch batch = new GameBatch(
        new NeuralNetwork(configuration.getRandomizationRate(), configuration.getLayerConfiguration())
    );

    configController.setDisable(true);
    TempStorage tempStorage = TempStorage.getInstance();
    tempStorage.addBatch(batch.getBatchEntity());

    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (!isTimerRunning) {
        return;
      }

      if (adapter == null) {
        NeuralNetwork neuralNet = batch.processNewGeneration();
        if (neuralNet != null) {
          adapter = new GameAdapter(neuralNet, null);
        }
      }

      if (adapter == null) {
        stopTimer();

        return;
      }

      adapter.moveSnake();
      GenerationEntity entity;
      entity = batch.getCurrentGenerationEntity();
      gameController.display(adapter.getGame());
      if (isRealtimeStatisticsVerbose) {
        gameController.displayStats(entity, adapter.getSnakeLength(), statisticsPosition);
      }
      configController.display(adapter.getGame().getDirection());
      if (adapter.isGameOver()) {
        adapter = null;
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }


  private void startTrainedNeuralNetworkDemo() {
    isTimerRunning = true;
    configController.setDisable(true);
    configController
        .selectAllRadioButtons(); // TODO: fix if possible; necessary as otherwise neural net exception would be thrown
    int speed = configuration.getMode().getSpeed();
    adapter = new GameAdapter(Serializer.load(), null);
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (!isTimerRunning) {

        return;
      }
      if (adapter == null) {
        stopTimer();

        return;
      }
      adapter.moveSnake();
      gameController.display(adapter.getGame());
      if (adapter.isGameOver()) {
        stopTimer();
      }
    }));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void stopTimer() {
    isTimerRunning = false;
    configController.setDisable(false);
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
          toggleGame();
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

  private void toggleGame() {
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
