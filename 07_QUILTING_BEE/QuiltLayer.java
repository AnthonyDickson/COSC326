package quiltingbee;

import java.awt.*;
import javax.swing.*;

/**  A quilt layer. */
public class QuiltLayer extends JPanel {
    static final int RECT_X = 20;
    static final int RECT_Y = RECT_X;
    static final int RECT_WIDTH = 100;
    static final int RECT_HEIGHT = RECT_WIDTH;
    
    public float scale;
    public int r, g, b;
    
    public QuiltLayer() {
      super();
    }
    
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(new Color(this.r, this.g, this.b));
      g.fillRect((int) (scale * RECT_X), (int) (scale * RECT_Y), (int) (scale * RECT_WIDTH), (int) (scale *RECT_HEIGHT));
    }
    
    @Override
    public Dimension getPreferredSize() {
      return new Dimension((int) (scale * (RECT_WIDTH + 2 * RECT_X)), (int) (scale * (RECT_HEIGHT + 2 * RECT_Y)));
    }

    public String toString() {
        return String.format("[%f, %d, %d, %d]", scale, r, g, b);
    }
}