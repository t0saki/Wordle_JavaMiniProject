import javax.swing.*;
import java.awt.*;

// The class of the letter board
public class WordBoard extends JPanel {
    private final int nX, nY;        // Tried times and word length
    private int lastRow, lastCol;    // The last position of the grid
    private JLabel[][] words;        // The letter grid
    private final WordList wl;

    // Initialize the game
    public void init() {
        // Initialize the grid
        this.removeAll();
        this.revalidate();
        this.repaint();
        // Create the grid
        this.setLayout(new GridLayout(nX, nY, 5, 5));
        words = new JLabel[nX][nY];
        for (int i = 0; i < nX; i++)
            for (int j = 0; j < nY; j++) {
                words[i][j] = new JLabel(" ", JLabel.CENTER);
                words[i][j].setFont(new Font("Cascadia Code", Font.PLAIN, 32));
                words[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                this.add(words[i][j]);// Add each letter to the grid
            }
        lastRow = lastCol = 0;
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        highlightRow(0);              // Highlight the first row
    }

    // Constructor
    public WordBoard(int nX, int nY, WordList wl) {
        this.wl = wl;
        this.nX = nX;
        this.nY = nY;
        init();
        setVisible(true);
    }

    // Highlight the row which is editing
    private void highlightRow(int row) {
        /*
        for(int i=0;i<nX;i++)
            words[lastRow][i].setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        lastRow=row;
        */
        for (int i = 0; i < nY; i++)
            words[row][i].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));
        highlightCol(0);
        //return new Dimension(lastCol,lastRow);
    }

    // Highlight the column which is editing
    private void highlightCol(int col) {
        if (col > 0)
            words[lastRow][col - 1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        //lastCol=col;
        if (col < nY)
            words[lastRow][col].setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));
        if (col < nY - 1)
            words[lastRow][col + 1].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));
        //return new Dimension(lastCol,lastRow);
    }

    // Add the word to the grid from the input
    public void setWord(String word) {
        if (lastCol < nY) {
            words[lastRow][lastCol].setText(word.toUpperCase());
            highlightCol(++lastCol);
        }
    }

    // Set the color of each letter
    private boolean setColor(String word, String ans) {
        boolean flag = true;
        int[] letter = new int[26];     // Count the number of each letter
        for (int i = 0; i < word.length(); i++)
            letter[ans.charAt(i) - 'A']++;
        for (int i = 0; i < nY; i++) {
            if (word.charAt(i) == ans.charAt(i)) {
                words[lastRow][i].setBorder(BorderFactory.createLineBorder(Color.GREEN, 8, true));
                letter[word.charAt(i) - 'A']--;// Mark the correct letter first
            } else
                flag = false;           // Has a wrong letter, the word is not correct
        }
        for (int i = 0; i < nY; i++)
            if (ans.charAt(i) != word.charAt(i))
                if (letter[word.charAt(i) - 'A']-- > 0)// Mark the wrong letter according to the remaining number
                    words[lastRow][i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 8, true));
                else
                    words[lastRow][i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 8, true));

        return flag;
    }

    // Check the word is the correct word or not
    public int checkWord() {
        int isSuccess = 1;

        // Check the length of the word is correct
        if (words[lastRow][nY - 1].getText().equals(" ")) {
            return -2;
        }

        // Get the complete word
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < nY; i++) {
            word.append(words[lastRow][i].getText());
        }

        // Check the word is in the word list
        if (!wl.isWord(word.toString())) {
            return -1;
        }

        // Label each letter with the corresponding color
        if (!setColor(word.toString(), wl.getWord()))
            isSuccess = 0;

        if (lastRow == nX - 1 && isSuccess != 1)
            return -3;              // Max tries reached
        else if (isSuccess == 0) {
            highlightRow(++lastRow);
            lastCol = 0;            // Not a success, next row
        }
        return isSuccess;
    }

    // Delete the last word when press Backspace
    public void backWord() {
        if (lastCol > 0) {
            words[lastRow][lastCol - 1].setText(" ");
            highlightCol(--lastCol);
        }
    }
}
