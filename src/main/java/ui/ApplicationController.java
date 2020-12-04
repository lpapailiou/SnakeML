package ui;

import ai.GameBatch;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import main.State;
import main.agent.DemoAgent;
import main.agent.ManualAgent;
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
    state.addTimelineListener(e -> {
      stop();
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
      instance.configController.setDisable(true);
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
      instance.state.stopTimeline();
      instance.configController.setDisable(false);
    }
  }

  private void startManualGame() {
    state.setDirection(configuration.getInitialDirection());
    state.setGame(new Game());
    configuration.getMode().getAgent().setState(state).setSpeed(configuration.getMode().getSpeed()).build();
  }

  private void startNewNeuralNetwork() {
    GameBatch batch = new GameBatch(
        new NeuralNetwork(configuration.getRandomizationRate(), configuration.getLayerConfiguration())
    );
    TempStorage tempStorage = TempStorage.getInstance();
    tempStorage.addBatch(batch.getBatchEntity());
    configuration.getMode().getAgent().setState(state).setSpeed(configuration.getMode().getSpeed()).setGameBatch(batch).build();
  }


  private void startTrainedNeuralNetworkDemo() {
    configController.selectAllRadioButtons();
    configuration.getMode().getAgent().setState(state).setSpeed(configuration.getMode().getSpeed()).build();
  }

  private void listenToKeyboardEvents(Scene scene) {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
      switch (key.getCode()) {
        case W:
        case UP:
          state.setDirection(Direction.UP);
          break;

        case A:
        case LEFT:
          state.setDirection(Direction.LEFT);
          break;

        case S:
        case DOWN:
          state.setDirection(Direction.DOWN);
          break;

        case D:
        case RIGHT:
          state.setDirection(Direction.RIGHT);
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
    if (state.isTimelineRunning()) {
      stop();
    } else {
      start();
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
