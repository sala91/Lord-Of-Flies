package ee.repository;

import ee.Main;
import ee.interfaces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Sander on 30/05/2015.
 */
public class Wasp implements IgameArea {

    int x;
    int y;
    Random r = new Random();

    public Wasp(){
        x = Main.ItemSize+Main.BorderSize+r.nextInt(Main.WindowWidth-150);
        y = Main.ItemSize+Main.BorderSize+r.nextInt(Main.WindowHeight-150);
    }

    public BufferedImage getImage(){
        BufferedImage icon = null;
        String imgFilename = "wasp.png";
        java.net.URL imgURL = getClass().getClassLoader().getResource(imgFilename);
        if (imgURL != null) {
            try {
                icon =  ImageIO.read(imgURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Couldn't find file: " + imgFilename);
        }
        return icon;
    }

    @Override
    public void Render(Graphics g) {
        g.drawImage(getImage(), this.x-Main.ItemSize/2, this.y-Main.ItemSize/2, Main.ItemSize, Main.ItemSize, null);
    }

    public boolean isHit(int hx, int hy){
        if (hx>x-Main.ItemSize/2 && hx<x+Main.ItemSize/2 && hy>y-Main.ItemSize/2 && hy<y+Main.ItemSize/2){
            getMessage();
            return true;
        }
        else{
            return false;
        }
    }

    public void getMessage(){
        Main.gamelost = true;
    }

    public void Move(int hx, int hy) {
        x = hx;
        y = hy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
