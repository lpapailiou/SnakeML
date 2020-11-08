package ai.data.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileHandler {

  private String data;

  public JsonFileHandler(String data) {
    this.data = data;
  }

  public void saveToTempStorage() {
    TempStorage storage = TempStorage.getInstance();
    storage.addBatchData(data);
  }

  public void saveToFileSystem() {
    File tmpFile;
    FileWriter writer = null;
    BufferedReader reader = null;
    try {
      tmpFile = File.createTempFile("SnakeML_batchData_", ".json");      // saves to USER\AppData\Local\Temp
      writer = new FileWriter(tmpFile);
      writer.write(data);
      reader = new BufferedReader(new FileReader(tmpFile));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}
