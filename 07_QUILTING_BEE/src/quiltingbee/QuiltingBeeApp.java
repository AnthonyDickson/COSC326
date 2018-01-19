package quiltingbee;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * QuiltingBeeApp
 * Create an image of a quilt design based on the given input and save that
 * image. 
 * 
 * @author Anthony Dickson
 */
public class QuiltingBeeApp {   

    public static void main(String[] args) {
        Scanner line;
        Scanner in = new Scanner(System.in);
        ArrayList<QuiltLayer> layers = new ArrayList<QuiltLayer>();
        
        while (in.hasNextLine()) {
            line = new Scanner(in.nextLine());
            float scale = line.nextFloat();
            Color color = new Color(line.nextInt(), 
                                    line.nextInt(), 
                                    line.nextInt());
            layers.add(new QuiltLayer(scale, color));
        }

        QuiltingBee quilt = new QuiltingBee(layers.toArray(new QuiltLayer[layers.size()]));
        quilt.write();

        in.close();
    }
}