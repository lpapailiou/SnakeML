package webserver;

import io.javalin.core.util.Header;
import io.javalin.http.Context;
import java.util.List;

public class HttpClient {

  public static void runGET(Context context) {

    ItemHolder itemHolder = new ItemHolder();

    context.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    context.result(context.queryParam("cmd"));

    if (context.queryParam("cmd").equals("load")){
      List<String> statisticsJson = itemHolder.consume();
      context.result(statisticsJson.toString());
    }
  }

  public static void runPOST(Context context) {
    context.result("Got POST. But it is only supported for control sequences and heartbeat.");
  }
}
