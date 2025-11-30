import java.awt.Color;
import java.awt.Graphics;

// Child Class extending Parent Class and Implementing Interface
public class Player extends GameObject implements Movable {
    
    private int velX = 0;

    public Player(int x, int y) {
        super(x, y, 30, 30, Color.BLUE);
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    // Must have at least one overridden method
    @Override
    public void move() {
        x += velX;
        // Keep player inside screen bounds 
        if (x < 0) x = 0;
        if (x > 750) x = 750; 
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}