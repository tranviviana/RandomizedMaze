package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.io.*;



public class GameUI {
    public static final int CANVASWIDTH = 1200;
    public static final int CANVASHEIGHT = 800;
    public static final int TITLEFONT = 60;
    public static final int REGULARFONT = 30;
    public static final double CENTER = 0.5;


    public GameUI() throws FileNotFoundException {
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
        newGame();
    }

    /*if the n is pressed the user is prompted to add a seed, this runs until the n is pressed*/
    public static void newGame() throws FileNotFoundException {
        do {
            while (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == 'N' || c == 'n') {
                    //resets previousGame so it is only storing the current game info
                    String filePath = "previousGame.txt";
                    new PrintWriter(new FileOutputStream(filePath), true);
                    World newWorld = processingSeedStrokes();
                    TERenderer ter = new TERenderer();
                    TETile[][] tiles = newWorld.worldState();
                    ter.initialize(tiles.length, tiles[1].length + 5);
                    newWorld.renderFrame(ter);
                    newWorld.generateHUD();
                    newWorld.playGame(ter);

                }
                if (c == 'l' || c == 'L') {
                    //load the old game
                    reload(false);
                }
                if (c == 'r' || c == 'R') {
                    reload(true);
                }
                if (c == 'q' || c == 'Q') {
                    System.exit(0);
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
            return autograder.loadedWorldFromInput(isReplay);
        }
        return null;
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

