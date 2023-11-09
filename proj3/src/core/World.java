package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;
    public int WIDTH;
    public int HEIGHT;
    public int numberRooms;
    /*fills the world starting from the start position to wherever it will end*/
    public World (Long seed) {
        randomGenerator = new Random(seed);
        WIDTH = randomGenerator.nextInt(10, 100);
        HEIGHT = randomGenerator.nextInt(10, 100);
        projWorld = new TETile[WIDTH][HEIGHT];
        numberRooms = randomGenerator.nextInt(3, Math.min(WIDTH,HEIGHT));
        fillSpace(0,0, WIDTH, HEIGHT, Tileset.NOTHING);
        generateRooms();
    }
    private void generateRooms() {
        for (int room = 0; room < numberRooms; room++) {
            int roomWIDTH = randomGenerator.nextInt(2, WIDTH/6);
            int roomHEIGHT = randomGenerator.nextInt(2, HEIGHT/6);
            int xLocation = randomGenerator.nextInt(WIDTH);
            int yLocation = randomGenerator.nextInt(HEIGHT);
            Room currentRoom = new Room(roomWIDTH, roomHEIGHT, this, xLocation, yLocation);
            if (currentRoom.placeable()) {
                fillSpace(xLocation, yLocation, xLocation + roomWIDTH, yLocation + roomHEIGHT, Tileset.FLOWER);
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