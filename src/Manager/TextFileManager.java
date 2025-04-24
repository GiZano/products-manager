package Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileManager {

    public static String readFileAsString(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    public static void writeStringToFile(String filePath, String content) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))){
            writer.write(content);
        }
    }
}
