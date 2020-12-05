package webserver;

import io.javalin.Javalin;

public class WebServer {

  private Javalin webserver = Javalin.create().start(8050);

  public void runServer() {
    webserver.config.enableCorsForAllOrigins();
    webserver.config.enableDevLogging();
    webserver.config.addStaticFiles("/www");
    webserver.get("", StatisticsEndpoint::listAllBatches);
  }

  public void stopServer() {
    webserver.stop();
  }
}
