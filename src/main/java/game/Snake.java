package game;

import java.util.LinkedList;
import java.util.List;

import main.configuration.ISnakeConfigReader;

/**
 * This class implements the Snake. Core element is the 'snake body', implemented as LinkedList to
 * hold coordinates.
 */
public class Snake {

  protected LinkedList<Cell> body = new LinkedList<>();
  private ISnakeConfigReader config = ISnakeConfigReader.getInstance();
  private int steps;
  private boolean isDead = false;
  private final int timeoutConstant = config
      .getSnakeTimeout();     // timeout will kill Snake to avoid endless loops in automated games
  private int timeout = timeoutConstant;
  private String causeOfDeath = "timeout";

  /**
   * The Snake initialization is designed to allow control about starting conditions of a game. All
   * required inputs are received by parameters.
   *
   * @param size                the initial size of the Snake. at least 1
   * @param initialDirection    the initial Direction of the next move. must not be null
   * @param initialHeadPosition the initial position of the Snake head as coordinate. must not be null
   */
  public Snake(int size, Direction initialDirection, Cell initialHeadPosition) {
    if (size < 1) {
      throw new IllegalArgumentException("Snake length must be at least 1.");
    } else if (initialDirection == null) {
      throw new IllegalArgumentException("Initial direction must not be null.");
    } else if (initialHeadPosition == null) {
      throw new IllegalArgumentException("initial position must not be null.");
    }
    andGodSaidLetThereBeSnake(size, initialDirection, initialHeadPosition);
  }

  private void andGodSaidLetThereBeSnake(int size, Direction initialDirection,
      Cell initialHeadPosition) {
    for (int i = 0; i < size; i++) {
      body.add(new Cell(initialHeadPosition.x - i * initialDirection.x,
          initialHeadPosition.y - i * initialDirection.y));
    }
  }

  void move(Direction direction, Cell food) {
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
        timeout =
            body.size() == (config.getBoardWidth() * config.getBoardHeight()) ? 0 : timeoutConstant;
      }
      steps++;
    }
  }

  /**
   * Method to retrieve the fitness of the Snake, in order to rate the game. The higher the rating,
   * the better is the game considered. Tis fitness function will provide two strategies: - if the
   * Snake is short/immature, it will be rated higher if more steps where made. This will allow the
   * Snake to explore the 'game board', learn that walls are bad and give room to learn that 'food'
   * is good. - if the Snake is longer, the rating will increase for food. On the same time, the
   * rating is decreased for every step made. This will lead the Snake to go for a more efficient
   * strategy.
   *
   * @return the fitness of the Snake
   */
  public long getFitness() {
    int snakeLength = body.size();
    int boardHalf = (config.getBoardWidth() + config.getBoardHeight()) / 2;

    if (snakeLength < boardHalf * 1.5) {
      return (long) Math.pow(snakeLength, 3.7) + steps;
    }
    return (long) (Math.pow(snakeLength, 4.7) - (steps / snakeLength));
  }

  private boolean isSnakeInItself(Cell cell) {
    if (body.stream().anyMatch(c -> c.equals(cell))) {
      causeOfDeath = "body";
      return true;
    }
    return false;
  }

  private boolean isSnakeInWall(Cell cell) {
    if ((cell.x >= config.getBoardWidth() || cell.x < 0) || (cell.y >= config.getBoardHeight()
        || cell.y < 0)) {
      causeOfDeath = "wall";
      return true;
    }
    return false;
  }

  /**
   * Method to retrieve body (list of coordinates).
   *
   * @return the body of Snake
   */
  public List<Cell> getBody() {
    return body;
  }

  /**
   * Method to retrieve the number of moves done by the Snake.
   *
   * @return the steps of the Snake
   */
  public int getSteps() {
    return steps;
  }

  /**
   * Method to retrieve the reason why a game was designated to end.
   *
   * @return the cause of death of the Snake
   */
  public String getCauseOfDeath() {
    return causeOfDeath;
  }

  /**
   * This method returns the current position of the Snake head as coordinate
   *
   * @return the Snake head coordinate
   */
  public Cell getHead() {
    return body.get(0);
  }

  boolean isHeadAt(Cell foodPosition) {
    return body.get(0).equals(foodPosition);
  }

  /**
   * Informs if the Snake is alive or not.
   *
   * @return true, if Snake is dead
   */
  public boolean isDead() {
    return isDead;
  }

}
