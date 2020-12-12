package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import game.Cell;
import game.Direction;
import game.Game;
import game.Snake;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import main.configuration.IGameAdapterConfigReader;

/**
 * This is an adapter class for game.Game. Its main purpose is to execute a game while implementing
 * machine learning by using a designated NeuralNetwork. Additionally, it will rate the game and
 * collect its data when a game over event occurs.
 */
public class GameAdapter implements Comparable<GameAdapter> {

  private NeuralNetwork neuralNetwork;
  private Game game;
  private Set<Integer> nodeSelection;
  private boolean isGameOver = false;
  private GenerationEntity generationEntity;
  private long fitness;

  /**
   * The constructor requires an instance of NeuralNetwork to be able to perform the machine
   * learning algorithm. As the collection of statistics is optional, the second parameter
   * GenerationEntity may be null.
   *
   * @param net              the NeuralNetwork used for this specific game
   * @param generationEntity the statistics data wrapper class to record data (optional)
   */
  GameAdapter(NeuralNetwork net, GenerationEntity generationEntity) {
    if (net == null) {
      throw new IllegalArgumentException("Neural network must no be null.");
    }
    IGameAdapterConfigReader config = IGameAdapterConfigReader.getInstance();
    neuralNetwork = net;
    nodeSelection = config.getInputNodeSelection();
    this.generationEntity = generationEntity;
    game = new Game();
    game.onGameOver(this::setGameOver);
  }

  /**
   * This constructor allows easy instantiation of a GameAdapter where no further statistics are
   * needed.
   *
   * @param net the NeuralNetwork used for this specific game
   */
  public GameAdapter(NeuralNetwork net) {
    this(net, null);
  }

  /**
   * This method coordinates the determination of the next direction, as well as the execution of
   * the according move of Snake.
   */
  public void moveSnake() {
    game.changeDirection(determineNextDirection(game.getSnake(), game.getFood()));
    game.onTick();
  }

  /**
   * This method informs if the game is over.
   *
   * @return true, if a game over occurred
   */
  public boolean isGameOver() {
    return isGameOver;
  }

  private Direction determineNextDirection(Snake snake, Cell food) {
    if (food == null) {
      return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    int arrayIndex = 0;
    double[] inputValues = new double[nodeSelection.size()];
    for (Integer nodeIndex : nodeSelection) {               // vision of snake: collects input data
      inputValues[arrayIndex] = InputNode.values()[nodeIndex].getInput(snake, food);
      arrayIndex++;
    }
    List<Double> out = neuralNetwork
        .predict(inputValues);  // evaluates input data with NeuralNetwork
    int maxIndex = out
        .indexOf(Collections.max(out));       // determines chosen Direction by choosing max index
    return Direction.values()[maxIndex];
  }

  private void updateFitness() {
    if (fitness == 0) {
      fitness = game.getSnake().getFitness();
    }
  }

  /**
   * Returns contained game instance.
   *
   * @return Game instance
   */
  public Game getGame() {
    return game;
  }

  long getFitness() {
    return fitness;
  }

  int getSnakeLength() {
    return game.getSnake().getBody().size();
  }

  private void setGameOver() {
    if (isGameOver) {
      return;
    }

    isGameOver = true;
    updateFitness();

    if (generationEntity != null) {
      generationEntity.aggregateSnakeData(game.getSnake());
    }
  }

  NeuralNetwork getNeuralNetwork() {
    return neuralNetwork;
  }

  /**
   * This method is used to sort processed games for further rating.
   * @param o the other GameAdapter for comparison
   * @return 1 if rated higher, -1 if rated lower, else 0
   */
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
