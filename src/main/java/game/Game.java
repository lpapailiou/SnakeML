package game;

import game.event.IGameOverConsumer;
import game.event.ITickAware;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.configuration.IGameConfigReader;

/**
 * This class is the basic implementation of the Snake game. It is designed as generically as
 * possible, so it can be reused for different Snake algorithms. It mainly takes care about the
 * Snake and the Cell ('food' or 'apple'). Additionally, it will manage a game over event. The game
 * is designed to react on a tick frequency, which is translated to 'steps of the Snake'.
 */
public class Game implements ITickAware {

  private final ArrayList<IGameOverConsumer> gameOverConsumers = new ArrayList<>();
  private static final Logger LOG = Logger.getLogger("winning snake logger");
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  private IGameConfigReader config = IGameConfigReader.getInstance();
  private Direction nextDirection = config.getInitialDirection();
  private Snake snake;
  private Cell food;
  private Random rand = new Random();

  /**
   * As soon as initialized, the Game will initialize food for the Snake on a random coordinate.
   */
  public Game() {
    food = provideFood();
  }

  Game(Snake snake) {
    this.snake = snake;
    food = provideFood();
  }

  /**
   * With this method, the Direction of the next move of the Snake can be set.
   *
   * @param nextDirection the Direction of the next move
   */
  public void changeDirection(Direction nextDirection) {
    this.nextDirection = nextDirection;
  }

  /**
   * This method is triggered periodically on a specific frequency. It executes one single move of
   * the Snake.
   */
  @Override
  public void onTick() {

    if (snake.isDead()) {
      this.emitGameOver();
      return;
    }

    snake.move(nextDirection, food);

    if (snake.isHeadAt(food)) {
      food = provideFood();
    }
    pcs.firePropertyChange("tick", false, true);
  }

  /**
   * This method will allow to add a listener to the game ticks.
   *
   * @param l the PropertyChangeListener to be added
   */
  public void addListener(PropertyChangeListener l) {
    pcs.addPropertyChangeListener("tick", l);
  }

  private void emitGameOver() {
    gameOverConsumers.forEach(IGameOverConsumer::handle);
  }

  /**
   * This method will consume a game over event.
   *
   * @param consumer the event consumer to be added
   */
  public void onGameOver(IGameOverConsumer consumer) {
    this.gameOverConsumers.add(consumer);
  }

  /**
   * Returns the Direction of the next move.
   *
   * @return the Direction of the next move
   */
  public Direction getDirection() {
    return nextDirection;
  }

  /**
   * Returns the Snake as object.
   *
   * @return the Snake
   */
  public Snake getSnake() {
    return snake;
  }

  /**
   * Returns the length of the Snake as integer.
   *
   * @return the Snake length
   */
  public int getSnakeLength() {
    return snake.getBody().size();
  }

  /**
   * Returns the coordinates of the food.
   *
   * @return the coordinates of the food
   */
  public Cell getFood() {
    return food;
  }

  private Cell provideFood() {
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
