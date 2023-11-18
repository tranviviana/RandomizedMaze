package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.io.*;


public class Main {
    public static final int CANVASWIDTH = 1200;
    public static final int CANVASHEIGHT = 800;
    public static final int TITLEFONT = 60;
    public static final int REGULARFONT = 30;
    public static final double CENTER = 0.5;


    /*creates the initial new game screen */
    public static void main(String[] args) throws Exception {
        StdDraw.setCanvasSize(CANVASWIDTH, CANVASHEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Poppins", Font.BOLD, TITLEFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER + 0.4, "CS 61B Ghost Busters");
        font = new Font("Poppins", Font.BOLD, REGULARFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER - 0.1, "New Game (N)");
        StdDraw.text(CENTER, CENTER - 0.2, "Load Game (L)");
        StdDraw.text(CENTER, CENTER - 0.3, "Replay (R)");
        StdDraw.text(CENTER, CENTER - 0.4, "Quit (Q)");
        StdDraw.picture(CENTER, CENTER + 0.15, "core/images/titleghost.png");
        World testingWorld = newGame();
        TETile[][] tiles = testingWorld.worldState();
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length + 5);
        ter.renderFrame(tiles);
        testingWorld.generateHUD();
        testingWorld.playGame(ter);
    }
    /*if the n is pressed the user is prompted to add a seed, this runs until the n is pressed*/
    public static World newGame() throws FileNotFoundException {
        do {
            while (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'N' || c == 'n') {
                    //resets previousGame so it is only storing the current game info
                    String filePath =  "previousGame.txt";
                    new PrintWriter(new FileOutputStream(filePath), true);
                    return processingSeedStrokes();
                }
                if (c == 'l' || c == 'L') {
                    //load the old game
                    return reload(false);
                }
                if (c == 'r' || c == 'R') {
                    return reload(true);
                }

            }
        }
        while (true);
    }

    /*reloads the game by parsing through the txt file and calling autograder to finish it*/
    public static World reload(boolean isReplay) {
        In fileName = new In("save-file.txt");
        if (fileName.isEmpty()) {
            System.exit(0);
        } else {
            String oldGame = fileName.readLine();
            AutoGraderReader autograder = new AutoGraderReader(oldGame);
            storeOldGame(oldGame);
            return autograder.loadedWorldFromInput(isReplay);
        }
        return null;
    }
    private static void storeOldGame(String oldGame) {
        String filePath =  "previousGame.txt";
        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            writer.println(oldGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //do we need to put backspace key????
    //assumesseed is all nums
    /*controls the input section, regenerating screen to show what has been typed in*/
    public static World processingSeedStrokes() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(CENTER, CENTER, "Enter Seed");
        StdDraw.filledRectangle(CENTER, CENTER - 0.2, 0.2, 0.05);
        StdDraw.show();
        StdDraw.setPenColor(Color.BLACK);
        StringBuilder longDeveloping = new StringBuilder();

        char c = ' ';
        do {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 's' || c == 'S') {
                    return startGame(Long.parseLong(longDeveloping.toString()));

                }
                //when game ends it escapes here
                longDeveloping.append(c);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(CENTER, CENTER, "Enter Seed");
                StdDraw.filledRectangle(CENTER, CENTER - 0.2, 0.2, 0.05);
                StdDraw.setPenColor(Color.black);
                StdDraw.text(CENTER, CENTER - 0.2, String.valueOf(longDeveloping));
                StdDraw.show();
            }
        }
        while (true);
    }
    /*start the game in world*/
    public static World startGame(long seed) {
        return new World(seed);
    }

}

