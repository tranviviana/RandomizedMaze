package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;
    public static final int minWorldSize = 30;
    public static final int maxWorldSize = 70;
    public static final int maxRoomSize = minWorldSize / 2;
    public static final int minRoomSize = 2;
    public int WIDTH;
    public int HEIGHT;
    public int numberRooms;
    /*fills the world starting from the start position to wherever it will end*/
    public World (Long seed) {
        randomGenerator = new Random(seed);
        WIDTH = randomGenerator.nextInt(minWorldSize, maxWorldSize);
        HEIGHT = randomGenerator.nextInt(minWorldSize, maxWorldSize);
        projWorld = new TETile[WIDTH][HEIGHT];
        //might need to change math class
        numberRooms = randomGenerator.nextInt(3, Math.min(WIDTH,HEIGHT));
        fillSpace(0,0, WIDTH, HEIGHT, Tileset.NOTHING);
        generateRooms();
    }
    //creates a random room of different sizes and places them on grid if possible
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
                    fillSpace(xLocation, yLocation, xLocation + roomWIDTH, yLocation + roomHEIGHT, Tileset.FLOWER);
                }
            }
        }
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