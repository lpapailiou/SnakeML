package main.configuration;

import game.element.Cell;
import game.Direction;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;

public class Config implements IGameConfig, IColorConfig, IFontConfig, INeuralNetworkConfig {

  private static Config instance;
  private double manualSpeedFactor = 1;
  private double botSpeedFactor = 1;
  private double cellWidth = 35;
  private int boardWidth = 16;
  private int boardHeight = 16;
  private int initialSnakeSize = 3;
  private Direction initialDirection = Direction.RIGHT;
  private Cell initialStartingPosition = new Cell(3,3);

  private ColorScheme colorScheme = ColorScheme.NORMAL;

  private double gameOverFontSize = 100;
  private double scoreFontSize = 30;
  private String gameOverText = "GAME OVER";    // TODO: font/text could be hardcoded?

  private int generationCount = 60;
  private int populationSize = 1000;
  private double randomizationRate = 0.2;
  private int[] layerConfiguration = {12, 16, 16, 12, 4};
  private int initialnputNodeCount = layerConfiguration[0];       // may be used for visual representation on gui
  private Set<Integer> inputNodeSelection = new HashSet<>();

  private Config() {
    for (int i = 0; i < initialnputNodeCount; i++) {
      inputNodeSelection.add(i);
    }
  }

  public static Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  @Override
  public double getManualSpeedFactor() {
    return manualSpeedFactor;
  }

  @Override
  public double getBotSpeedFactor() {
    return botSpeedFactor;
  }

  @Override
  public double getCellWidth() {
    return cellWidth;
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
  public void setBoardWith(int boardWith) {
    this.boardWidth = boardWith;
  }   // TODO: update cell dimensions

  @Override
  public void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }   // TODO: update cell dimensions

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
  public ColorScheme getColorScheme() {
    return colorScheme;
  }

  @Override
  public void setColorScheme(ColorScheme colorScheme) {
    this.colorScheme = colorScheme;
  }

  @Override
  public Color getSnakeBodyColor() {
    return colorScheme.getSnakeBodyColor();
  }

  @Override
  public Color getFoodColor() {
    return colorScheme.getFoodColor();
  }

  @Override
  public Color getBackgroundColor() {
    return colorScheme.getBackgroundColor();
  }

  @Override
  public Color getScoreFontColor() {
    return colorScheme.getScoreFontColor();
  }

  @Override
  public Color getGameOverFontColor() {
    return colorScheme.getGameOverFontColor();
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
  public void setLayerConfiguration(int[] layerConfiguration) {
    this.layerConfiguration = layerConfiguration;
  }

  @Override
  public int getInitialnputNodeCount() {
    return initialnputNodeCount;
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
    return boardWidth * boardHeight;
  }
}
