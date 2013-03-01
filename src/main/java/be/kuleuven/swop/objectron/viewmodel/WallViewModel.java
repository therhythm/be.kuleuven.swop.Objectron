package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.model.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 16:13
 */
public class WallViewModel {
    List<SquareViewModel> squares = new ArrayList<SquareViewModel>();

    public WallViewModel(List<Square> squares) {
        for(Square s : squares){
            this.squares.add(s.getSquareViewModel());
        }
    }

    public List<SquareViewModel> getSquares(){
        return Collections.unmodifiableList(squares);
    }
}
