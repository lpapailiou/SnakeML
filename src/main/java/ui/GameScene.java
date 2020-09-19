package ui;

import static main.Config.CELL_SIZE_IN_PX;
import static main.Config.NUMBER_OF_CELL_COLUMNS;
import static main.Config.NUMBER_OF_CELL_ROWS;

import game.Game;
import game.event.TickAware;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import ui.painter.impl.GameInformationPainter;
import ui.painter.impl.GamePainter;
import ui.painter.IGameInformationPainter;
import ui.painter.IGamePainter;

public class GameScene implements TickAware {


  private final Game game;
  IGamePainter gamePainter = new GamePainter();
  IGameInformationPainter gameInformationPainter = new GameInformationPainter();

  Canvas c = new Canvas(NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX,
      NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX);
  GraphicsContext gc = c.getGraphicsContext2D();
  VBox root = new VBox();
  public Scene scene;

  public GameScene(Game game) {
    this.game = game;
    initScene();
  }

  private void initScene() {
    this.scene = new Scene(
        root,
        NUMBER_OF_CELL_COLUMNS * CELL_SIZE_IN_PX,
        NUMBER_OF_CELL_ROWS * CELL_SIZE_IN_PX
    );
    root.getChildren().add(c);

    game.onGameOver(() -> gameInformationPainter.paintGameOver(gc));
  }

  @Override
  public void onTick() {
    gamePainter.paintBoard(gc);
    gamePainter.paintFood(gc, game.food.position);
    gamePainter.paintSnake(gc, game.snake);
  }
}
