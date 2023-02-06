import javax.swing.*;
import java.util.ArrayList;

// The class containing the word list
public class WordList {
    private final ArrayList<String> words;  // The words in the list
    private String word;                    // The selected word

    public WordList(String path) {
        words = new ArrayList<>();
        // Use try-catch to handle exceptions, file operations are not trivial
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.charAt(0) != '#')
                    words.add(line.toUpperCase());        // Read the file line by line
            }
            in.close();
        } catch (Exception e) {
            // Handle exceptions
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading file: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        refreshWord();
    }

    // Get the chosen word
    public String getWord() {
        return word;
    }

    // Select a random word from the list
    public void refreshWord() {
        int index = (int) (Math.random() * words.size());
        word = words.get(index);
        //System.out.println(word);
    }

    // Check if the word is in the list
    public boolean isWord(String word) {
        return words.contains(word);
    }
}
