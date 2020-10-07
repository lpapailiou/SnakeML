package ai;

import game.element.Cell;
import game.element.Food;
import game.element.Snake;

import static main.Config.NUMBER_OF_CELL_COLUMNS;
import static main.Config.NUMBER_OF_CELL_ROWS;

public enum InputNode {

  UP_WALL(0,"distance to upper wall") {
    @Override
    public double getInput(Snake snake, Food food) {
      return 1/(snake.getHead().y + 0.001);
    }
  },
  RIGHT_WALL(1, "distance to right wall") {
    @Override
    public double getInput(Snake snake, Food food) {
      return 1/(Math.abs(NUMBER_OF_CELL_COLUMNS-1 - snake.getHead().x)+0.001);
    }
  },
  DOWN_WALL(2, "distance to bottom wall") {
    @Override
    public double getInput(Snake snake, Food food) {
      return 1/(Math.abs(NUMBER_OF_CELL_ROWS-1 - snake.getHead().y)+0.001);
    }
  },
  LEFT_WALL(3, "distance to left wall") {
    @Override
    public double getInput(Snake snake, Food food) {
      return 1/(snake.getHead().x+0.001);
    }
  },
  UP_BODY(4, "distance to body upwards") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.y-1; i >= 0; i--) {
        pos.y = i;
        if (snake.getBody().stream().anyMatch(c -> c.equals(pos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  RIGHT_BODY(5,  "distance to body right hand side") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell pos = snake.getHead().clone();
      int end = NUMBER_OF_CELL_COLUMNS;
      int index = 0;
      for (int i = pos.x+1; i < end; i++) {
        pos.x = i;
        if (snake.getBody().stream().anyMatch(c -> c.equals(pos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  DOWN_BODY(6, "distance to body downwards") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell pos = snake.getHead().clone();
      int end = NUMBER_OF_CELL_ROWS;
      int index = 0;
      for (int i = pos.y+1; i < end; i++) {
        pos.y = i;
        if (snake.getBody().stream().anyMatch(c -> c.equals(pos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  LEFT_BODY(7, "distance to body left hand side") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell pos = snake.getHead().clone();
      int index = 0;
      for (int i = pos.x-1; i >= 0; i--) {
        pos.x = i;
        if (snake.getBody().stream().anyMatch(c -> c.equals(pos))) {
          break;
        }
        index++;
      }
      return 1/(index+0.001);
    }
  },
  UP_FOOD(8,  "vision of food upwards") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.position.y;
      if (snakeHead.x==food.position.x && distanceY > 0) {
        return 1;
      }
      return 0;
    }
  },
  RIGHT_FOOD(9,  "vision of food right hand side") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.position.x;
      if (snakeHead.y==food.position.y && distanceY < 0) {
        return 1;
      }
      return 0;
    }
  },
  DOWN_FOOD(10, "vision of food downwards") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.y - food.position.y;
      if (snakeHead.x==food.position.x && distanceY < 0) {
        return 1;
      }
      return 0;
    }
  },
  LEFT_FOOD(11,  "vision of food left hand side") {
    @Override
    public double getInput(Snake snake, Food food) {
      Cell snakeHead = snake.getHead().clone();
      int distanceY = snakeHead.x - food.position.x;
      if (snakeHead.y==food.position.y && distanceY > 0) {
        return 1;
      }
      return 0;
    }
  };

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

  public double getInput(Snake snake, Food food) {
    return 0;
  }

}
