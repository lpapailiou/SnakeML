package game;

import static main.Config.INITIAL_DIRECTION;
import static main.Config.INITIAL_SNAKE_SIZE;
import static main.Config.INITIAL_STARTING_POINT;
import static main.Config.NUMBER_OF_CELL_COLUMNS;
import static main.Config.NUMBER_OF_CELL_ROWS;

import game.element.Cell;
import game.element.Food;
import game.element.Snake;
import game.event.GameOverConsumer;
import game.event.TickAware;
import java.util.ArrayList;
import java.util.Random;

public class Game implements TickAware {

  private final ArrayList<GameOverConsumer> gameOverConsumers = new ArrayList<>();

  Direction nextDirection = INITIAL_DIRECTION;
  public Snake snake = new Snake(INITIAL_SNAKE_SIZE, INITIAL_DIRECTION, INITIAL_STARTING_POINT);
  public Food food;
  Random rand = new Random();

  public Game() {
    food = spawnFood();
  }

  public void changeDirection(Direction nextDirection) {
    this.nextDirection = nextDirection;
  }

  @Override
  public void onTick() {
    snake.move(nextDirection);

    if (snake.isDead()) {
      this.emitGameOver();
      return;
    }

    if (snake.isHeadAt(food.position)) {
      snake.grow();
      food = spawnFood();
    }
  }

  private void emitGameOver() {
    gameOverConsumers.forEach(GameOverConsumer::handle);
  }

  public void onGameOver(GameOverConsumer consumer) {
    this.gameOverConsumers.add(consumer);
  }

  public Food spawnFood() {
    Food food = new Food();
    food.position = findEmptyPosition();
    return food;
  }

  private Cell findEmptyPosition() {
    // kein do-while, da die Leserlichkeit stark leiden würde.
    while (true) {
      Cell positionUnderTest = new Cell(
          rand.nextInt(NUMBER_OF_CELL_COLUMNS - 1),
          rand.nextInt(NUMBER_OF_CELL_ROWS - 1)
      );

      boolean isEmptyPosition = snake.getBody().stream()
          .noneMatch(snakeCell -> snakeCell.equals(positionUnderTest));

      if (isEmptyPosition) {
        return positionUnderTest;
      }
    }
  }
}
