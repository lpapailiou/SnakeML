package ui.painter;

import ai.data.GenerationEntity;

/**
 * This interface provides a method to visualize aggregated statistics on the game panel. It may be
 * triggered only if according data is present, e.g. when the NeuralNetwork mode is active.
 */
public interface IGameInformationPainter {

  /**
   * This method triggers the visualisation of aggregated realitme statistics.
   *
   * @param entity      the aggregated GenerationEntity data wrapper instance
   * @param snakeLength the length of the current snake
   * @param position    the ID of the position the statistics should be displayed at
   */
  void paintGameInformation(GenerationEntity entity, int snakeLength, int position);

}
