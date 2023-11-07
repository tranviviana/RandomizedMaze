package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class Room implements worldComponents{
    private int HEIGHT;
    private int WIDTH;
    private int xLocation;
    private int yLocation;
    private TETile[][] projWorld;

    /**
     *
     * @param worldSeed
     * @param projWorld
     * @param x
     * @param y
     */
    public Room (Random worldSeed, TETile[][] projWorld, int x, int y) {
        HEIGHT = worldSeed.nextInt();
        WIDTH = worldSeed.nextInt();
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }
    @Override
    public boolean placeable() {
        if (xLocation + HEIGHT > )
        for (int x = xLocation; x <= WIDTH; x++) {
            for (int y = yLocation; y <= HEIGHT; y++) {
                if (projWorld[x][y] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Integer> dimensions() {
        return List.of(WIDTH,HEIGHT);
    }
}
