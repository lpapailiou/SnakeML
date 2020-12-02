package main.configuration;

public interface ITestConfig {

  static ITestConfig getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  int getGenerationCount();

  void setGenerationCount(int generationCount);

  int getPopulationSize();

  void setPopulationSize(int populationSize);

  void setBoardWidth(int boardWidth);

  void setBoardHeight(int boardHeight);

  void setMode(Mode mode);

}
