package main.configuration;

import java.util.function.Supplier;

import main.agent.Agent;
import main.agent.DemoAgent;
import main.agent.ManualAgent;
import main.agent.NeuralNetworkAgent;

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

  public String getLabel() {
    return label;
  }

  public Agent getAgent() {
    return agent.get().setSpeed(speed);
  }
}
