package main.configuration;

public interface IGenerationConfigReader {

  static IGenerationConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  int getPopulationSize();

}
