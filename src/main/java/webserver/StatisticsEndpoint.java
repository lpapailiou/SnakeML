package webserver;

import ai.data.BatchEntity;
import ai.data.storage.TempStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.core.util.Header;
import io.javalin.http.Context;

import java.util.List;

/**
 * This is a helper class, designed to collect all available batch data files from the temporary
 * storage, parse them to JSON strings and pass them to the web server.
 */
class StatisticsEndpoint {

  private StatisticsEndpoint() {}

  static void listAllBatches(Context context) {

    context.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

    List<BatchEntity> batches = TempStorage.getInstance().getBatches();

    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonData = mapper.writeValueAsString(batches);
      context.result(jsonData);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      context.result("{\"error\": \"An error occured while fetching game data\"}");
    }

  }

}
