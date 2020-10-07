package ai.data;

import java.util.List;

public class GenerationEntity {

  private int id;
  private List<SnakeEntity> snakes;

  public void setId(int id) {
    this.id = id;
  }

  public void setSnakes(List<SnakeEntity> snakes) {
    this.snakes = snakes;
  }

  public int getId() {
    return id;
  }

  public List<SnakeEntity> getSnakes() {
    return snakes;
  }

}
