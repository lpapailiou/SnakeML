package main.configuration;

import java.util.List;
import java.util.Set;

public interface INeuralNetworkConfig {

  int getBoardWidth();

  int getBoardHeight();

  int getGenerationCount();

  void setGenerationCount(int generationCount);

  int getPopulationSize();

  void setPopulationSize(int populationSize);

  double getRandomizationRate();

  void setRandomizationRate(double randomizationRate);

  int[] getLayerConfiguration();

  void setLayerConfiguration(int[] layerConfiguration);

  List<Integer> getLayerConfigurationAsList();

  int getInitialnputNodeCount();

  Set<Integer> getInputNodeSelection();

  void addInputNode(int selectedInputNode);

  void removeInputNodeFromSelection(int selectedInputNode);

  int getSnakeTimeout();
}
