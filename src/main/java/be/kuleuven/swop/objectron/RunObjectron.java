package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.gui.GameView;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Startup class for objectron
 *
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 20:57
 */
public class RunObjectron {
    @Parameter(names = "-htiles", description = "number of horizontal tiles")
    private int horizontalTiles = 10;

    @Parameter(names = "-vtiles", description = "number of vertical tiles")
    private int verticalTiles = 10;

    @Parameter(names = "-p1", description = "player 1 name")
    private String player1Name = "Player 1";

    @Parameter(names = "-p2", description = "player 2 name")
    private String player2Name = "Player 2";

    public void parseArgs(String[] args) {
        new JCommander(this, args);
    }

    public void run() {
        GameState state = new GameState(player1Name, player2Name, horizontalTiles, verticalTiles);
        GameController controller = new GameController(state);
        GameView view = new GameView(controller, horizontalTiles, verticalTiles, state.getCurrentPlayer().getPlayerViewModel());
        view.run();
        controller.addGameEventListener(view);
    }

    public static void main(String[] args) {
        RunObjectron runObjectron = new RunObjectron();
        runObjectron.parseArgs(args);
        runObjectron.run();
    }
}
