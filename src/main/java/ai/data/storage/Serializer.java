package ai.data.storage;

import ai.neuralnet.NeuralNetwork;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This class is used to deserialize a specific, trained NeuralNetwork from the resources folder.
 * The deserialized object may be used to visualize replay or test with different board sizes.
 */
public class Serializer {

  private Serializer() {
  }

  /**
   * This method will load a specific trained NeuralNetwork from the resources folder.
   *
   * @return a trained NeuralNetwork
   */
  public static NeuralNetwork load() {
    String filename = "NeuralNet_16x16_12_15_4_0-8.ser";
    File tempFile = createTempFile("serialized/" + filename, filename);

    NeuralNetwork phoenix;
    try {
      InputStream inputStream = new FileInputStream(tempFile);
      ObjectInputStream in = new ObjectInputStream(inputStream);
      phoenix = (NeuralNetwork) in.readObject();

      inputStream.close();
      in.close();

    } catch (IOException | ClassNotFoundException e) {
      throw new DeserializationException("failed to deserialize NeuralNetwork!", e);
    }
    return phoenix;
  }

  private static File createTempFile(String resource, String fileName) {
    try {
      InputStream htmlFile = Serializer.class.getClassLoader().getResourceAsStream(resource);
      File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
      Path tempPath = tempFile.toPath();
      Files.copy(htmlFile, tempPath, StandardCopyOption.REPLACE_EXISTING);
      return tempFile;
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DeserializationException("failed to deserialize");
  }
}
