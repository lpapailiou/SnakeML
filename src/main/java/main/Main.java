package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.configuration.Config;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    try {
      ClassLoader classLoader = Main.class.getClassLoader();
      FXMLLoader loader = new FXMLLoader(classLoader.getResource("ApplicationPanel.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 1600, 800);
      scene.getStylesheets().add(classLoader.getResource("baseCss.css").toExternalForm());
      scene.getStylesheets().add(Main.class.getClassLoader().getResource(Config.getInstance().getTheme().getCss()).toExternalForm());
      scene.setFill(Config.getInstance().getTheme().getBackgroundColor());
      stage.setScene(scene);
      stage.setMinHeight(839);
      stage.setMinWidth(1616);
      stage.setMaxHeight(839);
      stage.setMaxWidth(1616);
      stage.setTitle("Snake ML | FFHS Bern 2020");
      stage.getIcons().add(new Image("snake.png"));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*

  private AnimationTimer automaticTicker;
  private SnakeMlStage stage;
  private Game game;

  public void start(Stage primaryStage) {
    try {

      automaticTicker = new AnimationTimer() {
        long lastTick = 0;

        public void handle(long now) {
          if (lastTick == 0 || now - lastTick > 100000000 / Config.getInstance().getManualSpeedFactor()) {
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
  } */

  public static void main(String[] args) {
    launch(args);
  }
}