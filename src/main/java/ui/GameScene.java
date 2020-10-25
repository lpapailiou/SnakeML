package ui;

import game.Game;
import game.event.TickAware;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import main.configuration.Config;
import main.configuration.IGameConfig;
import ui.painter.impl.GameInformationPainter;
import ui.painter.impl.GamePainter;
import ui.painter.IGameInformationPainter;
import ui.painter.IGamePainter;

public class GameScene implements TickAware {


  private final Game game;
  IGamePainter gamePainter = new GamePainter();
  IGameInformationPainter gameInformationPainter = new GameInformationPainter();
  IGameConfig config = Config.getInstance();

  Canvas c = new Canvas(config.getBoardWidth() * config.getCellWidth(),
      config.getBoardHeight() * config.getCellWidth());
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
        config.getBoardWidth() * config.getCellWidth(),
        config.getBoardHeight() * config.getCellWidth()
    );
    root.getChildren().add(c);

    game.onGameOver(() -> gameInformationPainter.paintGameOver(gc));
  }

  @Override
  public void onTick() {
    gamePainter.paintBoard(gc);
    gamePainter.paintFood(gc, game.food);
    gamePainter.paintSnake(gc, game.snake);
  }
}
