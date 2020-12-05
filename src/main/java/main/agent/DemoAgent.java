package main.agent;

import ai.GameAdapter;
import ai.data.storage.Serializer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DemoAgent extends Agent {

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
    timeline.play();
    state.setTimeline(timeline);
  }

}
