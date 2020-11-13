package ai.data.storage;

import ai.data.BatchEntity;
import webserver.ItemHolder;
import java.util.ArrayList;
import java.util.List;


public class TempStorage {

  private static TempStorage instance;
//  private final List<String> jsonStringList = new ArrayList<>();
  private final List<BatchEntity> batches = new ArrayList<>();
  ItemHolder itemHolder = new ItemHolder();

  private TempStorage() {}

  public void addBatch(BatchEntity batchData) {
    batches.add(batchData);
//    jsonStringList.add(file);
//    itemHolder.produce(jsonStringList);
  }

//  public List<String> getJsonStrings() {
//    return new ArrayList<>(jsonStringList);
//  }

  public static synchronized TempStorage getInstance() {
    if (instance == null) {
      instance = new TempStorage();
    }
    return instance;
  }

  public List<BatchEntity> getBatches() {
    return batches;
  }
}
