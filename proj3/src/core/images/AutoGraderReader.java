package core.images;

import core.World;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;

public class AutoGraderReader {

    public static World loadedWorldFromInput(String input) {
        char[] result = input.toCharArray();
        World resultingWorld = new World(Long.parseLong("1231421"));
        for (int i = 0; i < result.length; i++) {
            if (result[i] == 'l' || result[i] == 'L') {
                resultingWorld = reload();
            }
            if (result[i] == 'n' || result[i] == 'N') {
               resultingWorld = loadedWorldFromInput(input);
            }
        }
        return resultingWorld;

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
            testingWorld.userInputHandler(result[j]);
        }
        return testingWorld;

    }
}
