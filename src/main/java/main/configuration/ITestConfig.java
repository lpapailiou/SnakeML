package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters. It
 * may used in the unit test cases.
 */
public interface ITestConfig {

  /**
   * This method allows access to all methods provided in the ITestConfig interface.
   *
   * @return ITestConfig
   */
  static ITestConfig getInstance() {
    return Config.getInstance();
  }

  /**
   * Returns the current board width.
   *
   * @return the board width
   */
  int getBoardWidth();

  /**
   * Returns the current board height.
   *
   * @return the board height
   */
  int getBoardHeight();

  /**
   * Gets the currently set generation count.
   *
   * @return the generation count.
   */
  int getGenerationCount();

  void setGenerationCount(int generationCount);

  /**
   * Gets the currently set population size.
   *
   * @return the population size
   */
  int getPopulationSize();

  void setPopulationSize(int populationSize);

  /**
   * Setter for the board width.
   *
   * @param boardWidth the chosen board width
   */
  void setBoardWidth(int boardWidth);

  /**
   * Setter for the board height.
   *
   * @param boardHeight the chosen board height
   */
  void setBoardHeight(int boardHeight);

  void setMode(Mode mode);

  /**
   * Returns the currently selected mode (in terms of game logic).
   *
   * @return the selected game mode
   */
  Mode getMode();

}
