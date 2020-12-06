//package webserver;
//
//
//import static junit.framework.TestCase.assertEquals;
//
//import ai.GameBatch;
//import ai.neuralnet.NeuralNetwork;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import kong.unirest.HttpResponse;
//import kong.unirest.Unirest;
//import main.configuration.ITestConfig;
//import main.configuration.Mode;
//import org.junit.Test;
//
//public class WebServerTest {
//
//  private final WebServer webServer = new WebServer();
//
//  @Test
//  public void PostRequestTest() throws InterruptedException {
//    webServer.runServer();
//    HttpResponse response = Unirest.post("http://localhost:8050/").asString();
//    assertEquals(200, response.getStatus());
//    String postResponseBody = "Got POST. But it is only supported for control sequences and heartbeat.";
//    assertEquals(postResponseBody, response.getBody());
//
//    webServer.stopServer();
//    Thread.sleep(2000);
//  }
//
//  @Test
//  public void GetRequestTest() throws InterruptedException {
//    webServer.runServer();
//
//    List<String> expectedResultJson;
//
//    ITestConfig config = ITestConfig.getInstance();
//    int generations = config.getGenerationCount();
//    int population = config.getPopulationSize();
//    config.setMode(Mode.NEURAL_NETWORK);
//    config.setBoardWidth(12);
//    config.setBoardHeight(12);
//    config.setGenerationCount(10);
//    config.setPopulationSize(10);
//    GameBatch batch = new GameBatch(new NeuralNetwork(0.999,12,32,32,32,4));
////    batch.run();
//
//    config.setGenerationCount(generations);
//    config.setPopulationSize(population);
//
//    expectedResultJson = new ArrayList<>();
////    expectedResultJson.add(batch.getJsonString());
//    HttpResponse response = Unirest.get("http://localhost:8050/?cmd=load").asString();
//
//    assertEquals(expectedResultJson.toString(), response.getBody());
//
//    webServer.stopServer();
//    Thread.sleep(2000);
//
//  }
//}