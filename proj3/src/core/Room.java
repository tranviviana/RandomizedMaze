package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class Room {
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
    public Room (int width, int height, World projWorld, int x, int y) {
        WIDTH = width;
        HEIGHT = height;
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }

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
        for (int x = xLocation - 2; x <= WIDTH + 2; x++) {
            for (int y = yLocation - 2; y <= HEIGHT + 2; y++) {
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