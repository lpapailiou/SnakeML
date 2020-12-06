package ai;

import game.Cell;
import game.Snake;
import main.configuration.INodeConfigReader;

/**
 * This enum is used to collect the vision of the Snake. For every implemented case it implements
 * the according function. Every value is mapped by its ordinal. As a every input node has its
 * specific purpose, a short description s added as a field value. This short description can be
 * visualized on the gui as Tooltip text.
 */
public enum InputNode {

  UP_WALL("distance to upper wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1 / (snake.getHead().y + 0.001);
    }
  },
  RIGHT_WALL("distance to right wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1 / (Math.abs(config.getBoardWidth() - 1 - snake.getHead().x) + 0.001);
    }
  },
  DOWN_WALL("distance to bottom wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1 / (Math.abs(config.getBoardHeight() - 1 - snake.getHead().y) + 0.001);
    }
  },
  LEFT_WALL("distance to left wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1 / (snake.getHead().x + 0.001);
    }
  },
  UP_BODY("distance to body upwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.y - 1; i >= 0; i--) {
        pos = new Cell(pos.x, i);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1 / (index + 0.001);
    }
  },
  RIGHT_BODY("distance to body right hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int end = config.getBoardWidth();
      int index = 0;
      for (int i = pos.x + 1; i < end; i++) {
        pos = new Cell(i, pos.y);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1 / (index + 0.001);
    }
  },
  DOWN_BODY("distance to body downwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int end = config.getBoardHeight();
      int index = 0;
      for (int i = pos.y + 1; i < end; i++) {
        pos = new Cell(pos.x, i);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1 / (index + 0.001);
    }
  },
  LEFT_BODY("distance to body left hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.x - 1; i >= 0; i--) {
        pos = new Cell(i, pos.y);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1 / (index + 0.001);
    }
  },
  UP_FOOD("vision of food upwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.y;
      if (snakeHead.x == food.x && distanceY > 0) {
        return 1000;
      }
      return 0;
    }
  },
  RIGHT_FOOD("vision of food right hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.x;
      if (snakeHead.y == food.y && distanceY < 0) {
        return 1000;
      }
      return 0;
    }
  },
  DOWN_FOOD("vision of food downwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.y;
      if (snakeHead.x == food.x && distanceY < 0) {
        return 1000;
      }
      return 0;
    }
  },
  LEFT_FOOD("vision of food left hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.x;
      if (snakeHead.y == food.y && distanceY > 0) {
        return 1000;
      }
      return 0;
    }
  };

  private static final INodeConfigReader config = INodeConfigReader.getInstance();
  private final String tooltipText;

  InputNode(String tooltipText) {
    this.tooltipText = tooltipText;
  }

  /**
   * This method will return the short description of the according input node.
   *
   * @return the short description
   */
  public String getTooltipText() {
    return tooltipText;
  }

  /**
   * This method will calculate a case-specific value, which is part of the vision of the Snake.
   *
   * @param snake the Snake which's vision is queried
   * @param food  the food of the current game state
   * @return the vision value for the queried case
   */
  public double getInput(Snake snake, Cell food) {
    return 0;
  }

}
