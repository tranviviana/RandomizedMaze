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
    public static List<List<Integer>> listofMiddle;

    public int WIDTH;
    public int HEIGHT;
    public int numberRooms;
    //private PriorityQueue<List<Integer>> roomLocations;

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
        listofMiddle = new ArrayList<>();
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
                    listofMiddle.add(currentRoom.roomMiddle());
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

    public void fillHallway(int room1x, int room1y, int room2x, int room2y) {
        if (room2x > room1x && room2y < room1y) { // Room2x > Room1x, but room2y < room1y
            int xDifference = room2x - room1x;
            int yDifference = room1y - room2y;
            for (int startX = room1x; startX < room2x; startX += randomGenerator.nextInt(0, xDifference)) {
                fillRooms(room1x, room1y, startX, room1y, Tileset.FLOWER);
                room1x = startX;
                for (int startY = room1y; startY > room2y; startY -= randomGenerator.nextInt(0, yDifference)) {
                    for (int y = room1y; y > startY; y--) {
                        projWorld[startX][y] = Tileset.FLOWER;
                    }
                    room1y = startY;
                }

            }
        } else if (room2x < room1x && room2y > room1y) { // when Room2x < Room1x, but room2y > room1y
            int xDifference = room1x - room2x;
            int yDifference = room2y - room1y;
            for (int startX = room1x; startX > room2x; startX -= randomGenerator.nextInt(0, xDifference)) {
                for (int x = room1x; x > startX; x--) {
                    projWorld[x][room1y] = Tileset.FLOWER;
                }
                room1x = startX;
                for (int startY = room1y; startY < room2y; startY += randomGenerator.nextInt(0, yDifference)) {
                    fillRooms(room1x, room1y, room1x, startY, Tileset.FLOWER);
                    room2y = startY;
                }
            }
        } else { // when room2x and room2y is bigger than room1x and room1y
            int xDifference = room2x - room1x;
            int yDifference = room2y - room1y;
            for (int startX = room1x; startX < room2x; startX += randomGenerator.nextInt(0, xDifference)) {
                fillRooms(room1x, room1y, startX, room1y, Tileset.FLOWER);
                room1x = startX;
                for (int startY = room1y; startY < room2y; startY += randomGenerator.nextInt(0, yDifference)) {
                    fillRooms(room1x, room1y, room1x, startY, Tileset.FLOWER);
                    room2y = startY;
                }
            }
        }
    }

}
