package be.kuleuven.swop.objectron.domain.game;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.item.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/8/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Turn {
    private Player currentPlayer;
    private Item currentItem;
    private int actionsRemaining;

    public Turn(Player player){
        this.currentPlayer = player;
        this.actionsRemaining = Settings.PLAYER_ACTIONS_EACH_TURN; //TODO - player.getRemainingPenalties();
    }

    public int getActionsRemaining(){
        return actionsRemaining;
    }

    public void setCurrentItem(Item item){
        this.currentItem = item;
    }

    public Item getCurrentItem(){
        return currentItem;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void reduceRemainingActions(int amount){
        if(actionsRemaining > amount){
            actionsRemaining -= amount;
        }else{
            //TODO currentPlayer.setRemainingPenalties(amount - actionsRemaining);
            actionsRemaining = 0;
        }
    }

}
