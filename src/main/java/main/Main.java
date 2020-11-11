package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.configuration.Config;
import ui.ApplicationController;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    try {
      ClassLoader classLoader = Main.class.getClassLoader();
      FXMLLoader loader = new FXMLLoader(classLoader.getResource("ApplicationPanel.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 1600, 800);
      scene.getStylesheets().add(classLoader.getResource("applicationCss.css").toExternalForm());
      scene.getStylesheets().add(Main.class.getClassLoader().getResource(Config.getInstance().getTheme().getCss()).toExternalForm());
      scene.setFill(Config.getInstance().getTheme().getBackgroundColor());
      stage.setScene(scene);
      stage.setMinHeight(839);
      stage.setMinWidth(1616);
      stage.setMaxHeight(839);
      stage.setMaxWidth(1616);
      stage.setTitle("Snake ML | FFHS Bern 2020");
      stage.getIcons().add(new Image("snake.png"));
      ((ApplicationController) loader.getController()).setStage(stage);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}