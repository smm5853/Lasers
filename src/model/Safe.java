package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Safe class
 * by: Syed Manal
 */
public class Safe {
    private Item[][] tiles; // the actual tiles in the safe
    private int mainRow; //rows in tiles
    private int mainColumn; // columns in tiles
    private boolean status; // if the game is still being played
    /**
     * Constructor for the safe which takes a file that contains the safe's information and builds it in the
     * 2d array
     * @param filename the name of the file that contains the safe information
     * @throws FileNotFoundException is thrown if the file could not be found
     */
    public Safe(String filename) throws FileNotFoundException {
        status = true;
        Scanner file = new Scanner(new File(filename));
        String[] coordinates = file.nextLine().split(" ");
        mainRow = Integer.parseInt(coordinates[0]);
        mainColumn = Integer.parseInt(coordinates[1]);
        tiles = new Item[mainRow][mainColumn];
        for(int i =0; i<mainRow; i++){
            String line = file.nextLine();
            String[] content = line.split(" ");
            for(int j = 0; j<mainColumn; j++){
                if(content[j].equals("0") || content[j].equals("1") || content[j].equals("2") || content[j].equals("3") || content[j].equals("4") ){
                    tiles[i][j] = new Pillar(Integer.parseInt(content[j]));
                }else if(content[j].equals("X")){
                    tiles[i][j] = new Pillar();
                }else{
                    tiles[i][j] = new Laser(0);
                }
            }
        }
    }

