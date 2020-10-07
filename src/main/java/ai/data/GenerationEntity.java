package ai.data;

import java.util.List;

public class GenerationEntity {

  private int id;
  private List<SnakeEntity> snakes;

  public GenerationEntity(int id, List<SnakeEntity> snakes) {
    this.id = id;
    this.snakes = snakes;
  }

  public int getId() {
    return id;
  }

  public List<SnakeEntity> getSnakes() {
    return snakes;
  }

}
