package ai.data;

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

  public int getBoard_width() {
    return boardWidth;
  }

  public int getBoard_height() {
    return boardHeight;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public int getPopulation_size() {
    return populationSize;
  }
}
