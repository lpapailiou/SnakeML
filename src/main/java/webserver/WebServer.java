package webserver;

import io.javalin.Javalin;

/**
 * This class will start up and maintain a local web server on the port 8050. This external
 * interface is used to push statistics data of neural network games to the local browser. There,
 * statistics can be visualized based on the exchanged data.
 */
public class WebServer {

  private Javalin javalin = Javalin.create().start(8050);

  /**
   * This method will configure the web server. Additionally, it will execute a get request to
   * publish game statistics.
   */
  public void runServer() {
    javalin.config.enableCorsForAllOrigins();
    javalin.config.enableDevLogging();
    javalin.config.addStaticFiles("/www");
    javalin.get("", StatisticsEndpoint::listAllBatches);
  }

  /**
   * This method will stop the server, in order to free up the used port.
   */
  public void stopServer() {
    javalin.stop();
  }
}
