package game.element;

import game.Direction;
import java.util.LinkedList;
import java.util.List;
import main.Config;

import static main.Config.SNAKE_TIMEOUT;

public class Snake {

  protected LinkedList<Cell> body = new LinkedList<>();
  private int steps;
  private int timeout = SNAKE_TIMEOUT;
  private String causeOfDeath = "timeout";
  private boolean isTimedOut = false;

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
      if (timeout == 0) {
        isTimedOut = true;
        return;   //TODO: does not work like that
      }
      Cell snakeHead = body.getFirst();
      body.addFirst(new Cell(snakeHead.x + direction.x, snakeHead.y + direction.y));
      body.removeLast();
      steps++;
    }
  }

  public boolean isDead() {
    return isSnakeInWall() || isSnakeInItself() || isTimedOut;
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
    if (snakeHead.x > Config.NUMBER_OF_CELL_COLUMNS || snakeHead.x < 0) {
      causeOfDeath = "wall";
      return true;
    } else if (snakeHead.y > Config.NUMBER_OF_CELL_ROWS || snakeHead.y < 0) {
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
    if (snakeLength < 10) {
      return (long) (steps*steps * Math.pow(2, snakeLength));
    }
    return (long) (steps*steps*Math.pow(2, 10) * (snakeLength-9));
  }

  public Cell getHead() {
    return body.get(0);
  }

  public boolean isHeadAt(Cell foodPosition) {
    return foodPosition.x == body.get(0).x && foodPosition.y == body.get(0).y;
  }

  public void grow() {
    timeout = SNAKE_TIMEOUT;
    body.add(new Cell(-1, -1)); //TODO: fishy
  }
}
