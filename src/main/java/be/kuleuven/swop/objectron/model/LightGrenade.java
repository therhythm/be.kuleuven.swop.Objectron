package be.kuleuven.swop.objectron.model;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 27/02/13
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class LightGrenade implements  Item {
    private Square square;
     @Override
     public void use(Square square){
          if (canHaveAsSquare(square))
              this.square = square;
         else
              throw new IllegalArgumentException("invalid square or square already has a light grenade");
     }

    private boolean canHaveAsSquare(Square square){

        if (square == null)
            return false;

        for (Item item : square.getAvailableItems()){
            if(item instanceof LightGrenade)
                return false;
        }

        return true;
    }
}
