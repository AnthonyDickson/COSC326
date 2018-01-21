package quiltingbee;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Dimension;

/**
 * QuiltingBeeApp
 */
public class QuiltingBeeApp extends JPanel {   
    static final Dimension SCREEN_SIZE = new Dimension(800, 600); // Screen width and height in pixels.
  
    private static void display(QuiltLayer[] layers) {
        QuiltingBee quilt = new QuiltingBee(layers, SCREEN_SIZE);
        
        System.out.println(quilt);
        
        JFrame frame = new JFrame("Quilting Bee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(quilt);
        frame.pack();
        frame.setSize(SCREEN_SIZE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner line;
        Scanner in = new Scanner(System.in);
        ArrayList<QuiltLayer> layers = new ArrayList<QuiltLayer>();
        
        while (in.hasNextLine()) {
            line = new Scanner(in.nextLine());
            QuiltLayer layer = new QuiltLayer();

            layer.scale = line.nextFloat();
            layer.r = line.nextInt();
            layer.g = line.nextInt();
            layer.b = line.nextInt();
            layers.add(layer);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            display(layers.toArray(new QuiltLayer[0]));
          }
        });
        
        in.close();
    }
}