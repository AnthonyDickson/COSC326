package quiltingbee;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * QuiltingBeeApp
 */
public class QuiltingBeeApp extends JPanel {   
  
    private static void display(QuiltLayer[] layers) {
        JFrame frame = new JFrame("Quilting Bee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        QuiltingBee quilt = new QuiltingBee(layers);        
        frame.getContentPane().add(quilt);
        frame.pack();
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