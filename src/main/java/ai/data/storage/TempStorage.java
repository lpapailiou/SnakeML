package ai.data.storage;

import ai.data.BatchEntity;
import java.util.ArrayList;
import java.util.List;


public class TempStorage {

  private static TempStorage instance;
  private final List<BatchEntity> batches = new ArrayList<>();

  private TempStorage() {
  }

  public void addBatch(BatchEntity batchData) {
    batches.add(batchData);
  }

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
