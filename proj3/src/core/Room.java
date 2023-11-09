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
    //need to change division in the future to set the maximum size of the room
    public Room (Random worldSeed, World projWorld, int x, int y) {
        WIDTH = worldSeed.nextInt(0,projWorld.WIDTH);
        HEIGHT = worldSeed.nextInt(0,projWorld.HEIGHT);
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }
    @Override
    public boolean placeable() {
        // Check if its within boundaries - minus on because of the walls
        if (xLocation + WIDTH > projWorld.WIDTH - 1 || xLocation < 1)  {
            return false;
        }
        if (yLocation + HEIGHT > projWorld.HEIGHT - 1 || yLocation < 1) {
            return false;
        }
        // Checks if you can create room
        for (int x = xLocation; x <= WIDTH; x++) {
            for (int y = yLocation; y <= HEIGHT; y++) {
                if (projWorld.getTile(x, y) != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        // Checks if there would be one block space free surrounding that room (No other rooms right next to it)
        for (int x = xLocation - 1; x <= WIDTH + 1; x++) {
            for (int y = yLocation - 1; y <= HEIGHT + 1; y++) {
                if (projWorld.getTile(x, y) == Tileset.FLOWER) {
                    return false;
                }

            }
        }
        return true;
    }

    public int roomWIDTH() {
        if (WIDTH == 1) {
            return WIDTH + 1; // This is so it's not a 1x1
        }
        return WIDTH;
    }
    public int roomHEIGHT() {
        if (HEIGHT == 1) {
            return HEIGHT + 1; // This is so it's not a 1x1
        }
        return HEIGHT;
    }



    // TODO: Create method that maybe returns an ArrayList containing the coordinates of the Room

}
