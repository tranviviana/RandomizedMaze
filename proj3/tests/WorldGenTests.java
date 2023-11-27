
import core.AutograderBuddy;
import core.World;

import edu.princeton.cs.algs4.StdAudio;
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
    public void soundTest() {
        StdAudio.play("core/sounds/coins2.midi");

    }

}