package ee.repository;

import ee.Main;

import java.io.*;
// Implements local high score saving and loading.
// You should never rely on local high score to make
// decisions on DataCloud or Azure HighScores!
public class gameDataLocal {

    public void localHighScoreSave() throws IOException {
        PrintWriter storage = new PrintWriter(new FileWriter("score.txt"));
        storage.println(Main.scoreYourBest);
        storage.close();
    }

    public int localHighScore() throws IOException {
        BufferedReader storage = new BufferedReader(new FileReader("score.txt"));
        String valueInTransit = storage.readLine();
        storage.close();
        return Integer.parseInt(valueInTransit);
    }

}
