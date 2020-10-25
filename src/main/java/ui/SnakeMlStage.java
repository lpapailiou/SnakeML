package ui;

import static main.Config.TITLE_TEXT;

import game.Direction;
import game.Game;
import game.event.TickAware;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SnakeMlStage implements TickAware {

  private final Game game;
  double dpi = Screen.getPrimary().getDpi();

  private Stage stage;
  GameScene gameScene;

  public SnakeMlStage(Stage stage, Game game) {
    this.stage = stage;
    this.game = game;

    initStage();
  }

  private void initStage() {
    gameScene = new GameScene(game);
    listenToKeyboardEvents(gameScene.scene);

    stage.setScene(gameScene.scene);
    stage.setTitle(TITLE_TEXT);
    stage.show();
  }

  private void listenToKeyboardEvents(Scene scene) {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
      switch (key.getCode()) {
        case W: case UP:
          game.changeDirection(Direction.UP);
          break;

        case A: case LEFT:
          game.changeDirection(Direction.LEFT);
          break;

        case S: case DOWN:
          game.changeDirection(Direction.DOWN);
          break;

        case D: case RIGHT:
          game.changeDirection(Direction.RIGHT);
          break;

      }
    });
  }

  @Override
  public void onTick() {
    gameScene.onTick();
  }
}
