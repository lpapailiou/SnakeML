package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import main.configuration.IConfigWriter;
import org.junit.Before;
import org.junit.Test;

public class SnakeTest {

  @Before
  public void setup() {
    IConfigWriter configWriter = IConfigWriter.getInstance();
    configWriter.setBoardWidth(14);
    configWriter.setBoardHeight(14);
  }

  @Test
  public void testMovingStraight() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(3, 3));

    ArrayList<Cell> newBodyPosition = new ArrayList<>();
    newBodyPosition.add(new Cell(4, 3));
    newBodyPosition.add(new Cell(3, 3));
    newBodyPosition.add(new Cell(2, 3));

    snake.move(Direction.RIGHT, new Cell(0, 0));

    assertTrue(snake.isHeadAt(new Cell(4, 3)));
    assertEquals(newBodyPosition, snake.getBody());
  }

  @Test
  public void testIncreasingStepCount() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(3, 3));

    assertEquals(0, snake.getSteps());

    snake.move(Direction.RIGHT, new Cell(0, 0));

    assertEquals(1, snake.getSteps());
  }

  @Test
  public void testLeftTurn() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(3, 3));

    ArrayList<Cell> newBodyPosition = new ArrayList<>();
    newBodyPosition.add(new Cell(3, 2));
    newBodyPosition.add(new Cell(3, 3));
    newBodyPosition.add(new Cell(2, 3));

    snake.move(Direction.UP, new Cell(0, 0));

    assertTrue(snake.isHeadAt(new Cell(3, 2)));
    assertEquals(newBodyPosition, snake.getBody());
  }

  @Test
  public void testRightTurn() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(3, 3));

    ArrayList<Cell> newBodyPosition = new ArrayList<>();
    newBodyPosition.add(new Cell(3, 4));
    newBodyPosition.add(new Cell(3, 3));
    newBodyPosition.add(new Cell(2, 3));

    snake.move(Direction.DOWN, new Cell(0, 0));

    assertTrue(snake.isHeadAt(new Cell(3, 4)));
    assertEquals(newBodyPosition, snake.getBody());
  }

  @Test
  public void testBackwardsTurn() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(3, 3));

    ArrayList<Cell> newBodyPosition = new ArrayList<>();
    newBodyPosition.add(new Cell(3, 3));
    newBodyPosition.add(new Cell(2, 3));
    newBodyPosition.add(new Cell(1, 3));

    assertFalse(snake.isDead());

    snake.move(Direction.LEFT, new Cell(0, 0));

    assertTrue(snake.isHeadAt(new Cell(3, 3)));
    assertEquals(newBodyPosition, snake.getBody());
    assertTrue(snake.isDead());
  }

  @Test
  public void testWallDeath() {
    Snake snake = new Snake(3, Direction.RIGHT, new Cell(13, 13));

    ArrayList<Cell> newBodyPosition = new ArrayList<>();
    newBodyPosition.add(new Cell(13, 13));
    newBodyPosition.add(new Cell(12, 13));
    newBodyPosition.add(new Cell(11, 13));

    assertFalse(snake.isDead());

    snake.move(Direction.RIGHT, new Cell(0, 0));

    assertTrue(snake.isHeadAt(new Cell(13, 13)));
    assertEquals(newBodyPosition, snake.getBody());
    assertTrue(snake.isDead());

  }

}
