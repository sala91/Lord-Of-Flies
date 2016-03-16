package ee.repository;

import ee.Main;
import ee.interfaces.IgameArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Implements interface iGameArea
public class gameArea {

    java.util.List<IgameArea> npcPlayers = new ArrayList<>();
    // List all NPC in the game
    Class<?>[] role = {Bug.class, Wasp.class};
    Random r = new Random();
    private TexturePaint paint;

    public void addNpc(){
        // Sanity check for element count
        // TODO: Avoid creating elements on top of each other
        while (Main.MaxElements > npcPlayers.size()){
            if (Main.Bugs == 0) {
                addNpc(Bug.class);
                Main.Bugs++;
            }
            else
            {
                addNpc(role[r.nextInt(role.length)]);
            }
        }
    }

    public void Move() {
        // TODO: Avoid Moving element on top of another element
        for (int i = 0; i < npcPlayers.size(); i++) {
            Random rR = new Random();
            int xM = Main.ItemSize+Main.BorderSize+rR.nextInt(Main.WindowWidth-150);
            int yM = Main.ItemSize+Main.BorderSize+rR.nextInt(Main.WindowHeight-150);
            npcPlayers.get(i).Move(xM, yM);
        }
    }

    // Use this to clean up all npc's and let new life be born
    public void Purge() {
        npcPlayers.clear();
        Main.CurrentElements = 0;
        Main.Bugs = 0;
    }


    public <T> void addNpc(Class<T> klass){
        try {
            npcPlayers.add((IgameArea) klass.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public void Delete(int x, int y){
        for (int i = 0; i < npcPlayers.size(); i++) {
            if (npcPlayers.get(i).isHit(x, y)) {
                npcPlayers.remove(npcPlayers.get(i));
                Main.CurrentElements--;
            }
        }
    }

    public void Render(Graphics g){
        if (paint == null) {
            try {
                BufferedImage icon = null;
                String imgFilename = "grass.jpg";
                java.net.URL imgURL = getClass().getClassLoader().getResource(imgFilename);
                icon = ImageIO.read(imgURL);
                // Create TexturePaint instance the first time
                Graphics2D biG2d = (Graphics2D) icon.getGraphics();
                biG2d.drawImage(icon, 0, 0, Color.black, null);
                paint = new TexturePaint(icon,
                        new Rectangle(0, 0, icon.getWidth(), icon.getHeight()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Now actually do the painting of background
        Graphics2D g2d = (Graphics2D) g;
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(g2d.getClip());
        }

        // Calls out render function for NPC's
        for (IgameArea tak : npcPlayers) {
            tak.Render(g);
        }
    }

    // Renders scores with overlay on top of the game
    public void RenderHUD(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, Main.WindowWidth, 15);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.BOLD, 10));
        g.drawString("SCORE: "+Main.score, 10, 10);
        g.drawString("YOUR RECORD: "+Main.scoreYourBest, 100, 10);
        g.drawString("WORLD RECORD: ", 250, 10);
    }

    // Renders game loosing screen
    public void RenderLostScreen(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, Main.WindowWidth, Main.WindowHeight);
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.setColor(Color.red);
        g.drawString("GAME OVER", Main.WindowWidth/2-100, Main.WindowHeight/2-100);
        g.setColor(Color.white);
        g.drawString("SCORE:"+Main.score, Main.WindowWidth/2-100, 25+Main.WindowHeight/2-100);
        g.drawString("Press space for new life!", Main.WindowWidth/2-100, 100+Main.WindowHeight/2);
    }

}