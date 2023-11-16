package core;

import core.Main;
import core.World;

import java.util.ArrayList;
import java.util.List;

public class AutoGraderReader {
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