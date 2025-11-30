import java.awt.Color;
import java.awt.Graphics;

// Child Class extending Parent Class and Implementing Interface
public class Meteor extends GameObject implements Movable {
    
    private int speed;

    // Must have at least one overloaded Constructor
    
    // Default speed
    public Meteor(int x, int y) {
        super(x, y, 20, 20, Color.RED);
        this.speed = 3;
    }

    // Custom speed
    public Meteor(int x, int y, int speed) {
        super(x, y, 20, 20, Color.RED);
        this.speed = speed;
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
    
    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight;
    }
}