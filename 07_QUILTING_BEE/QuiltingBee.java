package quiltingbee;

import java.util.Arrays;

/**
 * QuiltingBee - Represents a quilt design.
 */
public class QuiltingBee {
    QuiltLayer[] layers;

    public QuiltingBee(QuiltLayer[] layers) {
        this.layers = layers;
    }

    public QuiltLayer[] getLayers() {
        return Arrays.copyOf(layers, layers.length);
    }

    public String toString() {
        String str = "";

        for (QuiltLayer layer : layers) {
            str += layer + "\n";
        }

        return str;
    }
}