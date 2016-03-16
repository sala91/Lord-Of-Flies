package ee.interfaces;

import java.awt.*;

// Every NPC in this game must implement these features!
public interface IgameArea {
    // Npc renders itself
    public void Render(Graphics g);
    // Moves npc to cordinate x y
    public void Move(int hx, int hy);
    // Delets npc clicked on cordinate x y
    public boolean isHit(int hx, int hy);
    // Tell npc that it is touched
    public void getMessage();
    // Where is that npc excatly?
    public int getX();
    public int getY();
}
