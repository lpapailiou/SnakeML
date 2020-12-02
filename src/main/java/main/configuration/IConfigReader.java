package main.configuration;

import java.util.List;

public interface IConfigReader {

  static IConfigReader getInstance() {
    return Config.getInstance();
  }

  int getBoardWidth();

  int getBoardHeight();

  Theme getTheme();

  Mode getMode();

  int[] getLayerConfiguration();

  List<Integer> getLayerConfigurationAsList();

  int getGenerationCount();

  int getPopulationSize();

  double getRandomizationRate();

}
