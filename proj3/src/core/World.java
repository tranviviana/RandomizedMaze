package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    private static final int maxHEIGHT = 60;
    private static final int maxWIDTH = 100;
    private Random randomSeed;
    private int HEIGHT;
    private int WIDTH;
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
        HEIGHT = randomSeed.nextInt(0, maxHEIGHT);
        WIDTH = randomSeed.nextInt(0, maxWIDTH);
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
        int currentX = startX;
        int currentY = startY;
        while (currentY < endY) {
            while(currentX < endX){
                projWorld[currentX][currentY] = tileType;
                currentX += 1;
            }
            currentX = startX;
            currentY += 1;
        }

    }
        public TETile[][] worldState () {
            return projWorld;
        }
        public int worldWidth () {
            return WIDTH;
        }
        public int worldHEIGHT () {
            return HEIGHT;
        }
        public TETile getTile ( int x, int y){
            return projWorld[x][y];
        }


}



