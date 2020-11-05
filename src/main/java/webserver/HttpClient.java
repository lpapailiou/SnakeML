package webserver;

import ai.GameBatch;
import ai.data.BatchEntity;
import ai.data.storage.JsonFileHandler;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import game.Game;
import io.javalin.http.Context;
import org.apache.http.conn.util.InetAddressUtils;

public class HttpClient {

  public static void runGET(Context context){

    NeuralNetwork neuralNetwork = new NeuralNetwork();
    GameBatch batch = new GameBatch(neuralNetwork);

    context.result(batch.getJsonString().replaceAll(",", ",\n"));
  }

  public static void runPOST(Context context){
    context.result("Got POST. But it is only supported for control sequences and heartbeat.");
  }


}
