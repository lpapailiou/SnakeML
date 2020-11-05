package webserver;

import io.javalin.Javalin;

public class WebServer {

  public void runServer() {
    Javalin webserver = Javalin.create().start(8050);
    webserver.config.enableCorsForAllOrigins();
    webserver.config.enableDevLogging();
    webserver.get("", HttpClient::runGET);
    webserver.post("", HttpClient::runPOST);
  }
}
