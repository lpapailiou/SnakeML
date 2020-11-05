package main;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import main.configuration.Config;
import ui.SnakeMlStage;
import webserver.WebServer;
import java.util.concurrent.CountDownLatch;

public class Main extends Application {

  AnimationTimer automaticTicker;
  private SnakeMlStage stage;
  private Game game;

  public void start(Stage primaryStage) {
    try {

      automaticTicker = new AnimationTimer() {
        long lastTick = 0;

        public void handle(long now) {
          if (lastTick == 0 || now - lastTick > 100000000 / Config.getInstance()
              .getManualSpeedFactor()) {
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

    Service<Void> service = new Service<Void>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            //Background work
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                try {
                  WebServer webservice = new WebServer();
                  webservice.runServer();
                } finally {
                  latch.countDown();
                }
              }
            });
            latch.await();
            //Keep with the background work
            return null;
          }
        };
      }
    };
    service.start();

    launch(args);

  }
}