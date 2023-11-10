package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class Room {
    private int roomHEIGHT;
    private int roomWIDTH;
    public int xLocation;
    public int yLocation;
    private World projWorld;

    /**
     *
     * @param projWorld
     * @param x
     * @param y
     */
    //need to change division in the future to set the maximum size of the room
    public Room (int width, int height, World projWorld, int x, int y) {
        roomWIDTH = width;
        roomHEIGHT = height;
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }

    public boolean placeable() {
        // Check if its within boundaries - minus on because of the walls
        if (xLocation + roomWIDTH >= projWorld.WIDTH - 1 || xLocation < 1)  {
            return false;
        }
        if (yLocation + roomHEIGHT >= projWorld.HEIGHT - 1 || yLocation < 1) {
            return false;
        }
        // Checks if you can create room
        for (int x = xLocation; x < xLocation + roomWIDTH; x++) {
            for (int y = yLocation; y < yLocation + roomHEIGHT; y++) {
                if (projWorld.getTile(x, y) != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        // Checks if there would be one block space free surrounding that room (No other rooms right next to it)
        for (int x = xLocation - 1; x <= xLocation + roomWIDTH + 1; x++) {
            for (int y = yLocation - 1; y <= yLocation + roomHEIGHT + 1; y++) {
                if (projWorld.getTile(x, y) == Tileset.FLOWER) {
                    return false;
                }

            }
        }
        return true;
    }

    public int roomWIDTH() {
        if (roomWIDTH == 1) {
            return roomWIDTH + 1; // This is so it's not a 1x1
        }
        return roomWIDTH;
    }
    public int roomHEIGHT() {
        if (roomHEIGHT == 1) {
            return roomHEIGHT + 1; // This is so it's not a 1x1
        }
        return roomHEIGHT;
    }



    // TODO: Create method that maybe returns an ArrayList containing the coordinates of the Room

}