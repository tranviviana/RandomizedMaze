package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.util.ArrayList;
import java.util.List;

public class AutoGraderReader {
    private List<Character> actions;
    private int leftoverIndex;

    public AutoGraderReader(String input) {
        actions = new ArrayList<>();

        for (int c = 0; c < input.length(); c++) {
            actions.add(input.charAt(c));
        }
        leftoverIndex = startIndextoShow();
        System.out.println(leftoverIndex);
    }
    /*returns the edited world when a user presses l or n*/
    public World loadedWorldFromInput(boolean isReplay) {
        if (actions.get(0) == 'l' || actions.get(0) == 'L') {
            isReplay = false;
            World oldWorld = Main.reload(isReplay);
            actions.remove(0);
            if (oldWorld == null) {
                //short circuiting for autograder
                AutograderBuddy.getWorldFromInput(":q");
            }
            return loadNewWorld(oldWorld, isReplay);
        } else {
            World newWorld = new World(getSeed());
            return loadNewWorld(newWorld, isReplay);
        }
    }
    /*returns the long needed to generate the new world*/
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
    /*generates the newWorld status based off of old world. For example if in my old file I moved left twice,
     *I move left
     *twice in this new world*/
    private int startIndextoShow() {
        In fileName = new In("previousGame.txt");
        String previousGameActions = fileName.readLine();
        int localStartIndex = 0;
        if (previousGameActions != null) {
            localStartIndex = actions.size() - previousGameActions.length();
        }
        return localStartIndex;
    }

    private World loadNewWorld(World oldWorld, boolean isReplay) {
        while (!actions.isEmpty()) {
            if (actions.get(0) == ':' && actions.size() > 1) {
                actions.remove(0);
                if (actions.get(0) == 'q' || actions.get(0) == 'Q') {
                    oldWorld.saveAndQuit(true);
                    actions.remove(0);
                }
            }
            if (!actions.isEmpty()) {
                if (actions.get(0) == 'l' || actions.get(0) == 'L') {
                    oldWorld = loadedWorldFromInput(isReplay);
                }
            }
            if (!actions.isEmpty()) {
                if (isReplay && actions.size() == leftoverIndex) {
                    TERenderer ter = new TERenderer();
                    ter.initialize(oldWorld.worldState().length, oldWorld.worldState()[0].length + 5);
                    ter.renderFrame(oldWorld.worldState());
                    oldWorld.generateHUD();
                    //oldWorld.renderFrame();
                    StdDraw.pause(1000);
                }
                oldWorld.userInputHandler(actions.get(0));
                actions.remove(0);
            }

        }
        return oldWorld;
    }
}

