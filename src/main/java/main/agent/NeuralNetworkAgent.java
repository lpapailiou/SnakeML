package main.agent;

import ai.GameAdapter;
import ai.GameBatch;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class NeuralNetworkAgent extends Agent {

  private GameBatch gameBatch;

  /**
   * This is the implementation of the method specific game logic. It will load the current
   * configuration, create an according NeuralNetwork and set up batch games to be processed in the
   * background. The batch consists of multiple generations. After each generation is processed, the
   * 'resulting' NeuralNetwork will be extracted. It is used to create a new game, which is provided
   * to the state instance. This is the game which will be visualized on the user interface.
   */
  @Override
  public void build() {
    super.build();
    GameBatch batch = new GameBatch(
        new NeuralNetwork(config.getRandomizationRate(), config.getLayerConfiguration())
    );
    this.setGameBatch(batch);
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (adapter == null) {
        NeuralNetwork neuralNet = gameBatch.processNewGeneration();
        if (neuralNet != null) {
          adapter = new GameAdapter(neuralNet);
          state.setGenerationEntity(gameBatch.getCurrentGenerationEntity());
          state.setGame(adapter.getGame());
        }
      }

      if (adapter == null) {
        stopTimer();
        return;
      }

      adapter.moveSnake();

      if (adapter.isGameOver()) {
        adapter = null;
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    state.setTimeline(timeline);
    timeline.play();
  }

  private void setGameBatch(GameBatch gameBatch) {
    this.gameBatch = gameBatch;
    TempStorage tempStorage = TempStorage.getInstance();
    tempStorage.addBatch(gameBatch.getBatchEntity());
  }

}
