package ee;

import ee.models.player;
import ee.repository.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// What is this game about?
// You are summoned here to save bugs and give them new hope.
// But you don't want to save those nasty wasps, better not!
public class Main extends Canvas implements KeyListener, MouseListener, ComponentListener  {

    public static Main main;
    public static String name = "Lord of the Flies";
    public static double version = 1.0;
    public static int FPS;
    public static String user;
    public static int CurrentElements = 0;
    public static boolean gamelost;
    public static int score = 0;
    public static int scoreYourBest = 0;
    public static int ItemSize = 48;
    public static int BorderSize = 3;
    public static int Bugs = 0;
    private JFrame gameScreen;
    private boolean running;
    public static int WindowWidth = 0;
    public static int WindowHeight = 0;
    public static boolean saveToCloud = true;

    // Performance tuning variables
    private int FPSTarget = 1;
    public static int MaxElements = 10;

    // Networking
    public static String DBConnectionString = "jdbc:mysql://d25612.mysql.zone.ee/d25612sd97468?user=d25612sa94704&password=4sn8EK";
    Toolkit tk = Toolkit.getDefaultToolkit();

    // Instance (non-static) methods work on objects that are of a particular type (the class).
    gameArea Game = new gameArea();

    // Constructor
    public Main() {
        WindowWidth = ((int) tk.getScreenSize().getWidth());
        WindowHeight = ((int) tk.getScreenSize().getHeight());
        log("--- Diagnostic: ScreenW "+WindowWidth);
        log("--- Diagnostic: ScreenX "+WindowHeight);
        try {
            gameDataLocal Data = new gameDataLocal();
            scoreYourBest = Data.localHighScore();
            log("--- Loaded local high score");
        } catch (IOException ioError) {
            log("--- " +ioError);
        }
    }

    // Main mehtod
    public static void main(String[] args) {
        main = new Main();
        main.init();
    }

    // Starting the engine
    private void init() {
        log("BattleIT Group LTD © 2015");
        log("Love, Sander Soots");
        gameScreen = new JFrame(name);
        gameScreen.setVisible(true);
        gameScreen.setResizable(true);
        gameScreen.setPreferredSize(new Dimension(600, 600));
        // For windows resized event
        gameScreen.addComponentListener(this);
        gameScreen.setMinimumSize(gameScreen.getPreferredSize());
        //gameScreen.setExtendedState(Frame.MAXIMIZED_BOTH);
        gameScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
        gameScreen.add(main);

        // Overriding application closing, to save high score to local storage
        gameScreen.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    gameDataLocal Data = new gameDataLocal();
                    Data.localHighScoreSave();
                    log("--- Saved HighScore");
                } catch (IOException ioError) {
                    log("--- " +ioError);
                }

                if (JOptionPane.showConfirmDialog(gameScreen,
                        "Are you sure to close this game?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        log("--- Starting game engine");
        loop();
    }

    // Game engine
    public void loop() {
        running = true;

        int fps = 0;

        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        final double ns = 1000000000.0 / FPSTarget;
        double delta = 0;

        while (running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {

                if (fps <= FPSTarget) {
                    fps++;

                    update();
                    repaint();

                    delta--;
                }
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;

                //log("--- Running at " + fps + " FPS and UPS");
                FPS = fps;
                fps = 0;

            }
        }

    }

    // With each frame, we think
    public void update() {
        if (score > scoreYourBest ) scoreYourBest = score;
        Game.addNpc();
        Game.Move();
        //log("--- Updated game mind");
    }

    // Manage gamestate
    public void paint(Graphics g) {
        if (gamelost != true){
            Game.Render(g);
            Game.RenderHUD(g);
        }
        else {
            Game.RenderLostScreen(g);
            running = false;
            log("--- Stopped game, this is the last frame!");
        }
        //log("--- Rendered frame");
    }

    public void saveHighScore() {
    user = JOptionPane.showInputDialog(this, "Enter your nickname: ", "", JOptionPane.INFORMATION_MESSAGE);
    user = user.trim();
    try {
        while (user.equals("")) {
            user = JOptionPane.showInputDialog(this, "Enter your nickname: ", "", JOptionPane.INFORMATION_MESSAGE);
            user = user.trim();
        }
        gameDataCloud add = new gameDataCloud();
        add.addResult(new player(0, user, score));
    } catch (NullPointerException e) {
        System.out.println("Something went wrong!" + e);
    } catch (SQLException e) {
        System.out.println("Something went wrong!" + e);
    }
    }
    // Mouse click event
    public void mouseClicked(MouseEvent e) {
        Game.Delete(e.getX(), e.getY());
    }

    public void keyPressed(KeyEvent e) {
        //log(""+e.getKeyCode());

        if (e.isControlDown()==true) {
            saveHighScore();
        }

        // When you press Alt, launch HighScores
        if (e.getKeyCode()==18) {
            new LaunchHighScores();
        }
        // When you press Space, reset game
        if (e.getKeyCode()==32) {
            score = 0;
            gamelost = false;
            Game.Purge();
        }
    }

    public void componentHidden(ComponentEvent e) {
        log("--- Hidden, game is paused");
        running = false;
    }

    public void componentMoved(ComponentEvent e) {
        log("--- Moved, hopefully it will render");
    }

    public void componentResized(ComponentEvent e) {
        log("--- Resized, resize gameArea");
        WindowWidth = gameScreen.getWidth();
        WindowHeight = gameScreen.getHeight();
        Game.Purge();
        log("--- Re-Initialization of screen " + WindowWidth);
        log("--- Re-Initialization of screen "+WindowHeight);
    }

    public void componentShown(ComponentEvent e) {
        log("--- Shown, game is resumed");
        running = true;
    }

    // Logging helper methods
    public void log(String string) {
        System.out.println("[" + name + "] [" + version + "] " + string);
    }
    public void log() {
        System.out.println("[" + name + "] [" + version + "]");
    }

    // Just because JAVA...
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}