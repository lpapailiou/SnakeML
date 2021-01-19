package main.configuration;

import game.Cell;
import game.Direction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class holds the centralized parametrization of the application parameters. During runtime,
 * some designated parameters may be modified. From outside this package, it can be accessed only by
 * using a specific interface. The interfaces may overlap, in order to keep the access points as
 * specific as possible (interface segregation principle).
 */
public class Config implements IGameConfigReader, IMainConfigReader, ITestConfig, IConfigReader,
    IConfigWriter,
    IApplicationConfigReader, INodeConfigReader, IGenerationConfigReader, IGameBatchConfigReader,
    IGameAdapterConfigReader, ISnakeConfigReader,
    IPainterConfigReader, IAgentConfigReader {

  private static Config instance;
  private int boardWidth = 16;
  private int boardHeight = 12;
  private int initialSnakeSize = 3;
  private static final Direction INITIAL_DIRECTION = Direction.RIGHT;
  private Cell initialStartingPosition = new Cell(3, 3);

  private Theme theme = Theme.CLASSIC;
  private Mode mode = Mode.MANUAL;

  private int generationCount = 60;
  private int populationSize = 2000;
  private double randomizationRate = 0.8;
  private static final int NUM_INPUT_NODES = 12;
  private static final int NUM_OUTPUT_NODES = 4;
  private int[] layerConfiguration = {NUM_INPUT_NODES, 4, NUM_OUTPUT_NODES};
  private Set<Integer> inputNodeSelection = new HashSet<>();

  private Config() {
    for (int i = 0; i < layerConfiguration[0]; i++) {
      inputNodeSelection.add(i);
    }
  }

  static synchronized Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  /**
   * Setter for the chosen mode (in terms of game logic).
   *
   * @param mode the mode to-be-set
   */
  @Override
  public void setMode(Mode mode) {
    this.mode = mode;
  }

  /**
   * Returns the currently selected mode (in terms of game logic).
   *
   * @return the selected game mode
   */
  @Override
  public Mode getMode() {
    return mode;
  }

  /**
   * Returns the current board width.
   *
   * @return the board width
   */
  @Override
  public int getBoardWidth() {
    return boardWidth;
  }

  /**
   * Returns the current board height.
   *
   * @return the board height
   */
  @Override
  public int getBoardHeight() {
    return boardHeight;
  }

  /**
   * Setter for the board width.
   *
   * @param boardWidth the chosen board width
   */
  @Override
  public void setBoardWidth(int boardWidth) {
    this.boardWidth = boardWidth;
  }

  /**
   * Setter for the board height.
   *
   * @param boardHeight the chosen board height
   */
  @Override
  public void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }

  /**
   * Gets number of segments the Snake should be initialized with.
   *
   * @return the size of the initial Snake
   */
  @Override
  public int getInitialSnakeSize() {
    return initialSnakeSize;
  }

  /**
   * Gets the initial direction the Snake will move towards to.
   *
   * @return the direction to-go-to
   */
  @Override
  public Direction getInitialDirection() {
    return INITIAL_DIRECTION;
  }

  /**
   * Gets the initial starting coordinates for a new Snake (head).
   *
   * @return the initial starting coordinates
   */
  @Override
  public Cell getInitialStartingPosition() {
    return initialStartingPosition;
  }

  /**
   * Provides the currently selected theme.
   *
   * @return the current theme
   */
  @Override
  public Theme getTheme() {
    return theme;
  }

  /**
   * Sets the selected theme.
   *
   * @param theme the selected theme
   */
  @Override
  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  /**
   * Gets the currently set generation count.
   *
   * @return the generation count.
   */
  @Override
  public int getGenerationCount() {
    return generationCount;
  }

  /**
   * Sets the entered generation count.
   *
   * @param generationCount the selected generation count
   */
  @Override
  public void setGenerationCount(int generationCount) {
    this.generationCount = generationCount;
  }

  /**
   * Gets the currently set population size.
   *
   * @return the population size
   */
  @Override
  public int getPopulationSize() {
    return populationSize;
  }

  /**
   * Sets the entered population size.
   *
   * @param populationSize the selected generation size
   */
  @Override
  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  /**
   * Gets the currently set randomization rate.
   *
   * @return the randomization rate
   */
  @Override
  public double getRandomizationRate() {
    return randomizationRate;
  }

  /**
   * Sets the entered randomization rate.
   *
   * @param randomizationRate the selected randomization rate
   */
  @Override
  public void setRandomizationRate(double randomizationRate) {
    this.randomizationRate = randomizationRate;
  }

  /**
   * Returns the layer configuration as immutable integer array.
   *
   * @return the layer configuration
   */
  @Override
  public int[] getLayerConfiguration() {
    return layerConfiguration.clone();
  }

  /**
   * Returns the layer configuration as Integer List.
   *
   * @return the layer configuration
   */
  @Override
  public List<Integer> getLayerConfigurationAsList() {
    List<Integer> layerConfigurationList = new ArrayList<>();
    for (int j : layerConfiguration) {
      layerConfigurationList.add(j);
    }
    return layerConfigurationList;
  }

  /**
   * Sets the layer configuration according to the input array.
   *
   * @param layerConfiguration the chosen layer configuration
   */
  @Override
  public void setLayerConfiguration(int[] layerConfiguration) {
    this.layerConfiguration = layerConfiguration;
  }

  /**
   * Returns the input node selection as a list of Integer. The input nodes are represented as enum
   * ordinals.
   *
   * @return the input node selection
   */
  @Override
  public Set<Integer> getInputNodeSelection() {
    return new HashSet<>(inputNodeSelection);
  }

  /**
   * This method allows to add a selected input node by enum ordinal.
   *
   * @param selectedInputNode the ID of the selected input node
   */
  @Override
  public void addInputNode(int selectedInputNode) {
    this.inputNodeSelection.add(selectedInputNode);
  }

  /**
   * This method allows to remove a specific input node from the input node selection by ordinal.
   *
   * @param selectedInputNode the ID of the input node to-be-removed
   */
  @Override
  public void removeInputNodeFromSelection(int selectedInputNode) {
    this.inputNodeSelection.remove(selectedInputNode);
  }

  /**
   * Returns the timeout for the snake. If the steps of the snake exceed this number, the according
   * game will be terminated. This allows to avoid infinite loops in automated games.
   *
   * @return the timeout for snake steps
   */
  @Override
  public int getSnakeTimeout() {
    if (mode == Mode.MANUAL) {
      return Integer.MAX_VALUE;
    }
    return boardWidth * boardHeight;
  }
}
