package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {

    private Square[][] squares;

    public Grid(int width, int height){
        this.squares = new Square[height][width];

        setupGrid();
    }

    public boolean isValidMove(Square s, Direction d){
        throw new RuntimeException("Unimplemented");
    }

    private void setupGrid(){
        //TODO grid initialization
    }

}
