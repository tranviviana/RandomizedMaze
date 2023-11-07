package core;

import tileengine.TERenderer;
import tileengine.Tileset;
import tileengine.TETile;

public class World {

    private static final int xMin = 20;
    private static final int xMax = 1000;
    private static final int yMin = 20;
    private static final int yMax = 1000;

    /**
     * Fills the world
     * @param seed 
     */
    public World (long seed) {

    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(xMin, xMax);

        ter.renderFrame();
    }

}
