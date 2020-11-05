package webserver;

import io.javalin.Javalin;

public class WebServer {

  public void runServer() {
    Javalin webserver = Javalin.create().start(8050);
    webserver.get("", HttpClient::runGET);
    webserver.post("", HttpClient::runPOST);
  }

//  public static void main(String[] args) {
//    WebServer service = new WebServer();
//    service.runServer();
//
//  }

}
