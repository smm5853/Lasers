package main;


import model.Safe;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main Class, where everything is ran
 */
public class Lasers {
    /**
     * The main method. Checks if there are an appropriate number of args, if there is then continue on. If there are 2
     * then execute the input and then continue. If there is one then just continue and ask for user inputs to place
     * lasers.
     * @param args command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // check sanity of input
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java LasersPTUI safe-file [input]");
        } else {
            //Arguments should be in the form src/tests/x [src/tests/y] where x is safe and y is the optional input
            String file = args[0];
            Safe safe = new Safe(file);
            safe.help();
            System.out.println(" ");
            safe.display();
            if(args.length ==2){
                safe.quickAdd(args[1]);
            }
            Scanner in = new Scanner(System.in);
            while (safe.getStatus()) {
                while (in.hasNext()) {
                    String line = in.nextLine();
                    String[] strings = line.split(" ");
                    if (strings.length < 1) {
                        safe.help();
                        System.out.println(" ");
                        safe.display();
                    } else if (strings[0].charAt(0) == 'd') {
                        safe.display();
                    } else if (strings[0].charAt(0) == 'v') {
                        System.out.println(safe.verify());
                        safe.display();
                    } else if (strings[0].charAt(0) == 'h') {
                        safe.help();
                    } else if (strings[0].charAt(0) == 'q') {
                        safe.setStatus(false);
                        break;
                    } else if (strings[0].charAt(0) == 'a') {
                        if (strings.length != 3) {
                            System.out.println("Incorrect Number of Coordinates");
                        } else {
                            safe.addLaser(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                        }
                    } else if (strings[0].charAt(0) == 'r') {
                        if (strings.length != 3) {
                            System.out.println("Incorrect Number of Coordinates");
                        } else {
                            safe.removeLaser(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                        }
                    } else {
                        System.out.println("Unrecognized command: " + strings[0]);
                    }
                }
            }
            System.out.println("Thanks for playing");

        }
    }
}
