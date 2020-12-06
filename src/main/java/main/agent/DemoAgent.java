package main.agent;

import ai.GameAdapter;
import ai.data.storage.Serializer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DemoAgent extends Agent {

  /**
   * This is the implementation of the method specific game logic.
   * It will load a serialized NeuralNetwork, provide it to the state instance and start the game.
   */
  @Override
  public void build() {
    super.build();
    adapter = new GameAdapter(Serializer.load());
    state.setGame(adapter.getGame());
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {

      adapter.moveSnake();
      if (adapter.isGameOver()) {
        stopTimer();
      }

    }));

    timeline.setCycleCount(Timeline.INDEFINITE);
    state.setTimeline(timeline);
    timeline.play();
  }

}
