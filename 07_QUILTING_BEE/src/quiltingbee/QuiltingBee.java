package quiltingbee;

import java.util.PriorityQueue;
import java.util.Comparator;
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
    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 800;
    static final Point CENTER = new Point(IMAGE_WIDTH / 2, IMAGE_HEIGHT / 2);

    QuiltLayer[] layers;
    BufferedImage output;
    Graphics2D g2;
    PriorityQueue<DrawJob> jobQueue;

    public QuiltingBee(QuiltLayer[] layers) {
        this.layers = layers;
        this.output = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, 
                                        BufferedImage.TYPE_INT_ARGB);
        this.g2 = this.output.createGraphics();
        this.jobQueue = new PriorityQueue<>(21, new DrawJobComparator());

        process();
    }
    
    /** Process the quilt design and prepare it for writing to file. */
    void process() {
        fitToSize();
        enqueueJobs(0, CENTER);
        draw();
    }

    /** 
     * Adjust the layer sizes such that the entire quilt design will fill the 
     * image. 
     */
    void fitToSize() {
        int designWidth = 0;
        int designHeight = 0;

        for (QuiltLayer layer : layers) {
            designWidth += layer.size.width;
            designHeight += layer.size.height;
        }

        float widthRatio = (float)IMAGE_WIDTH / designWidth;
        float heightRatio = (float)IMAGE_HEIGHT / designHeight;

        for (QuiltLayer layer : layers) {
            layer.size.width = (int)(widthRatio * layer.size.width);
            layer.size.height = (int)(heightRatio * layer.size.height);
        }
    }

    /** Enqueue jobs to draw each square in the quilt design. */
    void enqueueJobs(int i, Point position) {
        if (i == layers.length) return;

        DrawJob job = new DrawJob(layers[i], position, i);        
        jobQueue.add(job);

        for (Corners corner : Corners.values()) {
            enqueueJobs(i + 1, job.getPosOf(corner));
        }
    }
            
    /**
     * Draw the quilt design.
     */
    void draw() {
        while (!jobQueue.isEmpty()) {
           jobQueue.poll().draw(g2);
        }
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
    class DrawJob {     
        QuiltLayer layer;   
        Point position;
        int priority;
        
        public DrawJob(QuiltLayer layer, Point position, int priority) {
            // Adjust position to take layer dimensions into account.
            position.x -= (int)(0.5 * layer.size.width);
            position.y -= (int)(0.5 * layer.size.height);
            
            this.layer = layer;
            this.position = position;
            this.priority = priority;
        }
        
        /**
         * Get the position of a given corner.
         * 
         * @param corner The corner whose position will be returned.
         * @return The position of the corner.
         */
        Point getPosOf(Corners corner) {
            Dimension size = layer.size;

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
        
        void draw(Graphics2D g) {
            g.setColor(layer.color);
            g.fillRect(position.x, position.y, layer.size.width, layer.size.height);
        }

        @Override
        public String toString() {
            return super.toString() + " " + position;
        }
    }

    class DrawJobComparator implements Comparator<DrawJob> {
        @Override
        public int compare(DrawJob x, DrawJob y) {
            return x.priority - y.priority;
        }

    }
}