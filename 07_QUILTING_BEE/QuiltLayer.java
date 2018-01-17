package quiltingbee;

/**  A quilt layer. */
public class QuiltLayer {
    public float scale;
    public int r, g, b;

    public String toString() {
        return String.format("[%f, %d, %d, %d]", scale, r, g, b);
    }
}