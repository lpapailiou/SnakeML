package game.element;

import game.Direction;
import java.util.LinkedList;
import java.util.List;
import main.configuration.Config;
import main.configuration.ISnakeConfigReader;

public class Snake {

  protected LinkedList<Cell> body = new LinkedList<>();   // TODO: is protected because of test, solve differently?
  private ISnakeConfigReader config = Config.getSnakeConfigReader();
  private int steps;
  private boolean isDead = false;
  private final int timeoutConstant = config.getSnakeTimeout();
  private int timeout = timeoutConstant;
  private String causeOfDeath = "timeout";

  public Snake(int size, Direction initialDirection, Cell initialHeadPosition) {
    andGodSaidLetThereBeSnake(size, initialDirection, initialHeadPosition);
  }

  private void andGodSaidLetThereBeSnake(int size, Direction initialDirection,
      Cell initialHeadPosition) {
    for (int i = 0; i < size; i++) {
      body.add(new Cell(initialHeadPosition.x - i * initialDirection.x,
          initialHeadPosition.y - i * initialDirection.y));
    }
  }

  public void move(Direction direction, Cell food) {
    if (!isDead()) {
      timeout--;
      Cell snakeHead = body.getFirst();
      Cell newSegment = new Cell(snakeHead.x + direction.x, snakeHead.y + direction.y);
      if (isSnakeInWall(newSegment) || isSnakeInItself(newSegment) || timeout < 1) {
        isDead = true;
        return;
      }
      body.addFirst(newSegment);
      if (!newSegment.equals(food)) {
        body.removeLast();
      } else {
        timeout = body.size() == (config.getBoardWidth() * config.getBoardHeight()) ? 0 : timeoutConstant;
      }
      steps++;
    }
  }

  public boolean isDead() {
    return isDead;
  }

  private boolean isSnakeInItself(Cell cell) {
    if (body.stream().anyMatch(c -> c.equals(cell))) {
      causeOfDeath = "body";
      return true;
    }
    return false;
  }

  private boolean isSnakeInWall(Cell cell) {
    if ((cell.x >= config.getBoardWidth() || cell.x < 0) || (cell.y >= config.getBoardHeight() || cell.y < 0)) {
      causeOfDeath = "wall";
      return true;
    }
    return false;
  }

  public List<Cell> getBody() {
    return body;
  }

  public int getSteps() {
    return steps;
  }

  public String getCauseOfDeath() {
    return causeOfDeath;
  }

  public long getFitness() {
    int snakeLength = body.size();
    int boardHalf = (config.getBoardWidth() + config.getBoardHeight())/2;

    if (snakeLength < boardHalf * 1.5) {
      return (long) Math.pow(snakeLength, 3.7) + steps;
    }
    return (long) (Math.pow(snakeLength, 4.7) - (steps/snakeLength));
  }

  public Cell getHead() {
    return body.get(0);
  }

  public boolean isHeadAt(Cell foodPosition) {
    return body.get(0).equals(foodPosition);
  }

}
