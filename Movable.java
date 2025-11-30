import java.awt.Graphics;

// Must have at least one interface with 2 or more classes that implement it
public interface Movable {
    void move();
    void draw(Graphics g);
    java.awt.Rectangle getBounds();
}