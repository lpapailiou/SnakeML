package ai;

import ai.data.GenerationEntity;
import ai.neuralnet.NeuralNetwork;
import game.Direction;
import game.Game;
import game.Cell;
import game.Snake;
import main.configuration.IGameAdapterConfigReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameAdapter implements Comparable<GameAdapter> {

  private NeuralNetwork neuralNetwork;
  private Game game;
  private Set<Integer> nodeSelection;
  private boolean isGameOver = false;
  private GenerationEntity generationEntity;
  private long fitness;

  public GameAdapter(NeuralNetwork net, GenerationEntity generationEntity) {
    IGameAdapterConfigReader config = IGameAdapterConfigReader.getInstance();
    neuralNetwork = net;
    nodeSelection = config.getInputNodeSelection();
    this.generationEntity = generationEntity;
    game = new Game();
    game.onGameOver(this::setGameOver);
  }

  public GameAdapter(NeuralNetwork net) {
    this(net, null);
  }

  public void moveSnake() {
    game.changeDirection(determineNextDirection(game.getSnake(), game.getFood()));
    game.onTick();
  }

  public boolean isGameOver() {
    return isGameOver;
  }

  private Direction determineNextDirection(Snake snake, Cell food) {
    if (food == null) { // TODO: Refactor into GameWin Event?
      return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    // TODO: rewrite to stream api?
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

  private void updateFitness() {
    if (fitness == 0) {
      fitness = game.getSnake().getFitness();
    }
  }

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
