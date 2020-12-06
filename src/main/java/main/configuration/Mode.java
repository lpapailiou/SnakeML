package main.configuration;

import java.util.function.Supplier;

import main.agent.Agent;
import main.agent.DemoAgent;
import main.agent.ManualAgent;
import main.agent.NeuralNetworkAgent;

/**
 * This enum will provide a specific value per game mode.
 * It holds a Supplier with the according Agent subclass. If a game is started by
 * the user interface, this enum takes over the mode selection and starts
 * with game initialization.
 */
public enum Mode {

  MANUAL("classic arcade mode (manual)", 200, ManualAgent::new),
  NEURAL_NETWORK("neural network", 50, NeuralNetworkAgent::new),
  NEURAL_NETWORK_DEMO("trained neural network (demo)", 50, DemoAgent::new);

  private String label;
  private int speed;
  private Supplier<Agent> agent;

  Mode(String label, int speed, Supplier agent) {
    this.label = label;
    this.speed = speed;
    this.agent = agent;
  }

  /**
   * Gets a short description of the game mode.
   * @return the short description of the mode
   */
  public String getLabel() {
    return label;
  }

  /**
   * This method will create a new instance of the according Agent sub class with the mapped
   * speed property. The receiver may finish the build to start the game.
   * @return the prepared Agent instance according to selected mode
   */
  public Agent getAgent() {
    return agent.get().setSpeed(speed);
  }
}
