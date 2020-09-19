package main;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.SnakeMlStage;

public class Main extends Application {

  private

  AnimationTimer automaticTicker;
  SnakeMlStage stage;
  private Game game;

  public void start(Stage primaryStage) {
    try {

      automaticTicker = new AnimationTimer() {
        long lastTick = 0;

        public void handle(long now) {
          if (lastTick == 0 || now - lastTick > 100000000 / Config.SPEED_FACTOR) {
            lastTick = now;
            stage.onTick();
            game.onTick();
          }
        }
      };
      automaticTicker.start();

      game = new Game();
      stage = new SnakeMlStage(primaryStage, game);

      game.onGameOver(() -> automaticTicker.stop());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}