    /**
     * Reads a file and executes the commands on it
     * @param filename name of file with safe commands
     * @throws FileNotFoundException is thrown is file could not be found
     */
    public void quickAdd(String filename) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filename));
        while(file.hasNextLine()){
            String[] command = file.nextLine().split(" ");
            if(command[0].charAt(0) == 'a' && command.length==3){
                this.addLaser(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
            }else if(command[0].charAt(0) == 'r' && command.length==3){
                this.removeLaser(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
            } else if (command[0].charAt(0) == 'q') {
                this.setStatus(false);
            }else if(command[0].charAt(0) == 'v'){
                this.verify();
            }else if(command[0].charAt(0) == 'h'){
                this.help();
            }
        }
    }

    /**
     * Help, which displays the different commands
     */
    public void help(){
        System.out.println("a|add r c: Add laser to (r,c)");
        System.out.println("d|display: Display safe");
        System.out.println("h|help: Print this help message");
        System.out.println("q|quit: Exit program");
        System.out.println("r|remove r c: Remove laser from (r,c)");
        System.out.println("v|verify: Verify safe correctness");
    }

    /**
     * The display, which shows the safe itself
     */
    public void display(){
        String columns = "   ";
        String dashes = "-";
        for(int i =0; i<mainColumn; i++){
            columns += i % 10 + "  ";
            dashes += "---";
        }
        System.out.println(columns);
        System.out.println(dashes);
        for(int i=0; i<mainRow; i++) {
            String row = i % 10 + "|";
            for (int j = 0; j < tiles[i].length; j++) {
                row += tiles[i][j].getName();
            }
            System.out.println(row);
        }
    }

    /**
     * Adding a laser to a given tile in the safe. First checks if the location is valid or not, and then adds the laser.
     * Then calls a LaserHelper method, to deal with the beams. Finally, checks if there are pillars in the 4 cardinal
     * directions, and if there are, then that columns count is increased
     * @param row the row which the laser is being added to
     * @param column the column which the laser is being added to
     */
    public void addLaser(int row, int column) {
        if(row < 0 || column < 0 || row > mainRow - 1 || column >mainColumn - 1 ||
                tiles[row][column] instanceof Pillar || tiles[row][column].getStatus() == 1) {
            System.out.println("Error Adding Laser at (" + row + ", " + column + ")");
        }else{
            tiles[row][column].setStatus(1);
            tiles[row][column].setActive(true);
            tiles[row][column].addCount(1);
            this.addLaserHelper(row, column);
            int down = row -1;
            int up = row +1;
            int right = column +1;
            int left= column-1;
            if(down >= 0){
                if(tiles[down][column] instanceof Pillar){
                    tiles[down][column].addCount(1);
                }
            }
            if(left >= 0){
                if(tiles[row][left] instanceof Pillar){
                    tiles[row][left].addCount(1);
                }
            }
            if(up < mainRow){
                if(tiles[up][column] instanceof Pillar){
                    tiles[up][column].addCount(1);
                }
            }
            if(right < mainColumn ){
                if(tiles[row][right] instanceof Pillar){
                    tiles[row][right].addCount(1);
                }
            }
            this.display();
        }
    }

    /**
     * setter for status of the game
     * @param status what the status is changing to
     */
    public void setStatus(boolean status){
        this.status = status;
    }

    /**
     * getter for the status of the game
     * @return status of the game
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * helper method that is responsible for enabling the beams when a laser is acivated
     * @param row row where the laser is activated
     * @param column column where the laser is activated
     */
    public void addLaserHelper(int row, int column){
        for(int i = row-1; i>=0; i--){
            if(tiles[i][column] instanceof Pillar){
                break;
            }else if(tiles[i][column] instanceof Laser){
                if(tiles[i][column].getStatus()==0){
                    tiles[i][column].setStatus(2);
                    tiles[i][column].setActive(true);
                    tiles[i][column].addCount(1);
                }else if(tiles[i][column].getStatus()==1){
                    tiles[i][column].addCount(1);
                    tiles[i][column].setValid(false);
                    tiles[row][column].setValid(false);
                    break;
                }else if(tiles[i][column].getStatus()==2){
                    tiles[i][column].addCount(1);
                }
            }
        }
        for(int i = row+1; i<mainRow; i++){
            if(tiles[i][column] instanceof Pillar){
                break;
            }else if(tiles[i][column] instanceof Laser){
                if(tiles[i][column].getStatus()==0){
                    tiles[i][column].setStatus(2);
                    tiles[i][column].setActive(true);
                    tiles[i][column].addCount(1);
                }else if(tiles[i][column].getStatus()==1){
                    tiles[i][column].addCount(1);
                    tiles[i][column].setValid(false);
                    tiles[row][column].setValid(false);
                    break;
                }else if(tiles[i][column].getStatus()==2){
                    tiles[i][column].addCount(1);
                }
            }
        }
        for(int j = column-1; j>=0; j--){
            if(tiles[row][j] instanceof Pillar){
                break;
            }else if(tiles[row][j] instanceof Laser){
                if(tiles[row][j].getStatus()==0){
                    tiles[row][j].setStatus(2);
                    tiles[row][j].setActive(true);
                    tiles[row][j].addCount(1);
                }else if(tiles[row][j].getStatus()==1){
                    tiles[row][j].addCount(1);
                    tiles[row][j].setValid(false);
                    tiles[row][column].setValid(false);
                    break;
                }else if(tiles[row][j].getStatus()==2){
                    tiles[row][j].addCount(1);
                }
            }
        }
        for(int j = column+1; j<mainColumn; j++){
            if(tiles[row][j] instanceof Pillar){
                break;
            }else if(tiles[row][j] instanceof Laser){
                if(tiles[row][j].getStatus()==0){
                    tiles[row][j].setStatus(2);
                    tiles[row][j].setActive(true);
                    tiles[row][j].addCount(1);
                }else if(tiles[row][j].getStatus()==1){
                    tiles[row][j].addCount(1);
                    tiles[row][j].setValid(false);
                    tiles[row][column].setValid(false);
                    break;
                }else if(tiles[row][j].getStatus()==2){
                    tiles[row][j].addCount(1);
                }
            }
        }
    }

    /**
     * Remove a laser from a given coordinate. First verify that the coordinate is valid and then proceed to remove it,
     * and any laser beams if applicable. Call a helper to deal with the beams. Then remove the laser from outlet
     * if it is connected to a pillar
     * @param row row of laser to be removed
     * @param column column of laser to be removed
     */
    public void removeLaser(int row, int column){
        if(row < 0 || column < 0 || row>mainRow || column>mainColumn-1 ||
                tiles[row][column] instanceof Pillar || tiles[row][column].getStatus() != 1){
            System.out.println("Error Removing Laser at (" + row + ", " + column + ")");
        }else{
            tiles[row][column].addCount(-1);
            if(tiles[row][column].getCount() > 0){
                tiles[row][column].setStatus(2);
            }else{
                tiles[row][column].setStatus(0);
                tiles[row][column].setActive(false);
            }
            this.removeLaserHelper(row, column);
            int down = row -1;
            int up = row +1;
            int right = column +1;
            int left= column-1;
            if(down >= 0){
                if(tiles[down][column] instanceof Pillar){
                    tiles[down][column].addCount(-1);
                }
            }
            if(left >= 0){
                if(tiles[row][left] instanceof Pillar){
                    tiles[row][left].addCount(-1);
                }
            }
            if(up < mainColumn){
                if(tiles[up][column] instanceof Pillar){
                    tiles[up][column].addCount(-1);
                }
            }
            if(right < mainRow){
                if(tiles[row][right] instanceof Pillar){
                    tiles[row][right].addCount(-1);
                }
            }
            this.display();
        }
    }

    /**
     * helper to deal with laser beam removal, called by the removeLaser method
     * @param row row to start with
     * @param column column to start with
     */
    public void removeLaserHelper(int row, int column){
        for(int i=row-1; i>=0; i-- ){
            if(tiles[i][column] instanceof Pillar){
                break;
            }else if(tiles[i][column].getStatus() == 1){
                tiles[i][column].setValid(true);
                tiles[row][column].setValid(true);
                tiles[i][column].addCount(-1);
            }else if(tiles[i][column].getStatus() ==2){
                tiles[i][column].addCount(-1);
                if(tiles[i][column].getCount()==0){
                    tiles[i][column].setStatus(0);
                }
            }
        }
        for(int i=row+1; i<mainRow; i++ ){
            if(tiles[i][column] instanceof Pillar){
                break;
            }else if(tiles[i][column].getStatus() == 1){
                tiles[i][column].setValid(true);
                tiles[row][column].setValid(true);
                tiles[i][column].addCount(-1);
            }else if(tiles[i][column].getStatus() ==2){
                tiles[i][column].addCount(-1);
                if(tiles[i][column].getCount()==0){
                    tiles[i][column].setStatus(0);
                }
            }
        }
        for(int j=column-1; j>=0; j-- ){
            if(tiles[row][j] instanceof Pillar){
                break;
            }else if(tiles[row][j].getStatus() == 1){
                tiles[row][j].setValid(true);
                tiles[row][column].setValid(true);
                tiles[row][j].addCount(-1);
            }else if(tiles[row][j].getStatus() ==2){
                tiles[row][j].addCount(-1);
                if(tiles[row][j].getCount()==0){
                    tiles[row][j].setStatus(0);
                }
            }
        }
        for(int j=column+1; j<mainColumn; j++ ){
            if(tiles[row][j] instanceof Pillar){
                break;
            }else if(tiles[row][j].getStatus() == 1){
                tiles[row][j].setValid(true);
                tiles[row][column].setValid(true);
                tiles[row][j].addCount(-1);
            }else if(tiles[row][j].getStatus() ==2){
                tiles[row][j].addCount(-1);
                if(tiles[row][j].getCount()==0){
                    tiles[row][j].setStatus(0);
                }
            }
        }
    }

    /**
     * Verify if the safe works, going from left to right and eventually down. If the safe is fully verified, then
     * the game is won.
     * @return a string which indicates whether or not the safe is verified
     */
    public String verify(){
        for(int i=0; i<mainRow; i++){
            for(int j=0; j<tiles[i].length; j++){
                if(!tiles[i][j].isActive() || !tiles[i][j].isValid()){
                    return "Error verifying at: (" + i +", " + j +")";
                }
            }
        }
        return "Safe is fully verified!";
    }

}
