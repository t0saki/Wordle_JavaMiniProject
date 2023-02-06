import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The class of the main window
public class GameFrame extends JFrame {
    WordBoard wb;
    private final JLabel status;        // Status bar under the board
    private final WordList wl;
    KeyAdapter kl = new KeyAdapter() {    // Key listener for the keyboard to handle the input
        public void keyPressed(KeyEvent e) {
            status.setText(" ");
            processGame(e);
        }
    };

    // Initialize the game
    private void initNB() {
        removeKeyListener(kl);  // Reset the KeyListener to avoid multiple inputs
        wb.init();              // Initialize the game
        wl.refreshWord();
        wb.setVisible(true);
        status.setText(" ");    // Clear the status bar
        addKeyListener(kl);
        requestFocus();         // set the focus to the board to listen to the input
    }

    // Show the game result
    private void finishGame(boolean isWin) {
        String msg = (isWin ? "You win!" :
                "You lose! The Answer is: " + wl.getWord()) + "\nPlay again?";
        switch (JOptionPane.showConfirmDialog(null, msg,
                "Game Finished", JOptionPane.YES_NO_CANCEL_OPTION)) {
            case JOptionPane.YES_OPTION:
                initNB();               // Initialize the game
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);  // Exit the game
                break;
            case JOptionPane.CANCEL_OPTION:
                break;                  // Do nothing
        }
        requestFocus();
    }

    // Process the key input
    private void processGame(KeyEvent key) {
        //System.out.println("key: " + key.getKeyChar());
        int keyCode = key.getKeyCode();

        // Different treatment for different keys
        if (keyCode == KeyEvent.VK_ENTER) {  // Press Enter
            switch (wb.checkWord()) {
                case 1 -> {
                    status.setText("SUCCESS");
                    status.setForeground(Color.GREEN);
                    // Remove kl to stop the input
                    removeKeyListener(kl);
                    finishGame(true);
                }
                case 0 -> {
                    status.setText("Try Again");
                    status.setForeground(Color.CYAN);
                }
                case -1 -> {
                    status.setText("Not a Word");
                    status.setForeground(Color.CYAN);
                }
                case -2 -> {
                    status.setText("Not Completed");
                    status.setForeground(Color.CYAN);
                }
                case -3 -> {
                    status.setText("FAILED");
                    status.setForeground(Color.RED);
                    // Remove kl to stop the input
                    removeKeyListener(kl);
                    finishGame(false);
                }
            }
            //status.setText("SUCCESS");
        } else if (keyCode <= KeyEvent.VK_Z && keyCode >= KeyEvent.VK_A) {
            wb.setWord(key.getKeyChar() + "");    // Press a-z
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            wb.backWord();                      // Press Backspace
        } else {
            status.setText("Invalid Input");    // Invalid input
            status.setForeground(Color.CYAN);
        }
    }


    public GameFrame(int width, int height, int nX, int nY, WordList wl) {
        // Set the size and style of the frame
        setTitle("Wordle Game");
        this.wl = wl;
        setLayout(new BorderLayout());
        setSize(width, height);
        setVisible(true);
        rootPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create the word board
        wb = new WordBoard(nX, nY, wl);
        wb.setVisible(false);
        add(wb, BorderLayout.CENTER);

        JLabel title = new JLabel("Wordle Game", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        add(title, BorderLayout.NORTH);

        // Create an answer dialog
        JButton ansBtn = new JButton("Answer");
        ansBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        ansBtn.addActionListener(
                event -> {
                    JOptionPane.showMessageDialog(null, "The Answer is: " + wl.getWord(),
                            "Answer", JOptionPane.INFORMATION_MESSAGE);
                    requestFocus();
                });
        ansBtn.setVisible(false);

        // sButton is a panel to hold 4 buttons on the bottom
        JPanel sButton = new JPanel();
        sButton.setLayout(new GridLayout(1, 4, 5, 5));

        // Create a button to start and restart the game
        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        startBtn.addActionListener(event -> {
            initNB();
            startBtn.setText("Restart");
            ansBtn.setVisible(true);
        });
        sButton.add(startBtn);
        sButton.add(ansBtn);

        // Create a button to get help
        JButton helpBtn = new JButton("Help");
        helpBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        helpBtn.addActionListener(
                event -> {
                    JOptionPane.showMessageDialog(null, """
                            Wordle is a very popular word game recently. This program is an\040
                            imitation of the original version published in the New York Times.
                                                    
                            How to play:
                            Use the letters A-Z of the keyboard in order to enter a five-digit word,\040
                            press Backspace to delete the last character entered in the same line,\040
                            and press Enter to check the word you entered.
                            After checking, each letter is marked in green, yellow or gray.
                            Green indicates that the letter is correct and in the correct position;
                            Yellow indicates that it is in the answer but in the incorrect position;
                            Gray means that it is not in the answer at all.
                                                        
                            Please note: As with the original Wordle, recurring letters may still be\040
                            grayed out, for example when the answer is RAPID, typing APPLE will\040
                            result in the first P being grayed out and the second P green to indicate\040
                            that there is only one letter P in the answer.
                                                        
                            Your goal is to guess the word within six times, i.e. all letters are green.
                            Have fun playing the game!
                            """, "Help", JOptionPane.INFORMATION_MESSAGE);
                    requestFocus();
                });
        sButton.add(helpBtn);

        // Create a button to get information about the game
        JButton aboutBtn = new JButton("About");
        aboutBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutBtn.addActionListener(
                event -> {
                    JOptionPane.showMessageDialog(null, """
                            Zhengxiao Wu
                            Version 1.01
                            QMUL Number: 200978936
                            BUPT Number: 2020213235""", "About", JOptionPane.INFORMATION_MESSAGE);
                    requestFocus();
                });
        sButton.add(aboutBtn);

        // SouthPanel is a panel to hold the status bar and 4 buttons on the bottom
        JPanel SouthPanel = new JPanel();
        SouthPanel.setLayout(new GridLayout(2, 1, 5, 5));
        status = new JLabel(" ", JLabel.CENTER);
        status.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        SouthPanel.add(status);
        SouthPanel.add(sButton);

        add(SouthPanel, BorderLayout.SOUTH);
        requestFocus();

        // Create a Listener to quit the game
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
}
