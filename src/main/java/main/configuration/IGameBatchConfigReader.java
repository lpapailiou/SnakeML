package main.configuration;

public interface IGameBatchConfigReader {

  int getBoardWidth();

  int getBoardHeight();

  int[] getLayerConfiguration();

  int getGenerationCount();

  int getPopulationSize();

  double getRandomizationRate();

}
