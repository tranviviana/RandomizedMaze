package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class Room implements worldComponents{
    private int HEIGHT;
    private int WIDTH;
    private int xLocation;
    private int yLocation;
    private World projWorld;

    /**
     *
     * @param worldSeed
     * @param projWorld
     * @param x
     * @param y
     */
    public Room (Random worldSeed, World projWorld, int x, int y) {
        WIDTH = worldSeed.nextInt();
        HEIGHT = worldSeed.nextInt();
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }
    @Override
    public boolean placeable() {
        // Check if its within boundaries - minus on because of the walls
        if (xLocation + WIDTH > projWorld.worldWidth() - 1 || xLocation < 1)  {
            return false;
        }
        if (yLocation + HEIGHT > projWorld.worldHEIGHT() - 1 || yLocation < 1) {
            return false;
        }
        // Check if can create room
        for (int x = xLocation; x <= WIDTH; x++) {
            for (int y = yLocation; y <= HEIGHT; y++) {
                if (projWorld.getTile(x, y) != Tileset.NOTHING) {
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
