package webserver;

import ai.GameBatch;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import io.javalin.http.Context;

public class HttpClient {

  public static void runGET(Context context){
    TempStorage instanceData = TempStorage.getInstance();
    instanceData.getDataFiles();

    context.result(String.valueOf(instanceData));
  }

  public static void runPOST(Context context){
    context.result("Got GET. But it is only supported for control sequences and heartbeat.");
  }


}
