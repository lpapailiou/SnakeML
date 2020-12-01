package game;

import game.event.IGameOverConsumer;
import game.event.ITickAware;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.configuration.Config;
import main.configuration.IGameConfigReader;

public class Game implements ITickAware {

  private final ArrayList<IGameOverConsumer> gameOverConsumers = new ArrayList<>();

  private static final Logger LOG = Logger.getLogger("winning snake logger");
  private IGameConfigReader config = Config.getGameConfigReader();
  private Direction nextDirection = config.getInitialDirection();
  private Snake snake = new Snake(config.getInitialSnakeSize(), nextDirection,
      config.getInitialStartingPosition());
  private Cell food;
  private Random rand = new Random();

  public Game() {
    food = findEmptyPosition();
  }

  public void changeDirection(Direction nextDirection) {
    this.nextDirection = nextDirection;
  }

  @Override
  public void onTick() {

    if (snake.isDead()) {
      this.emitGameOver();
      return;
    }

    snake.move(nextDirection, food);

    if (snake.isHeadAt(food)) {
      food = findEmptyPosition();
    }
  }

  private void emitGameOver() {
    gameOverConsumers.forEach(IGameOverConsumer::handle);
  }

  public void onGameOver(IGameOverConsumer consumer) {
    this.gameOverConsumers.add(consumer);
  }

  public Direction getDirection() {
    return nextDirection;
  }

  public Snake getSnake() {
    return snake;
  }

  public Cell getFood() {
    return food;
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
        LOG.log(Level.INFO, " ********************** perfect game reached ********************** ");
        return null;
      }
    }
  }
}
