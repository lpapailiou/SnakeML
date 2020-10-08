package ai.data;

import game.element.Snake;

public class GenerationEntity {

  private static Object lock = new Object();
  private int id;
  private int populationSize;
  private int minSnakeLength = Integer.MAX_VALUE;
  private double avgSnakeLength;
  private int maxSnakeLength;
  private int minSteps = Integer.MAX_VALUE;
  private double avgSteps;
  private int maxSteps;
  private int numberOfWallDeath;
  private int numberOfBodyDeath;
  private int numberOfTimeoutDeath;
  private long minFitness = Long.MAX_VALUE;
  private double avgFitness;
  private long maxFitness;

  public void aggregateSnakeData(Snake snake) {
    synchronized (lock) {
      populationSize++;
      int length = snake.getBody().size();
      avgSnakeLength = ((avgSnakeLength * (populationSize - 1)) + length) / populationSize;
      if (minSnakeLength > length) {
        minSnakeLength = length;
      } else if (maxSnakeLength < length) {
        maxSnakeLength = length;
      }
      int steps = snake.getSteps();
      avgSteps = ((avgSteps * (populationSize - 1)) + steps) / populationSize;
      if (minSteps > steps) {
        minSteps = steps;
      } else if (maxSteps < steps) {
        maxSteps = steps;
      }
      String deathCause = snake.getCauseOfDeath();
      if (deathCause.equals("wall")) {
        numberOfWallDeath++;
      } else if (deathCause.equals("body")) {
        numberOfBodyDeath++;
      } else {
        numberOfTimeoutDeath++;
      }

      long fitness = snake.getFitness();
      avgFitness = ((avgFitness * (populationSize - 1)) + fitness) / populationSize;
      if (minFitness > fitness) {
        minFitness = fitness;
      } else if (maxFitness < fitness) {
        maxFitness = fitness;
      }
    }
   }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getMin_snake_length() {
    return minSnakeLength;
  }

  public double getAvg_snake_length() {
    return avgSnakeLength;
  }

  public int getMax_snake_length() {
    return maxSnakeLength;
  }

  public int getMin_steps() {
    return minSteps;
  }

  public double getAvg_steps() {
    return avgSteps;
  }

  public int getMax_steps() {
    return maxSteps;
  }

  public int getNumber_of_wall_deaths() {
    return numberOfWallDeath;
  }

  public int getNumber_of_body_deaths() {
    return numberOfBodyDeath;
  }

  public int getNumber_of_timeout_deaths() {
    return numberOfTimeoutDeath;
  }

  public long getMin_fitness() {
    return minFitness;
  }

  public double getAvg_fitness() {
    return avgFitness;
  }

  public long getMax_fitness() {
    return maxFitness;
  }

}
