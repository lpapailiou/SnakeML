package game;

import game.element.Cell;
import game.element.Snake;
import game.event.GameOverConsumer;
import game.event.TickAware;
import main.configuration.Config;
import main.configuration.IGameConfig;
import java.util.ArrayList;
import java.util.Random;

public class Game implements TickAware {    // TODO: better encapsulation?

  private final ArrayList<GameOverConsumer> gameOverConsumers = new ArrayList<>();

  private IGameConfig config = Config.getInstance();
  Direction nextDirection = config.getInitialDirection();
  public Snake snake = new Snake(config.getInitialSnakeSize(), nextDirection, config.getInitialStartingPosition());
  public Cell food;
  Random rand = new Random();

  public Game() {
    food = findEmptyPosition();
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

    if (snake.isHeadAt(food)) {
      snake.grow();
      food = findEmptyPosition();
    }
  }

  private void emitGameOver() {
    gameOverConsumers.forEach(GameOverConsumer::handle);
  }

  public void onGameOver(GameOverConsumer consumer) {
    this.gameOverConsumers.add(consumer);
  }

  private Cell findEmptyPosition() {
    // kein do-while, da die Leserlichkeit stark leiden würde.
    while (true) {
      Cell positionUnderTest = new Cell(
          rand.nextInt(config.getBoardWidth()),
          rand.nextInt(config.getBoardHeight())
      );

      boolean isEmptyPosition = snake.getBody().stream()
          .noneMatch(snakeCell -> snakeCell.equals(positionUnderTest));

      if (isEmptyPosition) {
        return positionUnderTest;
      }

      if (snake.getBody().size() == (config.getBoardWidth() * config.getBoardHeight())) {
        System.out.println("----------------------------------------------> perfect game!!!");
        return null;
      }
    }
  }
}
