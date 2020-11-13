package webserver;

import io.javalin.Javalin;

public class WebServer {

  Javalin webserver = Javalin.create().start(8050);

  public void runServer() {
    webserver.config.enableCorsForAllOrigins();
    webserver.config.enableDevLogging();
    webserver.config.addStaticFiles("/");
    webserver.get("", HttpClient::runGET);
    webserver.post("", HttpClient::runPOST);
  }

  public void stopServer(){
    webserver.stop();
  }
}
