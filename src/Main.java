import javax.swing.*;
import java.awt.*;

// The class of the entrance
public class Main {

    public static void main(String[] args) {
        int nX=6, nY=5;     // Set default size
        // Get the size from the user
        // nX is the upper limit of attempts, nY is the word length
        if (args.length>0)
            nX=Integer.parseInt(args[0]);
        if (args.length>1)
            nY=Integer.parseInt(args[1]);

        // Set a modern UI theme, the default is not very beautiful
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a file chooser
        FileDialog dialog = new FileDialog((Frame) null, "Select a TXT file containing the Wordle words", FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        //System.out.println(file + " chosen.");

        // Create a WordList object to read the words from the file
        WordList wl = new WordList(file);

        // Create a WordBoard object, the main window
        new GameFrame(440, 640, nX, nY, wl);
    }
}