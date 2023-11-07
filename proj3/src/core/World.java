package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {

    private Random randomSeed;
    private int HEIGHT;
    private int WIDTH;
    private TETile[][] projWorld;


    /**
     * Fills the world
     *
     * @param seed
     */
    public World (long seed) {
        //TERenderer ter = new TERenderer();
        randomSeed = new Random(seed);
        HEIGHT = randomSeed.nextInt(0, 1000);
        WIDTH = randomSeed.nextInt(0, 1000);
        //ter.initialize(WIDTH, HEIGHT);
        projWorld = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                projWorld[x][y] = Tileset.NOTHING;
            }
        }
    }
    public TETile[][] worldState() {
        return projWorld;
    }
    public int worldWidth() {
        return WIDTH;
    }
    public int worldHEIGHT() {
        return HEIGHT;
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

