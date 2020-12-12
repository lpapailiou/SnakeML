package ai.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * This class is designed to collect and hold statistics data for the used configuration, so
 * statistics are traceable. Method names may not apply to the Google Java Coding Styleguide, as
 * they are optimized for the JSON parser by jackson.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConfigurationEntity {

  private int boardWidth;
  private int boardHeight;
  private String algorithm;
  private int populationSize;

  public void setBoardWidth(int boardWidth) {
    this.boardWidth = boardWidth;
  }

  public void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  public int getBoardWidth() {
    return boardWidth;
  }

  public int getBoardHeight() {
    return boardHeight;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public int getPopulationSize() {
    return populationSize;
  }

}
