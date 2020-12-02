package main.configuration;

public interface IConfigWriter {

  static IConfigWriter getInstance() {
    return Config.getInstance();
  }

  void setBoardWidth(int boardWidth);

  void setBoardHeight(int boardHeight);

  void setTheme(Theme theme);

  void setMode(Mode mode);

  void setLayerConfiguration(int[] networkParameter);

  void addInputNode(int value);

  void removeInputNodeFromSelection(int value);

  void setGenerationCount(int generationCount);

  void setPopulationSize(int populationSize);

  void setRandomizationRate(double randomizationRate);

}
