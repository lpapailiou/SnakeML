package main.agent;

import ai.GameAdapter;
import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.State;

public class NeuralNetworkAgent extends Agent {

  private GameBatch batch;

  public NeuralNetworkAgent(State state, GameBatch batch) {   // TODO: or use default constructor and factory pattern, so instance can be provided by mode from producer?
    super(state);
    this.batch = batch;
  }

  @Override
  public Timeline startTimeline(int speed) {
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (adapter == null) {
        NeuralNetwork neuralNet = batch.processNewGeneration();
        if (neuralNet != null) {
          adapter = new GameAdapter(neuralNet, null);
          state.setGenerationEntity(batch.getCurrentGenerationEntity());
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
    return timeline;
  }

}
