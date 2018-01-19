package quiltingbee;

import java.util.ArrayDeque;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Represent a quilt design that consists of different layers.
 * 
 * @author Anthony Dickson
 */
class QuiltingBee {  
    static enum Corners { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT };
    static final int RECT_WIDTH = 100;
    static final int RECT_HEIGHT = 100;
    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 800;
    static final Point CENTER = new Point(IMAGE_WIDTH / 2, IMAGE_HEIGHT / 2);

    QuiltLayer[] layers;
    BufferedImage output;
    Graphics2D g2;
    ArrayDeque<DrawJob> jobQueue;

    public QuiltingBee(QuiltLayer[] layers) {
        this.layers = layers;
        this.output = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, 
                                        BufferedImage.TYPE_INT_ARGB);
        this.g2 = this.output.createGraphics();
        this.jobQueue = new ArrayDeque<>();

        process();
    }
    
    /** Process the quilt design and prepare it for writing to file. */
    void process() {
        enqueueJobs(0, CENTER);
        draw();
    }

    /** Enqueue jobs to draw each square in the quilt design. */
    void enqueueJobs(int i, Point position) {
        if (i == layers.length) return;
        
        DrawJob job = new DrawJob(layers[i], position, g2);        
        jobQueue.add(job);

        for (Corners corner : Corners.values()) {
            enqueueJob(i + 1, job.getPosOf(corner));
        }
    }
            
    /**
     * Draw the quilt design.
     */
    void draw() {
        while (!jobQueue.isEmpty()) {
           jobQueue.poll().draw();
        }
    }

    /**
     * Write the quilt design to file using the default settings.
     */
    void write() {
        write("output");
    }

    /**
     * Write the quilt design to file using the default file extension.
     * 
     * @param name The name of the file to write to.
     */
    void write(String name) {
        write(name, "png");
    }

    /**
     * Write the quilt design to file.
     * 
     * @param name The name of the file to write to.
     * @param extension The file extension. 
     * The type of image file the result should be saved as.
     */
    void write(String name, String extension) {
        try {
            String filename = name + "." + extension;
            File outputfile = new File(filename);
            ImageIO.write(output, extension, outputfile);
            System.out.println("Quilt design has been saved in this file: " + filename);
        } catch (IOException e) {
            System.err.println("File could not be saved.");
        }
    }

    /**
     * Return string representation of the quilt design.
     * 
     * @return The string representation of the quilt design.
     */
    @Override
    public String toString() {
        String str = "";

        for (QuiltLayer layer : layers) {
            str += layer + "\n";
        }

        return str;
    }

    /** A job of a drawing square in a quilt layer. */
    class DrawJob extends QuiltLayer {        
        Point position;
        Graphics2D g;
        
        public DrawJob(QuiltLayer layer, Point position, Graphics2D g) {
            super(layer);
            
            // Adjust position to take layer dimensions into account.
            position.x -= (int)(0.5 * layer.size.width);
            position.y -= (int)(0.5 * layer.size.height);
            
            this.position = position;
            this.g = g;
        }
        
        /**
         * Get the position of a given corner.
         * 
         * @param corner The corner whose position will be returned.
         * @return The position of the corner.
         */
        Point getPosOf(Corners corner) {
            if (corner == Corners.TOP_LEFT) {
                return new Point(position);
            }
            if (corner == Corners.TOP_RIGHT) {
                return new Point(position.x + size.width, position.y);
            }
            if (corner == Corners.BOTTOM_LEFT) {
                return new Point(position.x, position.y + size.height);
            }
            if (corner == Corners.BOTTOM_RIGHT) {
                return new Point(position.x + size.width, position.y + size.height);
            }
            
            return null;
        }
        
        void draw() {
            g.setColor(color);
            g.fillRect(position.x, position.y, size.width, size.height);
        }

        @Override
        public String toString() {
            return super.toString() + " " + position;
        }
    }
}