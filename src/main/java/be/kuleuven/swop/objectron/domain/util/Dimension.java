package be.kuleuven.swop.objectron.domain.util;

/**
 * A class of Dimension involving a width and height.
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 3:34 AM
 */
public class Dimension {

    private final int width;
    private final int height;

    /**
     * Initialize this Dimension with a given width and height.
     * @param width
     *        The width to initialize this Dimension with.
     * @param height
     *        The height to initialize this Dimension with.
     */
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int area() {
        return this.width * this.height;
    }

}
