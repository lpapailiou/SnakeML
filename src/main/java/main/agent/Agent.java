package main.agent;

import ai.GameAdapter;
import javafx.animation.Timeline;
import main.State;
import main.configuration.IAgentConfigReader;

public abstract class Agent {

  IAgentConfigReader config = IAgentConfigReader.getInstance();
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
    if (speed < 0) {
      throw new IllegalArgumentException("speed must not be below null!");
    }
    this.speed = speed;
    return this;
  }

  void stopTimer() {
    if (timeline != null) {
      timeline.stop();
    }
  }
}
