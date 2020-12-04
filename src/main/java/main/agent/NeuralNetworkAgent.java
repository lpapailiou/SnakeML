package main.agent;

import ai.GameAdapter;
import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class NeuralNetworkAgent extends Agent {

  private GameBatch gameBatch;

  @Override
  public void build() {
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (adapter == null) {
        NeuralNetwork neuralNet = gameBatch.processNewGeneration();
        if (neuralNet != null) {
          adapter = new GameAdapter(neuralNet, null);
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
    timeline.play();
    state.setTimeline(timeline);
  }

  public Agent setGameBatch(GameBatch gameBatch) {
    this.gameBatch = gameBatch;
    return this;
  }

}
