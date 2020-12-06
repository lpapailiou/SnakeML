package ai.data.storage;

import ai.data.BatchEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as temporary storage of game batch statistics data. It will not persist data
 * permanently. It is implemented as Singleton, to assure just a single instance is instantiated.
 */
public class TempStorage {

  private static TempStorage instance;
  private final List<BatchEntity> batches = new ArrayList<>();

  private TempStorage() {
  }

  /**
   * Will add batch data set to temporary storage.
   *
   * @param batchData
   */
  public void addBatch(BatchEntity batchData) {
    batches.add(batchData);
  }

  /**
   * Assures that only one instance of TempStorage is created for this JVM.
   *
   * @return the Singleton instance
   */
  public static synchronized TempStorage getInstance() {
    if (instance == null) {
      instance = new TempStorage();
    }
    return instance;
  }

  /**
   * Returns all temporary stored batch statistics.
   *
   * @return current batch data
   */
  public List<BatchEntity> getBatches() {
    return batches;
  }
}
