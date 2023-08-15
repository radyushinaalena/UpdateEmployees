package ru.skypro.lessons.springboot.weblibrary.helperMethod;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class HelperMethods {

    public static String readTextFromFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.lines(path)
                    .collect(Collectors.joining());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return "";
        }
    }

    public static void writeTextToFile(String text, String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, text);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
