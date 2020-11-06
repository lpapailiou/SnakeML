package main.configuration;

import game.element.Cell;
import game.Direction;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config implements IGameConfig, IColorConfig, IFontConfig, INeuralNetworkConfig {

  private static Config instance;
  private int boardWidth = 8;
  private int boardHeight = 8;
  private int initialSnakeSize = 3;
  private Direction initialDirection = Direction.RIGHT;
  private Cell initialStartingPosition = new Cell(3,3);

  private Theme theme = Theme.CLASSIC;
  private Mode mode = Mode.MANUAL;

  private double gameOverFontSize = 100;
  private double scoreFontSize = 30;
  private String gameOverText = "GAME OVER";    // TODO: font/text could be hardcoded?

  private int generationCount = 60;
  private int populationSize = 1000;
  private double randomizationRate = 0.8;
  private final int inputNodes = 12;
  private final int outputNodes = 4;
  private int[] layerConfiguration = {inputNodes, 16, outputNodes};
  private Set<Integer> inputNodeSelection = new HashSet<>();

  private Config() {
    for (int i = 0; i < layerConfiguration[0]; i++) {
      inputNodeSelection.add(i);
    }
  }

  public static Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public Mode getMode() {
    return mode;
  }

  @Override
  public int getBoardWidth() {
    return boardWidth;
  }

  @Override
  public int getBoardHeight() {
    return boardHeight;
  }

  @Override
  public void setBoardWidth(int boardWidth) {
    this.boardWidth = boardWidth;
  }

  @Override
  public void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }

  @Override
  public int getInitialSnakeSize() {
    return initialSnakeSize;
  }

  @Override
  public Direction getInitialDirection() {
    return initialDirection;
  }

  @Override
  public Cell getInitialStartingPosition() {
    return initialStartingPosition;
  }

  @Override
  public Theme getTheme() {
    return theme;
  }

  @Override
  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  @Override
  public Color getSnakeBodyColor() {
    return theme.getSnakeBodyColor();
  }

  @Override
  public Color getFoodColor() {
    return theme.getFoodColor();
  }

  @Override
  public Color getBackgroundColor() {
    return theme.getBackgroundColor();
  }

  @Override
  public Color getScoreFontColor() {
    return theme.getScoreFontColor();
  }

  @Override
  public Color getGameOverFontColor() {
    return theme.getGameOverFontColor();
  }

  @Override
  public double getGameOverFontSize() {
    return gameOverFontSize;
  }

  @Override
  public double getScoreFontSize() {
    return scoreFontSize;
  }

  @Override
  public String getGameOverText() {
    return gameOverText;
  }

  @Override
  public int getGenerationCount() {
    return generationCount;
  }

  @Override
  public void setGenerationCount(int generationCount) {
    this.generationCount = generationCount;
  }

  @Override
  public int getPopulationSize() {
    return populationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  @Override
  public double getRandomizationRate() {
    return randomizationRate;
  }

  @Override
  public void setRandomizationRate(double randomizationRate) {
    this.randomizationRate = randomizationRate;
  }

  @Override
  public int[] getLayerConfiguration() {
    return layerConfiguration.clone();
  }

  @Override
  public List<Integer> getLayerConfigurationAsList() {
    List<Integer> layerConfigurationList = new ArrayList<>();
    for (int i = 0; i < layerConfiguration.length; i++) {
      layerConfigurationList.add(new Integer(layerConfiguration[i]));
    }
    return layerConfigurationList;
  }

  @Override
  public void setLayerConfiguration(int[] layerConfiguration) {
    this.layerConfiguration = layerConfiguration;
  }

  @Override
  public Set<Integer> getInputNodeSelection() {
    return  new HashSet<>(inputNodeSelection);
  }

  @Override
  public void addInputNode(int selectedInputNode) {
    this.inputNodeSelection.add(selectedInputNode);
  }

  @Override
  public void removeInputNodeFromSelection(int selectedInputNode) {
    this.inputNodeSelection.remove(new Integer(selectedInputNode));
  }

  @Override
  public int getSnakeTimeout() {
    if (mode == Mode.MANUAL) {
      return Integer.MAX_VALUE;
    }
    return boardWidth * boardHeight;
  }
}
