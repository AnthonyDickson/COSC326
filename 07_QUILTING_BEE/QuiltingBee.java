package quiltingbee;

import java.awt.*;
import javax.swing.*;

/**
 * QuiltingBee - Represents a quilt design.
 */
public class QuiltingBee extends JPanel {   
  
    QuiltLayer[] layers;

    public QuiltingBee(QuiltLayer[] layers) {
        super();
        this.layers = layers;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      for (QuiltLayer layer : layers) {
        layer.paintComponent(g);
       }
    }

    public String toString() {
        String str = "";

        for (QuiltLayer layer : layers) {
            str += layer + "\n";
        }

        return str;
    }
}