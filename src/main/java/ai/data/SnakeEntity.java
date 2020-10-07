package ai.data;

public class SnakeEntity {

  private int snakeLength;
  private int steps;
  private long fitness;
  private String causeOfDeath;

  public void setSnakeLength(int snakeLength) {
    this.snakeLength = snakeLength;
  }

  public void setSteps(int steps) {
    this.steps = steps;
  }

  public void setFitness(long fitness) {
    this.fitness = fitness;
  }

  public void setCauseOfDeath(String causeOfDeath) {
    this.causeOfDeath = causeOfDeath;
  }

  public int getSnake_length() {
    return snakeLength;
  }

  public int getSteps() {
    return steps;
  }

  public long getFitness() {
    return fitness;
  }

  public String getCause_of_death() {
    return causeOfDeath;
  }

}
