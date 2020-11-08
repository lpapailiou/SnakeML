package ai.data.storage;

import java.util.ArrayList;
import java.util.List;

public class TempStorage {

  private static TempStorage instance;
  private List<String> jsonStringList;

  private TempStorage() {
    jsonStringList = new ArrayList<>();
  }

  void addBatchData(String file) {
    jsonStringList.add(file);

    // TODO: push file to server
  }

  public List<String> getJsonStrings() {
    return new ArrayList<>(jsonStringList);
  }

  static synchronized TempStorage getInstance() {
    if (instance == null) {
      instance = new TempStorage();
    }
    return instance;
  }

}
