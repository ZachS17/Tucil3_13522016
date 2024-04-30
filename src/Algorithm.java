// import net.sf.extjwnl.data.IndexWord;
// import net.sf.extjwnl.data.POS;
// import net.sf.extjwnl.data.Synset;
// import net.sf.extjwnl.dictionary.Dictionary;
// import net.sf.extjwnl.JWNLException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

interface AlgorithmCall {
    void solve(String kataAwal, String kataAkhir);
}

public class Algorithm {
    public void isWordValid(String kata) {
        // try {
        // // Initialize ExtJWNL dictionary
        // Dictionary dictionary = Dictionary.getDefaultResourceInstance();

        // // Look up a word in WordNet
        // String word = "dog";
        // IndexWord indexWord = dictionary.lookupIndexWord(POS.NOUN, word);

        // // If the word is found, print its synsets (sets of synonyms)
        // if (indexWord != null) {
        // System.out.println("Synonyms for \"" + word + "\":");
        // for (Synset synset : indexWord.getSenses()) {
        // System.out.println("- " + synset.getGloss());
        // }
        // } else {
        // System.out.println("Word \"" + word + "\" not found in WordNet.");
        // }
        // } catch (JWNLException e) {
        // e.printStackTrace();
        // }
        String filePath = "../lib/words.txt"; // Path to the text file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the target word
                if (line.contains(kata)) {
                    found = true;
                    break; // Exit loop if the word is found
                }
            }

            // Print result
            if (found) {
                System.out.println("Word '" + kata + "' found in the text file.");
            } else {
                System.out.println("Word '" + kata + "' not found in the text file.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Algorithm game = new Algorithm();
        game.isWordValid("wat");
    }
}