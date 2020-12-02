package ai;

import game.Direction;
import game.Game;
import game.Cell;
import game.Snake;
import main.configuration.Config;
import main.configuration.IConfigReader;
import main.configuration.IGameConfigReader;
import main.configuration.ITestConfig;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class InputNodeTest {


  @Test
  public void foodInputNodesTest() {
    ITestConfig config = ITestConfig.getInstance();
    config.setBoardWidth(20);
    config.setBoardHeight(20);

    Game game = new Game();

    // assumption that snake head is at (6, 3)
    assertEquals(3, game.getSnake().getHead().x);
    assertEquals(3, game.getSnake().getHead().y);

    double up = InputNode.UP_FOOD.getInput(game.getSnake(), new Cell(1,1));
    double right = InputNode.RIGHT_FOOD.getInput(game.getSnake(), new Cell(1,1));
    double down = InputNode.DOWN_FOOD.getInput(game.getSnake(), new Cell(1,1));
    double left = InputNode.LEFT_FOOD.getInput(game.getSnake(), new Cell(1,1));

    assertEquals(0, up, 0.001);
    assertEquals(0, right, 0.001);
    assertEquals(0, down, 0.001);
    assertEquals(0, left, 0.001);

    up = InputNode.UP_FOOD.getInput(game.getSnake(), new Cell(3,1));
    assertEquals(1000, up, 0.001);

    right = InputNode.RIGHT_FOOD.getInput(game.getSnake(), new Cell(4,3));
    assertEquals(1000, right, 0.001);

    down = InputNode.DOWN_FOOD.getInput(game.getSnake(), new Cell(3,10));
    assertEquals(1000, down, 0.001);

    left = InputNode.LEFT_FOOD.getInput(game.getSnake(), new Cell(0,3));
    assertEquals(1000, left, 0.001);

  }

  @Test
  public void bodynputNodesTest() {
    ITestConfig config = ITestConfig.getInstance();
    config.setBoardWidth(20);
    config.setBoardHeight(20);

    // use dummy snake to set custom body
    DummySnake snake = new DummySnake(IGameConfigReader.getInstance().getInitialSnakeSize(), IGameConfigReader.getInstance().getInitialDirection(), IGameConfigReader.getInstance().getInitialStartingPosition());
    snake.addBodyPart(new Cell(3,3));
    snake.addBodyPart(new Cell(3,2));
    snake.addBodyPart(new Cell(4,2));
    snake.addBodyPart(new Cell(4,3));
    snake.addBodyPart(new Cell(4,4));
    snake.addBodyPart(new Cell(3,5));
    snake.addBodyPart(new Cell(2,5));
    snake.addBodyPart(new Cell(1,5));
    snake.addBodyPart(new Cell(0,5));
    snake.addBodyPart(new Cell(0,4));
    snake.addBodyPart(new Cell(0,3));

    // assumption that snake head is at (6, 3)
    assertEquals(3, snake.getHead().x);
    assertEquals(3, snake.getHead().y);
    Cell food = new Cell(1,1);
    double up = InputNode.UP_BODY.getInput(snake, food);
    double right = InputNode.RIGHT_BODY.getInput(snake, food);
    double down = InputNode.DOWN_BODY.getInput(snake, food);
    double left = InputNode.LEFT_BODY.getInput(snake, food);

    assertEquals((1.0/0.001), up, 0.001);
    assertEquals((1.0/0.001), right, 0.001);
    assertEquals((1.0/1), down, 0.001);
    assertEquals((1.0/2), left, 0.001);

    System.out.println(up); // 1000.0
    System.out.println(right); // 1000.0
    System.out.println(down); // 0.9990009990009991
    System.out.println(left); // 0.49975012493753124
    snake.removeLastBodyPart();
    left = InputNode.LEFT_BODY.getInput(snake, food);
    System.out.println(left); // 0.33322225924691773

    assertEquals((1.0/3), left, 0.001);

  }

  @Test
  public void wallInputNodesTest() {
    ITestConfig config = ITestConfig.getInstance();
    config.setBoardWidth(20);
    config.setBoardHeight(20);

    Game game = new Game();
    game.onTick();
    game.onTick();
    game.onTick();

    // assumption that snake head is at (6, 3)
    assertEquals(6, game.getSnake().getHead().x);
    assertEquals(3, game.getSnake().getHead().y);

    double up = InputNode.UP_WALL.getInput(game.getSnake(), new Cell(1,1));
    double right = InputNode.RIGHT_WALL.getInput(game.getSnake(), new Cell(1,1));
    double down = InputNode.DOWN_WALL.getInput(game.getSnake(), new Cell(1,1));
    double left = InputNode.LEFT_WALL.getInput(game.getSnake(), new Cell(1,1));

    assertEquals((1.0/3), up, 0.001);
    assertEquals((1.0/(19-6)), right, 0.001);
    assertEquals((1.0/(19-3)), down, 0.001);
    assertEquals((1.0/6), left, 0.001);

    System.out.println(up); // 0.33322225924691773
    System.out.println(right); // 0.07691716021844473
    System.out.println(down); // 0.06249609399412536
    System.out.println(left); // 0.16663889351774702
  }

  static class DummySnake extends Snake {

    public DummySnake(int size, Direction initialDirection, Cell initialHeadPosition) {
      super(size, initialDirection, initialHeadPosition);
      body = new LinkedList<>();
    }

    public void addBodyPart(Cell cell) {
      body.add(cell);
    }

    public void removeLastBodyPart() {
      body.removeLast();
    }

  }

}
