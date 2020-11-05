package webserver;

import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import io.javalin.core.util.Header;
import io.javalin.http.Context;

public class HttpClient {

  public static void runGET(Context context) {

    context.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

    NeuralNetwork neuralNetwork = new NeuralNetwork();
    GameBatch batch = new GameBatch(neuralNetwork);
    batch.run();
    context.result(batch.getJsonString().replaceAll(",", ",\n"));
  }

  public static void runPOST(Context context) {
    context.result("Got POST. But it is only supported for control sequences and heartbeat.");
  }


}
