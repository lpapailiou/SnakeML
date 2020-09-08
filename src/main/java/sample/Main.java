package sample;

import static game.Config.CELL_SIZE_IN_PX;
import static game.Config.NUMBER_OF_CELL_COLUMNS;
import static game.Config.NUMBER_OF_CELL_ROWS;
import static game.Config.SNAKE_BODY_COLOR;

import game.Cell;
import game.Config;
import game.Direction;
import game.Snake;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

  Snake snake = new Snake(Config.INITIAL_SNAKE_SIZE, Config.INITIAL_DIRECTION, Config.INITIAL_STARTING_POINT);
  Direction nextDirection = Config.INITIAL_DIRECTION;
  int foodcolor = 0;
  Cell foodPosition;
  boolean isGameOver = false;
  Random rand = new Random();

  public void start(Stage primaryStage) {
    try {

      spawnFood();

      VBox root = new VBox();
      Canvas c = new Canvas(NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX, NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX);
      GraphicsContext gc = c.getGraphicsContext2D();
      root.getChildren().add(c);

      new AnimationTimer() {
        long lastTick = 0;

        public void handle(long now) {
          if (lastTick == 0) {
            lastTick = now;
            tick(gc);
            return;
          }

          if (now - lastTick > 1000000000 / Config.SPEED_FACTOR) {
            lastTick = now;
            tick(gc);
          }
        }

      }.start();

      Scene scene = new Scene(root, NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX, NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX);

      listenToKeyboardEvents(scene);

      primaryStage.setScene(scene);
      primaryStage.setTitle("SNAKE GAME");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listenToKeyboardEvents(Scene scene) {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
      if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
        nextDirection = Direction.UP;
      }
      if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
        nextDirection = Direction.LEFT;
      }
      if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) {
        nextDirection = Direction.DOWN;
      }
      if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
        nextDirection = Direction.RIGHT;
      }
    });
  }

  public void tick(GraphicsContext gc) {

    snake.move(nextDirection);
    if (isGameOver || snake.isDead()) {
      isGameOver = true;
      paintGameOver(gc);
      return;
    }

    eatFood();
    fillBackground(gc);

    paintScore(gc);

    paintFood(gc);
    paintSnake(gc);

  }

  private void paintGameOver(GraphicsContext gc) {
    gc.setFill(Config.GAME_OVER_FONT_COLOR);
    gc.setFont(new Font("", Config.GAME_OVER_FONT_SIZE));
    gc.fillText("GAME OVER", 100, 250);
  }

  private void paintScore(GraphicsContext gc) {
    gc.setFill(Config.SCORE_FONT_COLOR);
    gc.setFont(new Font("", Config.SCORE_FONT_SIZE_IN_PT));
    gc.fillText("Score: -5", 10, Config.SCORE_FONT_SIZE_IN_PT);
  }

  private void paintSnake(GraphicsContext gc) {
    snake.getBody().forEach(cell -> {
      gc.setFill(SNAKE_BODY_COLOR);
      gc.fillRect(cell.x * CELL_SIZE_IN_PX, cell.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX - 1, CELL_SIZE_IN_PX
          - 1);
      gc.setFill(SNAKE_BODY_COLOR);
      gc.fillRect(cell.x * CELL_SIZE_IN_PX, cell.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX - 2, CELL_SIZE_IN_PX
          - 2);
    });
  }

  private void paintFood(GraphicsContext gc) {
    // randomize a color for the food circle. TODO: refactor.
    Color cc = Color.WHITE;
    switch (foodcolor) {
      case 0:
        cc = Color.PURPLE;
        break;
      case 1:
        cc = Color.LIGHTBLUE;
        break;
      case 2:
        cc = Color.YELLOW;
        break;
      case 3:
        cc = Color.PINK;
        break;
      case 4:
        cc = Color.ORANGE;
        break;
    }
    gc.setFill(cc);
    gc.fillOval(foodPosition.x * CELL_SIZE_IN_PX, foodPosition.y * CELL_SIZE_IN_PX, CELL_SIZE_IN_PX,
        CELL_SIZE_IN_PX);
  }

  private void fillBackground(GraphicsContext gc) {
    gc.setFill(Config.BACKGROUND_COLOR);
    gc.fillRect(0, 0, NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX, NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX);
  }

  private void eatFood() {
    if (foodPosition.x == snake.getBody().get(0).x && foodPosition.y == snake.getBody().get(0).y) {
      snake.getBody().add(new Cell(-1, -1));
      spawnFood();
    }
  }


  public void spawnFood() {
    boolean isAlreadyOccupied;
    do {
      foodPosition = new Cell(
          rand.nextInt(NUMBER_OF_CELL_COLUMNS - 1),
          rand.nextInt(NUMBER_OF_CELL_ROWS - 1)
      );

      isAlreadyOccupied = snake.getBody().stream()
          .anyMatch(snakeCell -> snakeCell.equals(foodPosition));
    } while (isAlreadyOccupied);

  }

  public static void main(String[] args) {
    launch(args);
  }
}