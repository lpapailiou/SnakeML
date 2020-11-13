package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.configuration.Config;
import main.configuration.IMainConfigReader;
import ui.ApplicationController;
import webserver.WebServer;
import java.util.concurrent.CountDownLatch;

public class Main extends Application {

  public static void main(String[] args) {

    Service<Void> service = new Service<Void>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<Void>() {
          @Override
          protected Void call() throws Exception {
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
            return null;
          }
        };
      }
    };
    service.start();

    launch(args);

  }

  @Override
  public void start(Stage stage) {

    IMainConfigReader config = Config.getMainConfigReader();

    try {
      ClassLoader classLoader = Main.class.getClassLoader();
      FXMLLoader loader = new FXMLLoader(classLoader.getResource("ApplicationPanel.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 1600, 800);
      scene.getStylesheets().add(classLoader.getResource("applicationCss.css").toExternalForm());
      scene.getStylesheets().add(Main.class.getClassLoader().getResource(config.getTheme().getCss()).toExternalForm());
      scene.setFill(config.getTheme().getBackgroundColor());
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