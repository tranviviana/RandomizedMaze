package core.images;

import core.World;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AutoGraderReader {

    public static World loadedWorldFromInput(String input) {
        char[] result = input.toCharArray();
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 'l' || result[i] == 'L') {
                return reload();
            }
            if (result[i] == 'n' || result[i] == 'N') {
               return loadNewWorld(input);
            }
        }
        return null;

    }
    public static World reload() {
        String filename = "proj3/src/core/save-file.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return loadNewWorld(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static World loadNewWorld(String input) {
        char[] result = input.toCharArray();
        int i = 0;
        StringBuilder seed = new StringBuilder();
        if (result[i] == 'n' || result[i] == 'N' ) {
            i++;
            while (result[i] != 's' && result[i] != 'S') {
                seed.append(result[i]);
                i++;
            }
        }
        World testingWorld = new World(Long.parseLong(seed.toString()));
        for (int j = i + 1; j < result.length; j++) {
            if (result[j] == ':' && j + 1 < result.length) {
                if (result[j + 1] == 'q' || result[j + 1] == 'Q') {
                    testingWorld.saveAndQuit(true);
                    return testingWorld;
                }
            }
            testingWorld.userInputHandler(result[j]);
        }
        return testingWorld;

    }
}
