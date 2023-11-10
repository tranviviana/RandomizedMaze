package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;

    public static final int maxRoomSize = 50 / 4;
    public static final int minRoomSize = 2;
    public static final TETile floorRep = Tileset.FLOWER;
    public static final TETile wallRep = Tileset.WALL;
    public static final TETile nothingRep = Tileset.NOTHING;

    public int WIDTH;
    public int HEIGHT;
    public int numberRooms;
    private PriorityQueue<List<Integer>> roomLocations;

    /*fills the world starting from the start position to wherever it will end
     @param Long seed to generate the same world when the same seed is passed through */
    public World (Long seed) {
        randomGenerator = new Random(seed);
        WIDTH = 90;
        HEIGHT = 50;
        projWorld = new TETile[WIDTH][HEIGHT];
        //might need to change math class
        numberRooms = randomGenerator.nextInt(3, WIDTH);
        fillRooms(0,0, WIDTH, HEIGHT, nothingRep);
        generateRooms();
        //roomLocations = new PriorityQueue<>();
    }


    /*creates a random room of different sizes, generating random locations, and places them on grid if possible
    * stores the location of the rooms*/
    private void generateRooms() {
        int placed = 0;
        while (placed < 3) {
            for (int room = 0; room < numberRooms; room++) {
                int roomWIDTH = randomGenerator.nextInt(minRoomSize, maxRoomSize);
                int roomHEIGHT = randomGenerator.nextInt(minRoomSize, maxRoomSize);
                int xLocation = randomGenerator.nextInt(WIDTH);
                int yLocation = randomGenerator.nextInt(HEIGHT);
                Room currentRoom = new Room(roomWIDTH, roomHEIGHT, this, xLocation, yLocation);
                if (currentRoom.placeable()) {
                    placed++;
                    fillRooms(xLocation, yLocation, xLocation + roomWIDTH, yLocation + roomHEIGHT, floorRep);
                    //roomLocations.add(currentRoom.roomMiddle());
                }
            }
        }
    }
    //fills the tiles on the TileSet
    private void fillRooms(int startX, int startY, int endX, int endY, TETile tileType) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                projWorld[x][y] = tileType;
            }
        }
    }
    //recursively calls itself till all of the grid is filled
    private void fillWalls(int xStart, int yStart) {
        fillYDirection(xStart, yStart, -1);
        fillYDirection(xStart, yStart, +1);

    }
    private void fillYDirection (int xStart, int yStart, int directionY) {
        int fillY = 0;
        int workingY = yStart;
        while (getTile(xStart -1, workingY) == floorRep &&  getTile(xStart, workingY + directionY) == nothingRep) {
            //working upwards or downwards
            fillY = fillY + directionY;
        }
        fillRooms(xStart, yStart, xStart, yStart + fillY, wallRep);
        if (getTile(xStart -1, workingY + directionY) == nothingRep) {
            //fills up to the top edge of room
            fillXDirection(xStart,yStart + fillY + directionY);
        } else if (getTile(xStart, workingY + directionY) != nothingRep) {
            //responds to a hallway leaving from it
            fillXDirection(xStart, yStart + fillY);
        }
    }
    private void fillXDirection (int xStart, int yStart) {

    }



    // returns what the world looks like (for autograder)
    public TETile[][] worldState () {
        return projWorld;
    }
    // returns the tileType of a certain x and y location
    public TETile getTile ( int x, int y){
        return projWorld[x][y];
    }


}