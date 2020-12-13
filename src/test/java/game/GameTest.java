package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import main.configuration.IConfigWriter;
import org.junit.Test;

public class GameTest {

  @Test
  public void testFoodAtLastEmptyPosition() {
    IConfigWriter configWriter = IConfigWriter.getInstance();
    configWriter.setBoardHeight(1);
    configWriter.setBoardWidth(10);
    Snake snake = new Snake(9, Direction.RIGHT, new Cell(8, 0));

    Game game = new Game(snake);

    assertEquals(new Cell(9, 0), game.getFood());
  }

  @Test
  public void testFoodIsNullOnPerfectGame() {
    IConfigWriter configWriter = IConfigWriter.getInstance();
    configWriter.setBoardHeight(1);
    configWriter.setBoardWidth(10);
    Snake snake = new Snake(10, Direction.RIGHT, new Cell(9, 0));

    Game game = new Game(snake);

    assertNull(game.getFood());
  }

}
