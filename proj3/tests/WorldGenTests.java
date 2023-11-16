
import core.AutograderBuddy;
import core.World;
import core.blackWorld;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import spark.utils.IOUtils;
import tileengine.TERenderer;
import tileengine.TETile;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class WorldGenTests {

    public boolean isGameOver = true;
    @Test
    public void basicTest() throws Exception {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1111");
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);


    }

    @Test
    public void basicInteractivityTest() {
        // TODO: write a test that uses an input like "n123swasdwasd"
    }

    @Test
    public void basicSaveTest() {
        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
    }

    @Test
    public void blackTest() {
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1111");

        blackWorld secondWorld = new blackWorld();
        TETile[][] blackTiles = secondWorld.worldState();

        TERenderer blackTer = new TERenderer();
        blackTer.initialize(blackTiles.length, blackTiles[0].length);
        blackTer.renderFrame(blackTiles);


    }
}