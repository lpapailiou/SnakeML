package main.configuration;

/**
 * This interface is designed to allow access to very specific global configuration parameters.
 * It is used in the ConfigController class for writing access.
 */
public interface IConfigWriter {

  /**
   * This method allows access to all methods provided in the IConfigWriter interface.
   * @return IConfigWriter
   */
  static IConfigWriter getInstance() {
    return Config.getInstance();
  }

  /**
   * Setter for the board width.
   * @param boardWidth the chosen board width
   */
  void setBoardWidth(int boardWidth);

  /**
   * Setter for the board height.
   * @param boardHeight the chosen board height
   */
  void setBoardHeight(int boardHeight);

  /**
   * Sets the selected theme.
   * @param theme the selected theme
   */
  void setTheme(Theme theme);

  /**
   * Setter for the chosen mode (in terms of game logic).
   * @param mode the mode to-be-set
   */
  void setMode(Mode mode);

  /**
   * Sets the layer configuration according to the input array.
   * @param layerConfiguration the chosen layer configuration
   */
  void setLayerConfiguration(int[] layerConfiguration);

  /**
   * This method allows to add a selected input node by enum ordinal.
   * @param selectedInputNode the ID of the selected input node
   */
  void addInputNode(int selectedInputNode);

  /**
   * This method allows to remove a specific input node from the input node selection by ordinal.
   * @param selectedInputNode the ID of the input node to-be-removed
   */
  void removeInputNodeFromSelection(int selectedInputNode);

  /**
   * Sets the entered generation count.
   * @param generationCount the selected generation count
   */
  void setGenerationCount(int generationCount);

  /**
   * Sets the entered population size.
   * @param populationSize the selected generation size
   */
  void setPopulationSize(int populationSize);

  /**
   * Sets the entered randomization rate.
   * @param randomizationRate the selected randomization rate
   */
  void setRandomizationRate(double randomizationRate);

}
