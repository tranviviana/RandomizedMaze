package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    /*  @source splitting line is ChatGPT generated  */
    public static TETile[][] getWorldFromInput(String input)  {
        char[] result = input.toCharArray();
        StringBuilder seed = new StringBuilder();
        int i = 1;
        while (result[i] != 's' && result[i] != 'S') {
            seed.append(result[i]);
            i++;
        }
        World testingWorld = new World(Long.parseLong(seed.toString()));
        for (int j = 3; j < result.length; j++) {
            testingWorld.userInputHandler(testingWorld.character, result[j]);
        }
        TETile[][] tiles = testingWorld.worldState();
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length + 5);
        ter.renderFrame(tiles);
        testingWorld.generateHUD();
        testingWorld.playGame(ter);
        return testingWorld.worldState();

        //throw new RuntimeException("Please fill out AutograderBuddy!");


    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
