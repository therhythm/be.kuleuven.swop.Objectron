package be.kuleuven.swop.objectron.viewmodel;

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

    public TurnViewModel(int remainingActions, PlayerViewModel playerViewModel){
        this.remainingActions = remainingActions;
        this.playerViewModel = playerViewModel;
    }

    public int getRemainingActions(){
        return this.remainingActions;
    }

    public PlayerViewModel getPlayerViewModel(){
        return this.playerViewModel;
    }
}
