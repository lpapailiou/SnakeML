package webserver;

import ai.GameBatch;
import ai.data.storage.TempStorage;
import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import io.javalin.http.Context;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {

  private final CloseableHttpClient httpClient = HttpClients.createDefault();

  public static void runRequest(Context context) throws IOException, InterruptedException {

    HttpClient obj = new HttpClient();

    try {
      //System.out.println("Got GET. But it is only supported for control sequences and heartbeat.");
      obj.doGET();
      context.result("Got GET. But it is only supported for control sequences and heartbeat.");

      System.out.println("Got POST...");
      obj.doPost();
      obj.close();

    } finally {
      obj.close();
    }
  }

  private void close() throws IOException {
    httpClient.close();
  }

  private void doGET() throws IOException, InterruptedException {

    HttpGet request = new HttpGet("http://localhost:8050/");

    request.addHeader(HttpHeaders.CONTENT_TYPE, "application/text");

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      System.out.println(response.getStatusLine().toString());

      HttpEntity entity = response.getEntity();
      Header headers = entity.getContentType();
      System.out.println(headers);

      if (entity != null) {
        String result = EntityUtils.toString(entity);
        System.out.println(result);
      }
    }
  }

  private void doPost() throws IOException, InterruptedException {

    TempStorage instanceData = TempStorage.getInstance();

    HttpPost post = new HttpPost("http://localhost:8050/");

    post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

    instanceData.getDataFiles();

    post.setEntity(instanceData.getDataFiles());

    try (CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post)) {

      System.out.println(EntityUtils.toString(response.getEntity()));
    }

  }

//  public static void run(Context context) {
//
//    INeuralNetworkConfig config = Config.getInstance();
//    int generations = config.getGenerationCount();
//    int population = config.getPopulationSize();
//
//    Config.getInstance().setBoardWith(12);
//    Config.getInstance().setBoardHeight(12);
//    config.setGenerationCount(400);
//    config.setPopulationSize(2000);
//    GameBatch batch = new GameBatch(new NeuralNetwork(0.999, 12, 32, 32, 32, 4));
//    batch.run();
//    context.result(batch.getJsonString().replaceAll(",", ",\n"));
//    context.result(batch.getJsonString());
//    batch.saveJsonData();
//
//    config.setGenerationCount(generations);
//    config.setPopulationSize(population);
//
//  }
}
