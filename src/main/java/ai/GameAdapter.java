package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import game.element.Food;
import game.element.Snake;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameAdapter {

  private NeuralNetwork neuralNetwork;
  private Game game;
  private double[] inputValues = new double[12];
  private Set<Integer> nodeSelection = new HashSet<>();
  private boolean isGameOver = false;
  private GenerationEntity generationEntity;

  GameAdapter(NeuralNetwork net, GenerationEntity generationEntity) {
    neuralNetwork = net;
    this.generationEntity = generationEntity;
    game = new Game();
    for ( int i = 0; i < 12; i++) {
      nodeSelection.add(i);
    }
  }

  boolean moveSnake() {
    game.changeDirection(getDirection(game.snake, game.food));
    game.onTick();
    game.onGameOver(this::setGameOver);
    return !isGameOver;
  }

  Direction getDirection(Snake snake, Food food) {
    int arrayIndex = 0;
    for (Integer index : nodeSelection) {
      inputValues[arrayIndex] = InputNode.values()[index].getInput(snake, food);
      arrayIndex++;
    }
    List<Double> out = neuralNetwork.predict(inputValues);
    int maxIndex = out.indexOf(Collections.max(out));
    return Direction.values()[maxIndex];
  }

  long getFitness() {
    return game.snake.getFitness();
  }

  private synchronized void setGameOver() {
    synchronized (this) {
      try {
        generationEntity.aggregateSnakeData(game.snake);
      } catch (ArrayIndexOutOfBoundsException e) {
        e.printStackTrace();    // TODO: fix
      }
    }
    isGameOver = true;
  }

  public NeuralNetwork getNeuralNetwork() {
    return neuralNetwork;
  }

  int getSnakeLength() {
    return game.snake.getBody().size();
  }

}
