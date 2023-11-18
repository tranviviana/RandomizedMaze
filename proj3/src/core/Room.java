package core;


import java.util.*;

public class Room {
    private int roomHEIGHT;
    private int roomWIDTH;
    private int xLocation;
    private int yLocation;
    private World projWorld;


    /**
     *
     * @param projWorld
     * @param x
     * @param y
     */
    //need to change division in the future to set the maximum size of the room
    public Room(int width, int height, World projWorld, int x, int y) {
        roomWIDTH = width;
        roomHEIGHT = height;
        xLocation = x;
        yLocation = y;
        this.projWorld = projWorld;


    }

    public boolean placeable() {
        // Check if its within boundaries - minus on because of the walls
        if (xLocation - 1 <= 0 || yLocation - 1 <= 0) {
            return false;
        }
        if (xLocation + roomWIDTH >= World.WIDTH - 1 || xLocation < 1)  {
            return false;
        }
        if (yLocation + roomHEIGHT >= World.HEIGHT - 1 || yLocation < 1) {
            return false;
        }
        // Checks if you can create room
        for (int x = xLocation; x < xLocation + roomWIDTH; x++) {
            for (int y = yLocation; y < yLocation + roomHEIGHT; y++) {
                if (projWorld.getTile(x, y) != World.NOTHINGREP) {
                    return false;
                }
            }
        }
        // Checks if there would be one block space free surrounding that room (No other rooms right next to it)
        for (int x = xLocation - 1; x <= xLocation + roomWIDTH + 1; x++) {
            for (int y = yLocation - 1; y <= yLocation + roomHEIGHT + 1; y++) {
                if (projWorld.getTile(x, y) == World.FLOORREP) {
                    return false;
                }

            }
        }
        return true;
    }
    // returns the middle of the room in list format {0, 0}
    public List<Integer> roomMiddle() {
        int xMiddle = (xLocation + (roomWIDTH / 2));
        int yMiddle = (yLocation + (roomHEIGHT / 2));
        List<Integer> xyMiddle = new ArrayList<>();
        xyMiddle.add(xMiddle);
        xyMiddle.add(yMiddle);
        return xyMiddle;
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

    public List<Integer> ghostHelper() {
        List<Integer> list = new ArrayList<>();
        if (((roomWIDTH / 2) - 1) == 0) {
            list.add(1);
        } else {
            list.add((roomWIDTH / 2) - 1);
        }
        if (((roomHEIGHT / 2) - 1) == 0) {
            list.add(1);
        } else {
            list.add((roomHEIGHT / 2) - 1);
        }
        return list;
    }
}
