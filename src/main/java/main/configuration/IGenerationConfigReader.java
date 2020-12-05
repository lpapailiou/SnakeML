package main.configuration;

public interface IGenerationConfigReader {

  static IGenerationConfigReader getInstance() {
    return Config.getInstance();
  }

  int getPopulationSize();

}
