package ai;

import game.element.Cell;
import game.element.Snake;
import main.configuration.Config;
import main.configuration.INodeConfigReader;

public enum InputNode {

  UP_WALL(0,"distance to upper wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1/(snake.getHead().y + 0.001);
    }
  },
  RIGHT_WALL(1, "distance to right wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1/(Math.abs(config.getBoardWidth()-1 - snake.getHead().x)+0.001);
    }
  },
  DOWN_WALL(2, "distance to bottom wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1/(Math.abs(config.getBoardHeight()-1 - snake.getHead().y)+0.001);
    }
  },
  LEFT_WALL(3, "distance to left wall") {
    @Override
    public double getInput(Snake snake, Cell food) {
      return 1/(snake.getHead().x+0.001);
    }
  },
  UP_BODY(4, "distance to body upwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.y-1; i >= 0; i--) {
        pos = new Cell(pos.x, i);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  RIGHT_BODY(5,  "distance to body right hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int end = config.getBoardWidth();
      int index = 0;
      for (int i = pos.x+1; i < end; i++) {
        pos = new Cell(i, pos.y);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  DOWN_BODY(6, "distance to body downwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int end = config.getBoardHeight();
      int index = 0;
      for (int i = pos.y+1; i < end; i++) {
        pos = new Cell(pos.x, i);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  LEFT_BODY(7, "distance to body left hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.x-1; i >= 0; i--) {
        pos = new Cell(i, pos.y);
        Cell finalPos = pos;
        if (snake.getBody().stream().anyMatch(c -> c.equals(finalPos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  UP_FOOD(8,  "vision of food upwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.y;
      if (snakeHead.x==food.x && distanceY > 0) {
        return 1000;
      }
      return 0;
    }
  },
  RIGHT_FOOD(9,  "vision of food right hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.x;
      if (snakeHead.y==food.y && distanceY < 0) {
        return 1000;
      }
      return 0;
    }
  },
  DOWN_FOOD(10, "vision of food downwards") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.y;
      if (snakeHead.x==food.x && distanceY < 0) {
        return 1000;
      }
      return 0;
    }
  },
  LEFT_FOOD(11,  "vision of food left hand side") {
    @Override
    public double getInput(Snake snake, Cell food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.x;
      if (snakeHead.y==food.y && distanceY > 0) {
        return 1000;
      }
      return 0;
    }
  };

  private static INodeConfigReader config = Config.getNodeConfigReader();
  private int id;
  private String tooltipText;

  InputNode(int id, String tooltipText) {
    this.id = id;
    this.tooltipText = tooltipText;
  }

  public int getId() {
    return id;
  }

  public String getTooltipText() {
    return tooltipText;
  }

  public double getInput(Snake snake, Cell food) {
    return 0;
  }

}
