package ai.data.storage;

import webserver.ItemHolder;
import java.util.ArrayList;
import java.util.List;


public class TempStorage {

  private static TempStorage instance;
  private List<String> jsonStringList;
  ItemHolder itemHolder = new ItemHolder();

  private TempStorage() {
    jsonStringList = new ArrayList<>();
  }

  void addBatchData(String file) {
    jsonStringList.add(file);
    itemHolder.produce(jsonStringList);
  }

  public List<String> getJsonStrings() {
    return new ArrayList<>(jsonStringList);
  }

  public static synchronized TempStorage getInstance() {
    if (instance == null) {
      instance = new TempStorage();
    }
    return instance;
  }

}
