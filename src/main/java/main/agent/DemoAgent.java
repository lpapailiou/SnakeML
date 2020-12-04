package main.agent;

import ai.GameAdapter;
import ai.data.storage.Serializer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.State;
import main.agent.Agent;

public class DemoAgent extends Agent {

  public DemoAgent(State state) {
    super(state);
    adapter = new GameAdapter(Serializer.load(), null);
    state.setGame(adapter.getGame());
  }

  @Override
  public Timeline startTimeline(int speed) {
    isTimerRunning = true;
    timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
      if (!isTimerRunning) {
        return;
      }
      if (adapter == null) {
        stopTimer();
        return;
      }
      adapter.moveSnake();
      if (adapter.isGameOver()) {
        stopTimer();
      }
    }));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    return timeline;
  }

}
