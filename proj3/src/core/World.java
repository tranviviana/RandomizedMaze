package core;
import tileengine.*;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.io.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.time.*;

import static edu.princeton.cs.algs4.StdDraw.hasNextKeyTyped;
import static edu.princeton.cs.algs4.StdDraw.nextKeyTyped;

public class World {
    TETile[][] projWorld;
    Random randomGenerator;

    public static final TETile FLOORREP = new TETile('F', Color.gray, Color.black, "Floor", "core/images/floor.jpg");

    public static final TETile WALLREP = new TETile('W', Color.gray, Color.black, "Wall", "core/images/walltile.jpg");
    public static final TETile NOTHINGREP = Tileset.NOTHING;
    public static final TETile GHOSTTILE = new TETile('G', Color.gray, Color.black, "Ghost", "core/images/ghosts.jpg");
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static final int MAXROOMSIZE = WIDTH / 4;
    public static final int MINROOMSIZE = 3;
    private int numberRooms;
    private List<List<Integer>> listofMiddle;
    private List<List<Integer>> sizeofRooms;
    private Avatar character;
    private StringBuilder stringInput;

    private final String date = LocalDate.now().toString();
    private String time = LocalTime.now().toString();


    //private PriorityQueue<List<Integer>> roomLocations;
    /*fills the world starting from the start position to wherever it will end
     @param Long seed to generate the same world when the same seed is passed through */
    /*creates UI and spawns the avatar*/
    public World(Long seed) {
        stringInput = new StringBuilder();
        stringInput.append("n").append(seed).append("s");
        randomGenerator = new Random(seed);
        projWorld = new TETile[WIDTH][HEIGHT];
        //might need to change math class
        numberRooms = randomGenerator.nextInt(3, WIDTH);
        fillRooms(0, 0, WIDTH, HEIGHT, NOTHINGREP);
        listofMiddle = new ArrayList<>();
        sizeofRooms = new ArrayList<>();
        generateRooms();
        callingHallways();
        fillWalls();
        character = new Avatar(projWorld, listofMiddle.get(0).get(0), listofMiddle.get(0).get(1));
        ghostSpawner();
    }



