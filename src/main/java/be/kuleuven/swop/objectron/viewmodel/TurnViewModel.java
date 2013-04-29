package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.domain.item.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/8/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TurnViewModel {
    private int remainingActions;
    private PlayerViewModel playerViewModel;
    private String selectedItem;

    public TurnViewModel(int remainingActions, PlayerViewModel playerViewModel, Item selectedItem) {//TODO item viewmodel instead of strings
        this.remainingActions = remainingActions;
        this.playerViewModel = playerViewModel;
        this.selectedItem = selectedItem == null ? "no item" : selectedItem.getName();
    }

    public int getRemainingActions() {
        return this.remainingActions;
    }

    public PlayerViewModel getPlayerViewModel() {
        return this.playerViewModel;
    }

    public String getCurrentItem() {
        return selectedItem;
    }
}
