package ai.data.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TempStorage {

  private static TempStorage instance;
  private List<File> dataFiles;

  private TempStorage() {
    dataFiles = new ArrayList<>();
  }

  void addFile(File file) {
    dataFiles.add(file);
  }

  public List<File> getDataFiles() {
    return new ArrayList<>(dataFiles);
  }

  public static synchronized TempStorage getInstance() {
    if (instance == null) {
      instance = new TempStorage();
    }
    return instance;
  }

}
