package scenario;


import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.InvalidMoveException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:14
 */
public class TestUCMove {

    GameController controller;


    @Before
    public void fixture(){
        controller = new GameController();
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
