package quiltingbee;

import java.awt.*;
import javax.swing.*;

/**
 * QuiltingBee - Represents a quilt design.
 */
public class QuiltingBee extends JPanel {
    static final int margin = 20; // Margin size in pixels.

    QuiltLayer[] layers;

    public QuiltingBee(QuiltLayer[] layers, Dimension screenSize) {
        super();
        this.layers = layers;

        init(screenSize);
    }

    /**
     * Sets the position and sizes for the squares in each layer and
     * adds 3 more squares for each layer after the first.
     * 
     * @param screenSize The size of the screen we should fit the shapes to.
     */
    private void init(Dimension screenSize) {
        QuiltLayer[] newLayers = new QuiltLayer[4 * layers.length - 3];
        QuiltLayer prev = null;
        int i = 0;

        for (QuiltLayer layer : this.layers) {
            if (prev == null) {
                // Center the first layer on the screen.
                layer.pos.x = (int)(0.5 * (screenSize.width - layer.size.width));
                layer.pos.y = (int)(0.5 * (screenSize.height - layer.size.height));
                newLayers[i++] = layer; 
            } else {
                // Scale subsequent layers based on the previous layer.
                Dimension scaled = new Dimension();
                scaled.width = (int)(layer.scale * prev.size.width);
                scaled.height = (int)(layer.scale * prev.size.height);
                layer.size = scaled;
                // Calculate the positions for the corners of the previous layer.
                // Top Left
                Point tl = new Point(prev.pos.x - (int)(0.5 * layer.size.width),
                                     prev.pos.y - (int)(0.5 * layer.size.height));
                // Top Right
                Point tr = new Point(tl);
                tr.x += prev.size.width;
                // Bottom Left
                Point bl = new Point(tl);
                bl.y += prev.size.height;
                // Bottom Right
                Point br = new Point(tl);
                br.x += prev.size.width;
                br.y += prev.size.height;
                
                // Add squares at each corner of the previous layer.
                newLayers[i] = new QuiltLayer(layer);
                newLayers[i].pos = tl;
                i++;
                newLayers[i] = new QuiltLayer(layer);
                newLayers[i].pos = tr;
                i++;
                newLayers[i] = new QuiltLayer(layer);
                newLayers[i].pos = bl;
                i++;
                newLayers[i] = new QuiltLayer(layer);
                newLayers[i].pos = br;
                i++;
            }

            prev = layer;            
        }

        this.layers = newLayers;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      for (QuiltLayer layer : layers) {
        layer.paintComponent(g);
       }
    }

    // @Override
    // public Dimension getPreferredSize() {
    //     Dimension size = new Dimension();

    //     for (QuiltLayer layer : layers) {
    //         Dimension layerSize =  layer.getPreferredSize();
    //         size.height += layerSize.height;
    //         size.width += layerSize.width;
    //     }

    //     return size;
    // }

    public String toString() {
        String str = "";

        for (QuiltLayer layer : layers) {
            str += layer + "\n";
        }

        return str;
    }
}