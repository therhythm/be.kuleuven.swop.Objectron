package be.kuleuven.swop.objectron.domain.util;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 3:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Dimension {

    private final int width;
    private final int height;

    public Dimension(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int area(){
        return this.width * this.height;
    }

}
