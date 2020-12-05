package ai.data.storage;

import ai.neuralnet.NeuralNetwork;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Serializer {

    public static NeuralNetwork load() {
        String filename = "NeuralNet_16x16_12_15_4_0-8.ser";
        File tempFile = createTempFile("serialized/"+filename, filename);

        NeuralNetwork phoenix = null;
        try {
            InputStream inputStream = new FileInputStream(tempFile);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            phoenix = (NeuralNetwork) in.readObject();

            inputStream.close();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        throw new RuntimeException("failed to deserialize");
    }
}
