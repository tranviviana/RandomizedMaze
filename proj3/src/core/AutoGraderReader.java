package core;

import java.util.ArrayList;
import java.util.List;

public class AutoGraderReader {
    public List<Character> actions;

    public AutoGraderReader(String input) {
        actions = new ArrayList<>();
        for (int c = 0; c < input.length(); c++) {
            actions.add(input.charAt(c));
        }
    }
    /*returns the edited world when a user presses l or n*/
    public World loadedWorldFromInput() {
        if (actions.get(0) == 'l' || actions.get(0) == 'L') {
            World oldWorld = Main.reload();
            actions.remove(0);
            if (oldWorld == null) {
                //short circuiting for autograder
                AutograderBuddy.getWorldFromInput(":q");
            }
            return loadNewWorld(oldWorld);
        } else {
            World newWorld = new World(getSeed());
            return loadNewWorld(newWorld);
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
    /*generates the newWorld status based off of old world. For example if in my old file I moved left twice, I move left
    twice in this new world*/

    private World loadNewWorld(World oldWorld) {
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
                    oldWorld = loadedWorldFromInput();
                }
            }
            if (!actions.isEmpty()) {
                oldWorld.userInputHandler(actions.get(0));
                actions.remove(0);
            }

        }
        return oldWorld;
    }
}

