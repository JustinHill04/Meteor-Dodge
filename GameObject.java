import java.awt.Color;
import java.awt.Rectangle;

// Must have at least one parent class that has 2 or more child classes that extend it
public abstract class GameObject {
    protected int x, y, width, height;
    protected Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}