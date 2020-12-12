package main.agent;

import ai.GameAdapter;
import javafx.animation.Timeline;
import main.State;
import main.configuration.IAgentConfigReader;

/**
 * This - an abstract superclass of all agents - will serve as blueprint for logic needed to operate
 * a game. Every sub class will implement a specific mode and make use of the method provided here.
 * Agents are created with the builder patterns, as different modes may require different input data
 * as well as to add additional flexibility in implementation.
 */
public abstract class Agent {

  IAgentConfigReader config = IAgentConfigReader.getInstance();
  Timeline timeline;
  int speed;
  State state;
  GameAdapter adapter;

  /**
   * This method stub checks if the state is set or not. It is a base for further game logic
   * implementations within the sub classes.
   */
  public void build() {
    if (state == null) {
      throw new IncompleteStateException("incomplete build! state ist not set!");
    }
  }

  /**
   * A factory method to set the state.
   *
   * @param state the state to be feeded
   * @return this instance
   */
  public Agent setState(State state) {
    this.state = state;
    return this;
  }

  /**
   * A factory method to set the speed of game execution.
   *
   * @param speed the speed in millis
   * @return this instance
   */
  public Agent setSpeed(int speed) {
    if (speed < 0) {
      throw new IllegalArgumentException("speed must not be below 0!");
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
