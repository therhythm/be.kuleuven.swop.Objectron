package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 20/05/13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class GridViewModel {
    private List<SquareViewModel> squareViewModels;

    public GridViewModel(List<SquareViewModel> squareViewModels){
        this.squareViewModels = squareViewModels;
    }

    public List<SquareViewModel> getSquareViewModels() {
        return squareViewModels;
    }
}
