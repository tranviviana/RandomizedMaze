package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;
    public static final TETile FLOORREP = Tileset.FLOWER;
    public static final TETile WALLREP = Tileset.WALL;
    public static final TETile NOTHINGREP = Tileset.NOTHING;

    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    public static final int MAXROOMSIZE = WIDTH / 4;
    public static final int MINROOMSIZE = 3;
    private int numberRooms;
    private List<List<Integer>> listofMiddle;
    //private PriorityQueue<List<Integer>> roomLocations;

    /*fills the world starting from the start position to wherever it will end
     @param Long seed to generate the same world when the same seed is passed through */
    public World(Long seed) {
        randomGenerator = new Random(seed);
        projWorld = new TETile[WIDTH][HEIGHT];
        //might need to change math class
        numberRooms = randomGenerator.nextInt(3, WIDTH);
        fillRooms(0, 0, WIDTH, HEIGHT, NOTHINGREP);
        listofMiddle = new ArrayList<>();
        generateRooms();
        callingHallways();
        fillWalls();
    }


    /*creates a random room of different sizes, generating random locations, and places them on grid if possible
     * stores the location of the rooms*/
    private void generateRooms() {
        int placed = 0;
        while (placed < 3) {
            for (int room = 0; room < numberRooms; room++) {
                int roomWIDTH = randomGenerator.nextInt(MINROOMSIZE, MAXROOMSIZE);
                int roomHEIGHT = randomGenerator.nextInt(MINROOMSIZE, MAXROOMSIZE);
                int xLocation = randomGenerator.nextInt(WIDTH);
                int yLocation = randomGenerator.nextInt(HEIGHT);
                Room currentRoom = new Room(roomWIDTH, roomHEIGHT, this, xLocation, yLocation);
                if (currentRoom.placeable()) {
                    listofMiddle.add(currentRoom.roomMiddle());
                    placed++;
                    fillRooms(xLocation, yLocation, xLocation + roomWIDTH, yLocation + roomHEIGHT, FLOORREP);
                }
            }
        }
    }

    /*goes through each of the rooms and connects the room to the next room over
     * at the end connects the first to the last*/
    private void callingHallways() {
        int roomMiddles = listofMiddle.size() - 1;
        for (int room = 0; room < roomMiddles; room++) {
            List<Integer> currentRoom = listofMiddle.get(room);
            List<Integer> nextRoom = listofMiddle.get(room + 1);
            fillHallway(currentRoom.get(0), currentRoom.get(1), nextRoom.get(0), nextRoom.get(1));
        }
        //lines toooooo long  meant to connect last to first
        fillHallway(listofMiddle.get(0).get(0), listofMiddle.get(0).get(1), listofMiddle.get(roomMiddles).get(0), listofMiddle.get(roomMiddles).get(1));
    }

    //fills the tiles on the TileSet for rectangle like things
    private void fillRooms(int startX, int startY, int endX, int endY, TETile tileType) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                projWorld[x][y] = tileType;
            }
        }
    }

    /*goes through each of the tiles in the grid.. if its within the margin it checks all four directions
    if its one of the side ones it checks within the margins to avoid null error*/
    private void fillWalls() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (projWorld[x][y] == NOTHINGREP) {
                    //working through the tops of rooms/hallways
                    if (x + 1 < WIDTH) {
                        if (checkSurrounding(x + 1, y)) {
                            projWorld[x][y] = WALLREP;
                        }
                    }
                    if (x - 1 > 0) {
                        if (checkSurrounding(x - 1, y)) {
                            projWorld[x][y] = WALLREP;
                        }
                    }

                    //working through sides of rooms/ hallways
                    if (y + 1 < HEIGHT) {
                        if (checkSurrounding(x, y + 1)) {
                            projWorld[x][y] = WALLREP;
                        }
                    }
                    if (y - 1 > 0) {
                        if (checkSurrounding(x, y - 1)) {
                            projWorld[x][y] = WALLREP;
                        }
                    }

                    if (y - 1 >= 0 && y + 1 < HEIGHT && x - 1 >= 0 && x + 1 < WIDTH) {
                        //checking bottom left top right
                        if (checkSurrounding(x - 1, y - 1) || checkSurrounding(x + 1, y + 1)) {
                            projWorld[x][y] = WALLREP;
                        }
                        //checking bottom right top left
                        if (checkSurrounding(x - 1, y + 1) || checkSurrounding(x + 1, y - 1)) {
                            projWorld[x][y] = WALLREP;
                        }
                    }
                }
            }
        }
    }


    // avoids repeating the floor rep
    private boolean checkSurrounding(int x, int y) {
        return projWorld[x][y] == FLOORREP;
    }

    // returns what the world looks like (for autograder)
    public TETile[][] worldState() {
        return projWorld;
    }

    // returns the tileType of a certain x and y location
    public TETile getTile(int x, int y) {
        return projWorld[x][y];
    }

    //LEAVE COMMENTS EDWIN!!!!!
    public void fillHallway(int room1x, int room1y, int room2x, int room2y) {
        int currentX = room1x;
        int currentY = room1y;

        // CROSS SCENARIO
        if (currentX == room2x && room1y > room2y) { // room1x == room2x, but room1y > room2y
            for (int y = currentY; y != room2y; y--) {
                projWorld[currentX][y] = Tileset.FLOWER;
            }
        }
        if (currentX == room2x && room2y > room1y) { // room1x == room2x, but room1y < room2y
            for (int y = currentY; y != room2y; y++) {
                projWorld[currentX][y] = Tileset.FLOWER;
            }
        }
        if (currentX < room2x && room1y == room2y) {
            for (int x = currentX; x != room2x; x++) {
                projWorld[x][currentY] = Tileset.FLOWER;
            }
        }
        if (currentX > room2x && room1y == room2y) {
            for (int x = currentX; x != room2x; x--) {
                projWorld[x][currentY] = Tileset.FLOWER;
            }
        }
        if (currentX != room2x && currentY != room2y) {
            // Scenario 1: room1x > room2x && room1x < room2y
            if (currentX > room2x && currentY < room2y) {
                while (currentX != room2x || currentY != room2y) {
                    if (currentX != room2x) {
                        int xDifference = currentX - room2x;
                        int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
                        for (int x = currentX; x != hallwayEndX; x--) {
                            projWorld[x][currentY] = Tileset.FLOWER;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = room2y - currentY;
                        int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y < hallwayEndY; y++) {
                            projWorld[currentX][y] = Tileset.FLOWER;
                        }
                        currentY = hallwayEndY;
                    }
                }
            }
            // Scenario 2: room1x < room2x && room1y > room2y
            if (currentX < room2x && currentY > room2y) {
                while (currentX != room2x || currentY != room2y) {
                    if (currentX != room2x) {
                        int xDifference = room2x - currentX;
                        int hallwayEndX = currentX + randomGenerator.nextInt(1, xDifference + 1);
                        for (int x = currentX; x != hallwayEndX; x++) {
                            projWorld[x][currentY] = Tileset.FLOWER;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = currentY - room2y;
                        int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y != hallwayEndY; y--) {
                            projWorld[currentX][y] = Tileset.FLOWER;
                        }
                        currentY = hallwayEndY;
                    }

                }
            }
            // Scenario 3: room1x < room2x && room1y < room2y
            if (currentX < room2x && currentY < room2y) {
                while (currentX != room2x || currentY != room2y) {
                    if (currentX != room2x) {
                        int xDifference = room2x - currentX;
                        int hallwayEndX = currentX + randomGenerator.nextInt(1, xDifference + 1);
                        for (int x = currentX; x != hallwayEndX; x++) {
                            projWorld[x][currentY] = Tileset.FLOWER;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = room2y - currentY;
                        int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y < hallwayEndY; y++) {
                            projWorld[currentX][y] = Tileset.FLOWER;
                        }
                        currentY = hallwayEndY;
                    }
                }
            }
            // Scenario 4: room1x > room2x && room1y > room2y
            if (currentX > room2x && currentY > room2y) {
                while (currentX != room2x || currentY != room2y) {
                    if (currentX != room2x) {
                        int xDifference = currentX - room2x;
                        int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
                        for (int x = currentX; x != hallwayEndX; x--) {
                            projWorld[x][currentY] = Tileset.FLOWER;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = currentY - room2y;
                        int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y != hallwayEndY; y--) {
                            projWorld[currentX][y] = Tileset.FLOWER;
                        }
                        currentY = hallwayEndY;
                    }
                }
            }
        }
    }
}
        // END OF CROSS SCENARIO

