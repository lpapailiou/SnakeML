package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import game.element.Cell;
import game.element.Snake;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

class GameAdapter implements Comparable<GameAdapter> {

  private NeuralNetwork neuralNetwork;
  private Game game;
  private Set<Integer> nodeSelection;
  private boolean isGameOver = false;
  private GenerationEntity generationEntity;
  private long fitness;

  GameAdapter(NeuralNetwork net, GenerationEntity generationEntity) {
    INeuralNetworkConfig config = Config.getInstance();
    neuralNetwork = net;
    nodeSelection = config.getInputNodeSelection();
    this.generationEntity = generationEntity;
    game = new Game();
  }

  boolean moveSnake() {
    game.changeDirection(getDirection(game.snake, game.food));
    game.onTick();
    game.onGameOver(this::setGameOver);
    return !isGameOver;
  }

  Direction getDirection(Snake snake, Cell food) {
    if (food == null) {
      return Direction.values()[new Random().nextInt(4)];
    }

    int arrayIndex = 0;
    double[] inputValues = new double[nodeSelection.size()];
    for (Integer nodeIndex : nodeSelection) {
      inputValues[arrayIndex] = InputNode.values()[nodeIndex].getInput(snake, food);
      arrayIndex++;
    }
    List<Double> out = neuralNetwork.predict(inputValues);
    int maxIndex = out.indexOf(Collections.max(out));
    return Direction.values()[maxIndex];
  }

  void updateFitness() {
    if (fitness == 0) {
      fitness = game.snake.getFitness();
    }
  }

  long getFitness() {
    return fitness;
  }

  int getSnakeLength() {
    return game.snake.getBody().size();
  }

  private void setGameOver() {
    if (!isGameOver) {
      isGameOver = true;
      updateFitness();
      generationEntity.aggregateSnakeData(game.snake);
    }
  }

  NeuralNetwork getNeuralNetwork() {
    return neuralNetwork;
  }

  @Override
  public int compareTo(GameAdapter o) {
    if (this.fitness > o.fitness) {
      return 1;
    } else if (this.fitness < o.fitness) {
      return -1;
    }
    return 0;
  }

}
