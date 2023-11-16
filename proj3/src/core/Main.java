package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;


public class Main {
    public static final int CANVASWIDTH = 1200;
    public static final int CANVASHEIGHT = 800;
    public static final int TITLEFONT = 60;
    public static final int REGULARFONT = 30;
    public static final double CENTER = 0.5;

    
    /*creates the initial new game screen */
    public static void main(String[] args) {
        StdDraw.setCanvasSize(CANVASWIDTH, CANVASHEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Poppins", Font.BOLD, TITLEFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER + 0.3, "CS 61B Ghost Busters");
        font = new Font("Poppins", Font.BOLD, REGULARFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER - 0.2, "New Game (N)");
        StdDraw.text(CENTER, CENTER - 0.3, "Load Game (L)");
        StdDraw.text(CENTER, CENTER - 0.4, "Quit (Q)");
        StdDraw.picture(CENTER, CENTER + 0.05, "core/images/titleghost.png");
        newGame();
    }
    /*if the n is pressed the user is prompted to add a seed, this runs until the n is pressed*/
    public static void newGame() {
        char c = ' ' ;
        do while (StdDraw.hasNextKeyTyped()) {
            c = StdDraw.nextKeyTyped();
            if (c == 'N' || c == 'n') {
                processingSeedStrokes();
                break;
            }
        }
        while (true);
    }

    //do we need to put backspace key????
    //assumesseed is all nums
    /*controls the input section, regenerating screen to show what has been typed in*/
    public static void processingSeedStrokes() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(CENTER, CENTER, "Enter Seed");
        StdDraw.filledRectangle(CENTER, CENTER - 0.2, 0.2, 0.05);
        StdDraw.show();
        StdDraw.setPenColor(Color.BLACK);
        StringBuilder longDeveloping = new StringBuilder();

        char c = ' ';
        do while (StdDraw.hasNextKeyTyped()) {
            c = StdDraw.nextKeyTyped();
            if (c == 's' || c == 'S') {
                startGame(Long.parseLong(longDeveloping.toString()));
                break;

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
        while (true);
    }
    /*start the game in world*/
    public static void startGame(long seed) {
        World testingWorld = new World(seed);
    }

}


