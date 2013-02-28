package scenario;


import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:14
 */
public class UC_Move {

    GameController controller;


    @Before
    public void fixture(){
        controller = new GameController(new GameState("p1", "p2", 10, 10));
    }

    @Test
    public void test_main_flow(){

    }

    @Test(expected = InvalidMoveException.class)
    public void test_wrong_positioning(){

    }

    @Test
    public void test_endgame_move(){

    }
}
