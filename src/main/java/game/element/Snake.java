package game.element;

import game.Direction;
import java.util.LinkedList;
import java.util.List;
import main.Config;

public class Snake {

  static LinkedList<Cell> body = new LinkedList<>();

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
    body.removeLast();

    Cell snakeHead = body.getFirst();
    body.addFirst(new Cell(snakeHead.x + direction.x, snakeHead.y + direction.y));
  }

  public boolean isDead() {
    return isSnakeInWall() || isSnakeInItself();
  }

  private boolean isSnakeInItself() {
    return body.stream().distinct().count() < (long) body.size();
  }

  private boolean isSnakeInWall() {
    Cell snakeHead = body.get(0);
    if (snakeHead.x > Config.NUMBER_OF_CELL_COLUMNS || snakeHead.x < 0) {
      return true;
    }

    return snakeHead.y > Config.NUMBER_OF_CELL_ROWS || snakeHead.y < 0;
  }


<<<<<<< HEAD
  public List<Cell> getBody() {
    return body;
=======
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
>>>>>>> e3027e5ed19fb599ad0a954e6a5c447c8fd3b1ef
  }

  public boolean isHeadAt(Cell foodPosition) {
    return foodPosition.x == body.get(0).x && foodPosition.y == body.get(0).y;
  }

  public void grow() {
    body.add(new Cell(-1, -1));
  }
}
