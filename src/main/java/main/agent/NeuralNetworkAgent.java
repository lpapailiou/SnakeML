package main.agent;

import ai.GameAdapter;
import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.State;
import main.agent.Agent;

public class NeuralNetworkAgent extends Agent {

  private GameBatch batch;

  public NeuralNetworkAgent(State state, GameBatch batch) {
    super(state);
    this.batch = batch;
  }

  @Override
  public Timeline startTimeline(int speed) {
    isTimerRunning = true;
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (!isTimerRunning) {
        return;
      }

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
