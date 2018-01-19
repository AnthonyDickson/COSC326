package quiltingbee;

import java.awt.Color;
import java.awt.Dimension;

/** 
 * A layer of a quilt design. 
 * 
 * @author Anthony Dickson
 */
class QuiltLayer {
    /** Default values for position and size. */
    static final int RECT_WIDTH = 100;
    static final int RECT_HEIGHT = RECT_WIDTH;

    Color color;
    float scale;
    Dimension size;

    public QuiltLayer(float scale, Color color) {
        this.scale = scale;
        this.color = color;
        this.size = new Dimension((int)(scale * RECT_WIDTH), (int)(scale * RECT_HEIGHT));
    }

    public QuiltLayer(QuiltLayer other) {
        this.scale = other.scale; 
        this.color = new Color(other.color.getRGB());
        this.size = new Dimension(other.size);
    }

    public String toString() {
        return String.format("[%f, %d, %d, %d]", scale, color.getRed(), color.getGreen(), color.getBlue());
    }
}