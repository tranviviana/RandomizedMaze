package core;

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
    public static TETile[][] getWorldFromInput(String input)  {
        AutoGraderReader autograder = new AutoGraderReader(input);
        return autograder.loadedWorldFromInput().worldState();
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

    public static class AutoGraderReader {
        private List<Character> actions;

        public AutoGraderReader(String input) {
            actions = new ArrayList<>();
            for (int c = 0; c < input.length(); c++) {
                actions.add(input.charAt(c));
            }

        }
        public World loadedWorldFromInput() {
            if (actions.get(0) == 'l' || actions.get(0) == 'L') {
                World oldWorld = Main.reload();
                actions.remove(0);
                return loadNewWorld(oldWorld);
            } else if (actions.get(0) == 'n' || actions.get(0) == 'N') {
                World newWorld = new World(getSeed());
                return loadNewWorld(newWorld);
            }
            return null;
        }

        private long getSeed() {
            StringBuilder seed = new StringBuilder();
            if (actions.get(0) == 'n' || actions.get(0) == 'N') {
                actions.remove(0);
                while (actions.get(0) != 's' && actions.get(0) != 'S') {
                    seed.append(actions.get(0));
                    actions.remove(0);
                }
                actions.remove(0);
            }
            return Long.parseLong(seed.toString());
        }
        private World loadNewWorld(World oldWorld) {
            for (int j = 0; j < actions.size(); j++) {
                if (actions.get(j) == ':' && j + 1 < actions.size()) {
                    if (actions.get(j + 1) == 'q' || actions.get(j + 1) == 'Q') {
                        oldWorld.saveAndQuit(true);
                        return oldWorld;
                    }
                }
                oldWorld.userInputHandler(actions.get(j));
            }
            return oldWorld;
        }
    }
}
