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
    public static final int MAXIMUMHALLSIZE = 9;
    private int numberRooms;
    private List<List<Integer>> listofMiddle;  
    //private PriorityQueue<List<Integer>> roomLocations;

    /*fills the world starting from the start position to wherever it will end
     @param Long seed to generate the same world when the same seed is passed through */
    public World(Long seed) throws Exception {
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
    private void callingHallways() throws Exception {
        int roomMiddles = listofMiddle.size() - 1;
        for (int room = 0; room < roomMiddles - 1; room++) {
            List<Integer> currentRoom =  listofMiddle.get(room);
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
                    if ( y + 1 < HEIGHT) {
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
                        if (checkSurrounding(x - 1, y - 1)  || checkSurrounding(x + 1, y + 1)) {
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
    public void fillHallway(int firstRoomX, int firstRoomY, int secondRoomX, int secondRoomY) throws Exception {
        int yDifference = Math.abs(secondRoomY - firstRoomY);
        int xDifference = Math.abs(secondRoomX - firstRoomX);
        //number of halls between the two
        int yHalls = 1;
        int xHalls = 1;
        if (yDifference > 0) {
            yHalls = randomGenerator.nextInt(1, yDifference);
        }
        if (xDifference > 0) {
            xHalls = randomGenerator.nextInt(1, xDifference);
        }
        //net sum of these will give final distance
        List<Integer> lengthsOfY = listOfHallwayValues(yDifference, yHalls);
        List<Integer> lengthsOfX = listOfHallwayValues(xDifference, xHalls);
        //starting location
        int currentY = firstRoomY;
        int currentX = firstRoomX;
        int combinedLengths = lengthsOfX.size() + lengthsOfY.size();
        while (combinedLengths > 0) {
            //0 is x direction
            int randomDirection = randomGenerator.nextInt(0,2);
            if (randomDirection == 0 && !lengthsOfX.isEmpty()) {
                //respond to x direction
                Integer addedXLocation = hallwaysPlaceable(currentX, lengthsOfX, WIDTH);
                currentX = addedXLocation + currentX;
                visuallyFillHallway(Math.min(currentX, currentX - addedXLocation), Math.max(currentX, currentX - addedXLocation), currentY, 0);
                lengthsOfX.remove(addedXLocation);
            }
            //1 is y direction
            else if (!lengthsOfY.isEmpty()) {
                Integer addedYLocation = hallwaysPlaceable(currentY, lengthsOfY, HEIGHT);
                currentY = addedYLocation + currentY;
                visuallyFillHallway(Math.min(currentY, currentY - addedYLocation), Math.max(currentY, currentY - addedYLocation),currentX, 1);
                lengthsOfY.remove(addedYLocation);
            }
            combinedLengths--;
        }
    }
    private void visuallyFillHallway(int start, int finalValue, int constant, int type) {
        if (type == 0) {
            for (start = start; start < finalValue; start++) {
                projWorld[start][constant] = FLOORREP;
            }
        } else {
            for (start = start; start < finalValue; start++) {
                projWorld[constant][start] = FLOORREP;
            }
        }

    }
    public List<Integer> listOfHallwayValues(int difference, int numHalls) {
        List<Integer> result = new ArrayList<>();
        if (difference == 0) {
            return result;
        }
        for (int i = 0; i < numHalls; i ++) {
            int num = randomGenerator.nextInt(difference) + 1;
            if (difference - num <= 0) {
                break;
            }
            result.add(num);
            difference -= num;

        }
        result.add(difference);
        return result;
    }
    public Integer hallwaysPlaceable(int currentLocation, List<Integer> inputtedLengths, int bounds) throws Exception {
        //need to check if changing inputted lengths list
        for (int index = 0; index < inputtedLengths.size(); index ++) {
            if (currentLocation + inputtedLengths.get(index) >= 0 && currentLocation + inputtedLengths.get(index) < bounds) {
                return inputtedLengths.get(index);
            }
        }
        return 0;
    }




//    //LEAVE COMMENTS EDWIN!!!!!
//    public void fillHallway(int room1x, int room1y, int room2x, int room2y) {
//        if (room2x > room1x && room2y < room1y) { // Room2x > Room1x, but room2y < room1y
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

}
