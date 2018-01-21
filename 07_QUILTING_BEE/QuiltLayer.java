package quiltingbee;

import java.awt.*;
import javax.swing.*;

/**  A quilt layer. */
public class QuiltLayer extends JPanel {
    /** Default values for position and size. */
    static final int RECT_X = 20;
    static final int RECT_Y = RECT_X;
    static final int RECT_WIDTH = 100;
    static final int RECT_HEIGHT = RECT_WIDTH;
    
    public float scale;
    public int r, g, b;
    public Point pos;
    public Dimension size;
    
    public QuiltLayer() {
      super();

      pos = new Point(RECT_X, RECT_Y);
      size = new Dimension(RECT_WIDTH, RECT_HEIGHT); 
    }

    /** Copy constructor. */
    public QuiltLayer(QuiltLayer other) {
        this.scale = other.scale;
        this.r = other.r;
        this.g = other.g;
        this.b = other.b;
        this.pos = new Point(other.pos);
        this.size = new Dimension(other.size);
    }
    
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.setColor(new Color(this.r, this.g, this.b));
      g.fillRect(pos.x, pos.y, size.width, size.height);
    }
    
    // @Override
    // public Dimension getPreferredSize() {
    //   return new Dimension(size.width + 2 * pos.x, size.height + 2 * pos.y);
    // }

    public String toString() {
        return String.format("[%f, %d, %d, %d]", scale, r, g, b);
    }
}