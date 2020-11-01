package webserver;

import ai.GameBatch;
import ai.neuralnet.NeuralNetwork;
import main.configuration.Config;
import main.configuration.INeuralNetworkConfig;
import io.javalin.http.Context;

public class GameBatchController {

  // TODO: Dummy Test response - need to make it dynamic

  public static void run(Context context) {
    INeuralNetworkConfig config = Config.getInstance();
    int generations = config.getGenerationCount();
    int population = config.getPopulationSize();

    Config.getInstance().setBoardWith(12);
    Config.getInstance().setBoardHeight(12);
    config.setGenerationCount(400);
    config.setPopulationSize(2000);
    GameBatch batch = new GameBatch(new NeuralNetwork(0.999, 12, 32, 32, 32, 4));
    batch.run();
    context.result(batch.getJsonString().replaceAll(",", ",\n"));
    context.result(batch.getJsonString());
    batch.saveJsonData();

    config.setGenerationCount(generations);
    config.setPopulationSize(population);
  }
}
