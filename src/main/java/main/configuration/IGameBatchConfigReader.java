package main.configuration;

public interface IGameBatchConfigReader {

  static IGameBatchConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  int[] getLayerConfiguration();

  int getGenerationCount();

  int getPopulationSize();

  double getRandomizationRate();

}