    /*style of the upper HUD shows how many ghosts busted*/
    public void generateHUD() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(1, HEIGHT + 4, "Tile: " + tileMoused().description());
        StdDraw.text(WIDTH - 4, HEIGHT + 4, "Ghost Busted: " + character.returnGhostBusted());
        StdDraw.text((double) WIDTH / 2, HEIGHT + 4, "Date: " + date);
    }
    /*creates a random room of different sizes, generating random locations, and places them on grid if possible
     * stores the location of the rooms*/
    private void generateRooms() {
        int placed = 0;
        while (placed < 4) {
            for (int room = 0; room < numberRooms; room++) {
                int roomWIDTH = randomGenerator.nextInt(MINROOMSIZE, MAXROOMSIZE);
                int roomHEIGHT = randomGenerator.nextInt(MINROOMSIZE, MAXROOMSIZE);
                int xLocation = randomGenerator.nextInt(WIDTH);
                int yLocation = randomGenerator.nextInt(HEIGHT);
                Room currentRoom = new Room(roomWIDTH, roomHEIGHT, this, xLocation, yLocation);
                if (currentRoom.placeable()) {
                    sizeofRooms.add(currentRoom.ghostHelper());
                    listofMiddle.add(currentRoom.roomMiddle());
                    placed++;
                    fillRooms(xLocation, yLocation, xLocation + roomWIDTH, yLocation + roomHEIGHT, FLOORREP);
                }
            }
        }
        numberRooms = placed;
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
        int xCoordOne = listofMiddle.get(0).get(0);
        int yCoordOne = listofMiddle.get(0).get(1);
        int xCoordTwo = listofMiddle.get(roomMiddles).get(0);
        int yCoordTwo = listofMiddle.get(roomMiddles).get(1);
        fillHallway(xCoordOne, yCoordOne, xCoordTwo, yCoordTwo);
    }
    //fills the tiles on the TileSet for rectangle like things
    private void fillRooms(int startX, int startY, int endX, int endY, TETile tileType) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                projWorld[x][y] = tileType;
            }
        }
    }
    /*goes through each of the tiles in the grid. if its within the margin it checks all four directions
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
    //returns where in the random the world is at

    // returns the tileType of a certain x and y location
    public TETile getTile(int x, int y) {
        return projWorld[x][y];
    }
    //LEAVE COMMENTS EDWIN!!!!!
    private void fillHallway(int room1x, int room1y, int room2x, int room2y) {
        int currentX = room1x;
        int currentY = room1y;
        fillHallWayHelper1(currentX, currentY, room2x, room2y);
        if (currentX != room2x && currentY != room2y) {
            // Scenario 1: room1x > room2x && room1x < room2y
            if (currentX > room2x && currentY < room2y) {
                while (currentX != room2x || currentY != room2y) {
                    if (currentX != room2x) {
                        int xDifference = currentX - room2x;
                        int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
                        for (int x = currentX; x != hallwayEndX; x--) {
                            projWorld[x][currentY] = FLOORREP;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = room2y - currentY;
                        int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y < hallwayEndY; y++) {
                            projWorld[currentX][y] = FLOORREP;
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
                            projWorld[x][currentY] = FLOORREP;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = currentY - room2y;
                        int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y != hallwayEndY; y--) {
                            projWorld[currentX][y] = FLOORREP;
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
                            projWorld[x][currentY] = FLOORREP;
                        }
                        currentX = hallwayEndX;
                    }
                    if (currentY != room2y) {
                        int yDifference = room2y - currentY;
                        int hallwayEndY = currentY + randomGenerator.nextInt(1, yDifference + 1);
                        for (int y = currentY; y < hallwayEndY; y++) {
                            projWorld[currentX][y] = FLOORREP;
                        }
                        currentY = hallwayEndY;
                    }
                }
            }
            // Scenario 4: room1x > room2x && room1y > room2y
            if (currentX > room2x && currentY > room2y) {
                fillHallWayHelper2(currentX, currentY, room2x, room2y);
            }
        }
    }

    private void fillHallWayHelper1(int room1x, int room1y, int room2x, int room2y) {
        // CROSS SCENARIO
        if (room1x == room2x && room1y > room2y) { // room1x == room2x, but room1y > room2y
            for (int y = room1y; y != room2y; y--) {
                projWorld[room1x][y] = FLOORREP;
            }
        }
        if (room1x == room2x && room2y > room1y) { // room1x == room2x, but room1y < room2y
            for (int y = room1y; y != room2y; y++) {
                projWorld[room1x][y] = FLOORREP;
            }
        }
        if (room1x < room2x && room1y == room2y) {
            for (int x = room1x; x != room2x; x++) {
                projWorld[x][room1y] = FLOORREP;
            }
        }
        if (room1x > room2x && room1y == room2y) {
            for (int x = room1x; x != room2x; x--) {
                projWorld[x][room1y] = FLOORREP;
            }
        }
    }
    private void fillHallWayHelper2(int currentX, int currentY, int room2x, int room2y) {
        while (currentX != room2x || currentY != room2y) {
            if (currentX != room2x) {
                int xDifference = currentX - room2x;
                int hallwayEndX = currentX - randomGenerator.nextInt(1, xDifference + 1);
                for (int x = currentX; x != hallwayEndX; x--) {
                    projWorld[x][currentY] = FLOORREP;
                }
                currentX = hallwayEndX;
            }
            if (currentY != room2y) {
                int yDifference = currentY - room2y;
                int hallwayEndY = currentY - randomGenerator.nextInt(1, yDifference + 1);
                for (int y = currentY; y != hallwayEndY; y--) {
                    projWorld[currentX][y] = FLOORREP;
                }
                currentY = hallwayEndY;
            }
        }
    }
    public void playGame(TERenderer ter) {
        renderFrame(ter);
        TETile currentTile = tileMoused();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (!isGameOver()) {
            if (currentTime != LocalTime.now()) {
                String formattedTime = currentTime.format(formatter);
                StdDraw.text((double) WIDTH / 2, HEIGHT + 3, "Time: " + formattedTime);
                currentTime = LocalTime.now();
            }


            if (tileMoused() != currentTile) {
                currentTile = tileMoused();

            }
            if (hasNextKeyTyped()) {
                char c = nextKeyTyped();
                userInputHandler(c);

            }
            renderFrame(ter);
        }
    }


    /*new worldstate everytime the avatar moves so this gets that and renders the screen for it*/
    public void renderFrame(TERenderer ter) {
        StdDraw.show();
        ter.renderFrame(this.worldState());
        generateHUD();
    }
    /*returns when a person can still play or not */
    private boolean isGameOver() {
        return character.returnGhostBusted() == numberRooms;
    }
    /* converts the x and y location to the tile type */
    private TETile tileMoused() {
        double xLocation = StdDraw.mouseX();
        double yLocation = StdDraw.mouseY();
        if (xLocation >= WIDTH || yLocation >= HEIGHT) {
            return NOTHINGREP;
        }
        return projWorld[(int) Math.floor(xLocation)][(int) Math.floor(yLocation)];
    }
    /*takes in the movement inputs*/
    public void userInputHandler(char c) {
        if (c == ':') {
            int waitingForNextKey = 0;
            while (waitingForNextKey == 0) {
                if (hasNextKeyTyped()) {
                    c = nextKeyTyped();
                    if (c == 'q' || c == 'Q') {
                        //save and quit for manual input
                        saveAndQuit(false);
                    }
                    waitingForNextKey = 1;
                }
            }
        }
        stringInput.append(c);
        switch (c) {
            default -> character.avatarMove(0, 0);
            case 'w' -> character.avatarMove(0, 1);
            case 'a' -> character.avatarMove(-1, 0);
            case 'd' -> character.avatarMove(1, 0);
            case 's' -> character.avatarMove(0, -1);

        }
    }
    // saves the current state of the game in a txt file to be loaded into later
    public void saveAndQuit(boolean isAutograderTrue) {
        String filePath =  "save-file.txt";
        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            writer.println(stringInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isAutograderTrue) {
            System.exit(0);
        }
    }

    private void ghostSpawner() {
        for (List<Integer> rooms : listofMiddle) {
            // Overall: comparing the coordinates of the rooms with the size of the rooms
            // Comments: I'm looking over this and I think I could have done it a better way but lol.
            List<Integer> random = sizeofRooms.get(0);
            sizeofRooms.remove(0);
            // Getting random numbers.
            // Did this way because nextInt does not take negative bounds into consideration
            int randomNumber = randomGenerator.nextInt(0, 4);
            int spawnX = 0;
            int spawnY = 0;
            // Depending on the random number. Ghosts spawn in a random location surrounding the origin.
            if (randomNumber == 0) {
                spawnX = rooms.get(0) + randomGenerator.nextInt(random.get(0));
                spawnY = rooms.get(1) + randomGenerator.nextInt(random.get(1));
            } else if (randomNumber == 1) {
                spawnX = rooms.get(0) - randomGenerator.nextInt(random.get(0));
                spawnY = rooms.get(1) + randomGenerator.nextInt(random.get(1));
            } else if (randomNumber == 2) {
                spawnX = rooms.get(0) + randomGenerator.nextInt(random.get(0));
                spawnY = rooms.get(1) - randomGenerator.nextInt(random.get(1));
            } else {
                spawnX = rooms.get(0) - randomGenerator.nextInt(random.get(0));
                spawnY = rooms.get(1) - randomGenerator.nextInt(random.get(1));
            }
            if (projWorld[spawnX][spawnY] == FLOORREP) {
                projWorld[spawnX][spawnY] = GHOSTTILE;
            }
        }
    }
}
