import core.AutograderBuddy;
import core.World;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

public class WorldGenTests {

    public boolean isGameOver = true;
    @Test
    public void basicTest() throws Exception {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n123412swwwwwwwww");
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(500000); // pause for 5 seconds so you can see the output
    }

    @Test
    public void basicInteractivityTest() {
        // TODO: write a test that uses an input like "n123swasdwasd"
    }

    @Test
    public void basicSaveTest() {
        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
    }
}