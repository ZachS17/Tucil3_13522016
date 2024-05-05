package Utilities;

import java.io.*;
import java.util.*;

public class Dictionary {
    public static Set<String> dictionary = new HashSet<>();

    public static void readDictionary() {
        String filePath = "../src/Utilities/dictionary.txt"; // rute file txt
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // baca setiap line dari file
            String word;
            while ((word = reader.readLine()) != null) {
                // pengecekan kata
                dictionary.add(word.toLowerCase());
            }
        } catch (IOException e) {
            // misal error baca
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static boolean isWordValid(String kata) {
        return dictionary.contains(kata);
    }
}
