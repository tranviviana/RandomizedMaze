
import core.AutograderBuddy;
import core.World;

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


        public void basicTest () {
            TETile[][] tiles = AutograderBuddy.getWorldFromInput("n123412s");
            TERenderer ter = new TERenderer();
            ter.initialize(tiles.length, tiles[0].length);
            ter.renderFrame(tiles);
            StdDraw.pause(1000);
            // put different seeds here to test different worlds
            // pause for 5 seconds so you can see the output

        }

        @Test
        public void basicInteractivityTest () {
            // TODO: write a test that uses an input like "n123swasdwasd"
            TETile[][] tiles = AutograderBuddy.getWorldFromInput("n123412swwwwwwwww");
            TERenderer ter = new TERenderer();
            ter.initialize(tiles.length, tiles[0].length);
            ter.renderFrame(tiles);
            StdDraw.pause(800);

        }

        @Test
        public void basicSaveTest () {
            // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
            //TETile[][] tiles = AutograderBuddy.getWorldFromInput("N999SDDDWWWDDD");
//
//        TETile[][] tiles  = AutograderBuddy.getWorldFromInput("N999SDDD:Q");
//        tiles = AutograderBuddy.getWorldFromInput("LWWWDDD");

//        TETile[][] tiles  = AutograderBuddy.getWorldFromInput("N999SDDD:Q");
//        tiles = AutograderBuddy.getWorldFromInput("LWWW:Q");
//        tiles = AutograderBuddy.getWorldFromInput("LDDD:Q");
//
            TETile[][] tiles = AutograderBuddy.getWorldFromInput("N999SDDD:Q");
            tiles = AutograderBuddy.getWorldFromInput("L:Q");
            tiles = AutograderBuddy.getWorldFromInput("LWWWDDD");

            TERenderer ter = new TERenderer();
            ter.initialize(tiles.length, tiles[0].length);
            ter.renderFrame(tiles);
            StdDraw.pause(10000000);
        }
    }
}