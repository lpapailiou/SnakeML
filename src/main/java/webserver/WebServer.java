package webserver;

import io.javalin.Javalin;

public class WebServer {

  void runServer() {
    Javalin webserver = Javalin.create().start(8050);
    webserver.get("", GameBatchController::run);
  }

  public static void main(String[] args) {
    WebServer service = new WebServer();
    service.runServer();

  }


}
