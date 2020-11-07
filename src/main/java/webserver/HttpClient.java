package webserver;

import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import io.javalin.core.util.Header;
import io.javalin.http.Context;

public class HttpClient {

  public static void runGET(Context context) {

    ItemHolder itemHolder = new ItemHolder();

    context.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    context.result(context.queryParam("cmd"));

    if (context.queryParam("cmd").equals("start")){
      GameBatch batch = new GameBatch(new NeuralNetwork(0.999,12,32,32,32,4)); // TODO: change after GUI is implemented
      // TODO: Es wird nicht via Button "Start" in JS gestartet sondern via JavaFX Game -> daher verschieben nach dort wo Button ist für Game start mit der gegebenen Config
      itemHolder.produce(batch);
      batch.run();
    }

    if (context.queryParam("cmd").equals("load")){
      GameBatch batch = itemHolder.consume();
      batch.generateJson();
      context.result(batch.getJsonString());
    }
  }

  public static void runPOST(Context context) {
    context.result("Got POST. But it is only supported for control sequences and heartbeat.");
  }
}
