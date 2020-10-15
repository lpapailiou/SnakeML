package game.element;

import game.Direction;
import java.util.LinkedList;
import java.util.List;
import main.configuration.Config;

public class Snake {

  protected LinkedList<Cell> body = new LinkedList<>();
  private int steps;
  private final int timeoutConstant = Config.getInstance().getSnakeTimeout();
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

  public void move(Direction direction) {
    if (!isDead()) {
      timeout--;
      Cell snakeHead = body.getFirst();
      body.addFirst(new Cell(snakeHead.x + direction.x, snakeHead.y + direction.y));
      body.removeLast();
      steps++;
    }
  }

  public boolean isDead() {
    return isSnakeInWall() || isSnakeInItself() || timeout < 1;
  }

  private boolean isSnakeInItself() {
    if (body.stream().distinct().count() < (long) body.size()) {
      causeOfDeath = "body";
      return true;
    }
    return false;
  }

  private boolean isSnakeInWall() {
    Cell snakeHead = body.get(0);
    if (snakeHead.x >= Config.getInstance().getBoardWidth() || snakeHead.x < 0) {
      causeOfDeath = "wall";
      return true;
    } else if (snakeHead.y >= Config.getInstance().getBoardHeight() || snakeHead.y < 0) {
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
    int boardHalf = (Config.getInstance().getBoardWidth() + Config.getInstance().getBoardHeight())/2;

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

  public void grow() {
    body.add(new Cell(-1, -1));
    timeout = body.size() == (Config.getInstance().getBoardWidth() * Config.getInstance().getBoardHeight()) ? 0 : timeoutConstant;
  }
}
