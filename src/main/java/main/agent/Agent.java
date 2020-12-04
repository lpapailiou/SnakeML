package main.agent;

import ai.GameAdapter;
import javafx.animation.Timeline;
import main.State;

abstract class Agent {

  Timeline timeline;
  State state;
  GameAdapter adapter;


  Agent(State state) {
    this.state = state;
  }

  protected void startTimeline(int speed) {
  }


  void stopTimer() {
    if (timeline != null) {
      timeline.stop();
    }
  }
}
