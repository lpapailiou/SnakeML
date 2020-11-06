package main.configuration;

public enum Mode {

  MANUAL("classic arcade mode (manual)", 200),
  NEURAL_NETWORK("neural network", 50);

  private String label;
  private int speed;

  Mode(String label, int speed) {
    this.label = label;
    this.speed = speed;
  }

  public String getLabel() {
    return label;
  }

  public int getSpeed() {
    return speed;
  }
}
