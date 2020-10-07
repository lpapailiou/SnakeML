package ai.data.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TempStorage {

  public static TempStorage instance;
  private List<File> dataFiles;

  private TempStorage() {
    this.instance = this;
    dataFiles = new ArrayList<>();
  }

  public void addFile(File file) {
    dataFiles.add(file);
  }

  public List<File> getDataFiles() {
    return new ArrayList<>(dataFiles);
  }

  public static synchronized TempStorage getInstance() {
    if (instance == null) {
      new TempStorage();
    }
    return instance;
  }


}
