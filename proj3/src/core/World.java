package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;
    public int WIDTH;
    public int HEIGHT;
    /*fills the world starting from the start position to wherever it will end*/
    public World (Long seed) {
        randomGenerator = new Random(seed);
        WIDTH = randomGenerator.nextInt(10, 100);
        HEIGHT = randomGenerator.nextInt(10, 100);
    }
    private void fillSpace(int startX, int startY, int endX, int endY, TETile tileType) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                projWorld[x][y] = tileType;
            }
        }
    }

    public TETile[][] worldState () {
        return projWorld;
    }

    public TETile getTile ( int x, int y){
        return projWorld[x][y];
    }


}