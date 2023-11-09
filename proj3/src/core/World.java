package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    private static final int maxHEIGHT = 60;
    private static final int maxWIDTH = 100;
    private Random randomSeed;
    public int HEIGHT;
    public int WIDTH;
    private TETile[][] projWorld;
    private int quantityofRooms;


    /**
     * Fills the world
     *
     * @param seed
     */
    /*initializes a world of random height and width and fills it to be  and random quantity of rooms*/
    public World (long seed) {
        randomSeed = new Random(seed);
//        HEIGHT = 40;
//        WIDTH = 40;
//        quantityofRooms = 1;
        HEIGHT = Math.abs(randomSeed.nextInt(10, maxHEIGHT));
        WIDTH = Math.abs(randomSeed.nextInt(10, maxWIDTH));
        projWorld = new TETile[WIDTH][HEIGHT];
        quantityofRooms = randomSeed.nextInt(60);
        fillSpace(0,0, WIDTH, HEIGHT, Tileset.NOTHING);
        fillRooms();
    }

    /*fills all the possible rooms */
    /*needs to stop creating rooms if grid is too filled NEEEED TO FINISH */
    private void fillRooms() {
        for (int i = 0; i < quantityofRooms; i++) {
//            int xLocation = 10;
//            int yLocation = 10;
            int xLocation = randomSeed.nextInt(maxWIDTH);
            int yLocation = randomSeed.nextInt(maxHEIGHT);
            Room currRoom = new Room(randomSeed, this, xLocation, yLocation);
            if (currRoom.placeable()) {
                fillSpace(xLocation, yLocation, xLocation + currRoom.roomWIDTH(), yLocation + currRoom.roomHEIGHT(), Tileset.FLOWER);
            }

        }
    }
    /*fills the world starting from the start position to wherever it will end*/
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