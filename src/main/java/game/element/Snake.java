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
    return (long) Math.pow(snakeLength, 3.7) + steps;   // looks less advanced, but works quite nicely <3

    //(((Math.pow(snakeLength, 3))) + Math.log(snakeLength) * (stepsPerSnake*stepsPerSnake));
/*
    if (snakeLength < (Config.getInstance().getBoardWidth()+Config.getInstance().getBoardHeight())/2) {
      return (long) ((stepsPerSnake*stepsPerSnake/2) * Math.pow(2, snakeLength));
    }
    return (long) ((Config.getInstance().getBoardWidth()*Config.getInstance().getBoardHeight() * Math.pow(2, snakeLength)));

    //return (long) ((stepsPerSnake*stepsPerSnake/2) * Math.pow(2, snakeLength));
    if (snakeLength < 10) {
      return (long) ((steps / snakeLength) * Math.pow(2, snakeLength));
    }
    return (long) (((steps / snakeLength) * Math.pow(2, snakeLength)) - stepsPerSnake);
    /*
    return (long) (steps + (Math.pow(2, snakeLength) + Math.pow(snakeLength, 2.1) * 500) - (Math.pow(snakeLength, 1.2) * Math.pow((0.25*steps), 1.3)));
    */
    /*
    // .................... original fitness function ....................
    if (snakeLength < 10) {
      return (long) (steps*steps * Math.pow(2, snakeLength));
    }
    return (long) (steps*steps*Math.pow(2, 10) * (snakeLength-9));
    // .................... original fitness function ....................
    */
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
