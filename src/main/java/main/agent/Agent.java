package main.agent;

import ai.GameAdapter;
import ai.GameBatch;
import javafx.animation.Timeline;
import main.State;

public abstract class Agent {

  Timeline timeline;
  int speed;
  State state;
  GameAdapter adapter;

  public void build() {
    if (state == null) {
      throw new RuntimeException("incomplete build! state ist not set!");
    }
  }

  public Agent setState(State state) {
    this.state = state;
    return this;
  }

  public Agent setSpeed(int speed) {
    this.speed = speed;
    return this;
  }

  public Agent setGameBatch(GameBatch gameBatch) {
    return this;
  }

  void stopTimer() {
    if (timeline != null) {
      timeline.stop();
    }
  }
}
