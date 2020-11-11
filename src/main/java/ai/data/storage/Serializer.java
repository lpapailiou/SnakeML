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

    public static void save(NeuralNetwork net) {      // TODO: remove if not used? document?
        String fileName = "src/main/resources/serialized/NeuralNet" + System.currentTimeMillis() + ".ser";
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(net);
            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork load() {
        String filename = "NeuralNet_16x16_12_15_4_0-8.ser";    // TODO: put String to config file?
        File tempfile = createTempFile("serialized/"+filename, filename);

        NeuralNetwork phoenix = null;
        try {
            InputStream inputStrean = new FileInputStream(tempfile);
            ObjectInputStream in = new ObjectInputStream(inputStrean);
            phoenix = (NeuralNetwork) in.readObject();

            inputStrean.close();
            in.close();

            //System.out.println("success? " + phoenix != null);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return phoenix;
    }

    private static File createTempFile(String resource, String fileName) {    // TODO: remove if not used? document?
        try {
            InputStream htmlFile = Serializer.class.getClassLoader().getResourceAsStream(resource);
            File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
            Path tempPath = tempFile.toPath();
            Files.copy(htmlFile, tempPath, StandardCopyOption.REPLACE_EXISTING);
            //tempFile.deleteOnExit();
            //URI url = tempFile.toURI();
            //Desktop.getDesktop().browse(url);
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("fail");
    }
}
