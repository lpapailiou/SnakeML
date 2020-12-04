package ui;

import ai.GameBatch;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
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
import main.State;
import main.agent.DemoAgent;
import main.agent.NeuralNetworkAgent;
import main.configuration.IApplicationConfigReader;
import main.configuration.Mode;

public class ApplicationController implements Initializable {

  @FXML
  private HBox rootElement;

  @FXML
  private GameController gameController;

  @FXML
  private ConfigController configController;

  private Stage stage;

  private State state = new State();
  private IApplicationConfigReader configuration = IApplicationConfigReader.getInstance();
  private static ApplicationController instance;
  private Timeline timeline;
  private Direction direction = configuration.getInitialDirection();
  private Scene scene;
  private boolean isRealtimeStatisticsVerbose = true;
  private int statisticsPosition;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    instance = this;
    state.addGameListener(e -> {
      gameController.display(state.getGame());
      if (configuration.getMode() == Mode.NEURAL_NETWORK) {
        configController.display(state.getGame().getDirection());
        if (isRealtimeStatisticsVerbose) {
          gameController.displayStats(state.getGenerationEntity(), state.getGame().getSnakeLength(),
              statisticsPosition);
        }
      }
    });
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
    direction = configuration.getInitialDirection();
    int speed = configuration.getMode().getSpeed();
    Game game = new Game();
    game.onGameOver(this::stopTimer);
    state.setGame(game);
    configController.setDisable(true);
    gameController.setDisplayedGame(game);

    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {

      game.changeDirection(direction);
      game.onTick();

    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void startNewNeuralNetwork() {
    GameBatch batch = new GameBatch(
        new NeuralNetwork(configuration.getRandomizationRate(), configuration.getLayerConfiguration())
    );

    configController.setDisable(true);
    TempStorage tempStorage = TempStorage.getInstance();
    tempStorage.addBatch(batch.getBatchEntity());

    timeline = new NeuralNetworkAgent(state, batch).startTimeline(configuration.getMode().getSpeed());
    timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == Status.STOPPED) {
        stopTimer();
      }
    });
  }


  private void startTrainedNeuralNetworkDemo() {
    configController.setDisable(true);
    configController.selectAllRadioButtons();
    timeline = new DemoAgent(state).startTimeline(configuration.getMode().getSpeed());
    timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == Status.STOPPED) {
        stopTimer();
      }
    });
  }

  private void stopTimer() {
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
    if (timeline != null && timeline.getStatus() == Status.STOPPED) {
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
