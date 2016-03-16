package ee.repository;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ee.HighScores;

public class LaunchHighScores extends JFrame {

    HighScores HighScoresApplet;

    public LaunchHighScores() {

        super("TOP100");
        HighScoresApplet = new HighScores();
        HighScoresApplet.start();
        HighScoresApplet.init();
        getContentPane().add(HighScoresApplet.getContentPane());
        setSize(500, 500);
        setLocation(704,196);
        setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LaunchHighScores();
            }
        });
    }

}
