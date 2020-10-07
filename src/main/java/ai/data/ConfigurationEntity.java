package ai.data;

public class ConfigurationEntity {

  private int board_width;
  private int board_height;
  private String algorithm;
  private int population_size;

  public ConfigurationEntity(int boardWith, int boardHeight, String algorithm, int populationSize) {
    this.board_width = boardWith;
    this.board_height = boardHeight;
    this.algorithm = algorithm;
    this.population_size = populationSize;
  }

  public int getBoard_width() {
    return board_width;
  }

  public int getBoard_height() {
    return board_height;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public int getPopulation_size() {
    return population_size;
  }
}
