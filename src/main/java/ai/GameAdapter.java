package ai;


import ai.data.SnakeEntity;
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
  private List<SnakeEntity> snakeEntities;

  GameAdapter(NeuralNetwork net, List<SnakeEntity> snakeEntities) {
    neuralNetwork = net;
    this.snakeEntities = snakeEntities;
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
    //List<Double> out = net.learn(inputValues, null);
    int maxIndex = out.indexOf(Collections.max(out));

    Direction result;
    if (maxIndex == 0) {
      result = Direction.LEFT;
    } else if (maxIndex == 1) {
      result = Direction.RIGHT;
    } else if (maxIndex == 2) {
      result = Direction.UP;
    } else {
      result = Direction.DOWN;
    }

    return result;
  }

  long getFitness() {
    return game.snake.getFitness();
  }

  private synchronized void setGameOver() {
    Snake snake = game.snake;
    SnakeEntity snakeEntity = new SnakeEntity();
    snakeEntity.setSnakeLength(snake.getBody().size());
    snakeEntity.setSteps(snake.getSteps());
    snakeEntity.setCauseOfDeath(snake.getCauseOfDeath());
    snakeEntity.setFitness(snake.getFitness());
    synchronized (this) {
      try {
        snakeEntities.add(0, snakeEntity);
      } catch (ArrayIndexOutOfBoundsException e) {
        //e.printStackTrace();    // TODO: fix
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
