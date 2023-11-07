package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {

    private Random randomSeed;
    private static int HEIGHT;
    private static int WIDTH;


    /**
     * Fills the world
     *
     * @param seed
     */
    public World(long seed) {
        TERenderer ter = new TERenderer();
        randomSeed = new Random(seed);
        HEIGHT = randomSeed.nextInt();
        WIDTH = randomSeed.nextInt();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] projWorld = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x <= HEIGHT; x++) {
            for (int y = 0; y <= WIDTH; y++) {
                projWorld[x][y] = Tileset.NOTHING;
            }

        }


    }
}


//    public static void main(String[] args) {
//        TERenderer ter = new TERenderer();
//        ter.initialize(HEIGHT, WIDTH);
//
//        World world = new World(345);
//
//        ter.renderFrame(world);
//    }
//
//}