//        while (currentX != room2x && currentY != room2y) {
//            // Scenario 1: room1x > room2x && room1x < room2y
//            if (currentX > room2x && currentY < room2y) {
//                int xDifference = currentX - room2x;
//                int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
//                for (int x = currentX; x != hallwayEndX; x--) {
//                    projWorld[x][currentY] = Tileset.FLOWER;
//                }
//                currentX = hallwayEndX;
//
//                int yDifference = room2y - currentY;
//                int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
//                for (int y = currentY; y < hallwayEndY; y++) {
//                    projWorld[currentX][y] = Tileset.FLOWER;
//                }
//                currentY = hallwayEndY;
//            }
//            // Scenario 2: room1x < room2x && room1y > room2y
//            if (currentX < room2x && currentY > room2y) {
//                int yDifference = room1y - room2y;
//                int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
//                for (int y = currentY; y != hallwayEndY; y--) {
//                    projWorld[currentX][y] = Tileset.FLOWER;
//                }
//                currentY = hallwayEndY;
//
//                int xDifference = room2x - currentX;
//                int hallwayEndX = currentX + randomGenerator.nextInt(1, xDifference + 1);
//                for (int x = currentX; x != hallwayEndX; x++) {
//                    projWorld[x][currentY] = Tileset.FLOWER;
//                }
//                currentX = hallwayEndX;
//            }
//            // Scenario 3: room1x < room2x && room1y < room2y
//            if (currentX < room2x && currentY < room2y) {
//                int xDifference = room2x - currentX;
//                int hallwayEndX = currentX + randomGenerator.nextInt(1, xDifference + 1);
//                for (int x = currentX; x != hallwayEndX; x++) {
//                    projWorld[x][currentY] = Tileset.FLOWER;
//                }
//                currentX = hallwayEndX;
//
//                int yDifference = room2y - currentY;
//                int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
//                for (int y = currentY; y < hallwayEndY; y++) {
//                    projWorld[currentX][y] = Tileset.FLOWER;
//                }
//                currentY = hallwayEndY;
//            }
//            // Scenario 4: room1x > room2x && room1y > room2y
//            if (currentX > room2x && currentY > room2y) {
//                int xDifference = currentX - room2x;
//                int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
//                for (int x = currentX; x != hallwayEndX; x--) {
//                    projWorld[x][currentY] = Tileset.FLOWER;
//                }
//                currentX = hallwayEndX;
//
//                int yDifference = room1y - room2y;
//                int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
//                for (int y = currentY; y != hallwayEndY; y--) {
//                    projWorld[currentX][y] = Tileset.FLOWER;
//                }
//                currentY = hallwayEndY;
//            } else {
//                break;
//            }
//
//            }
//        }





    // if (room2x > room1x && room2y < room1y) { // Room2x > Room1x, but room2y < room1y
    //            int xDifference = room2x - room1x;
    //            int yDifference = room1y - room2y;
    //            for (int startX = room1x; startX < room2x; startX += randomGenerator.nextInt(0, xDifference)) {
    //                fillRooms(room1x, room1y, startX, room1y, FLOORREP);
    //                room1x = startX;
    //                for (int startY = room1y; startY > room2y; startY -= randomGenerator.nextInt(0, yDifference)) {
    //                    for (int y = room1y; y > startY; y--) {
    //                        projWorld[startX][y] = FLOORREP;
    //                    }
    //                    room1y = startY;
    //                }
    //
    //            }
    //        } else if (room2x < room1x && room2y > room1y) { // when Room2x < Room1x, but room2y > room1y
    //            int xDifference = room1x - room2x;
    //            int yDifference = room2y - room1y;
    //            for (int startX = room1x; startX > room2x; startX -= randomGenerator.nextInt(0, xDifference)) {
    //                for (int x = room1x; x > startX; x--) {
    //                    projWorld[x][room1y] = FLOORREP;
    //                }
    //                room1x = startX;
    //                for (int startY = room1y; startY < room2y; startY += randomGenerator.nextInt(0, yDifference)) {
    //                    fillRooms(room1x, room1y, room1x, startY, FLOORREP);
    //                    room2y = startY;
    //                }
    //            }
    //        } else { // when room2x and room2y is bigger than room1x and room1y
    //            int xDifference = room2x - room1x;
    //            int yDifference = room2y - room1y;
    //            for (int startX = room1x; startX < room2x; startX += randomGenerator.nextInt(0, xDifference)) {
    //                fillRooms(room1x, room1y, startX, room1y, FLOORREP);
    //                room1x = startX;
    //                for (int startY = room1y; startY < room2y; startY += randomGenerator.nextInt(0, yDifference)) {
    //                    fillRooms(room1x, room1y, room1x, startY, FLOORREP);
    //                    room2y = startY;
    //                }
    //            }
    //        }
    //    }

