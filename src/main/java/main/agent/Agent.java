package main.agent;

import ai.GameAdapter;
import game.Direction;
import javafx.animation.Timeline;
import main.State;

abstract class Agent {

  Timeline timeline;
  boolean isTimerRunning;
  State state;
  GameAdapter adapter;


  Agent(State state) {
    this.state = state;
  }

  protected Timeline startTimeline(int speed) {
    return null;
  }


  void stopTimer() {
    if (timeline != null) {
      timeline.stop();
    }
  }
}
