package org.cis120.matching;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Matching {

    private int[][] board;
    private boolean[][] boardState; // true = revealed; false = unrevealed
    private boolean oddTurn; // true = odd turn; false = even turn

    // storing the game state (the coordinates of the current cards chosen to be
    // revealed in that round)
    private LinkedList<Integer> currentCoordinates;

    // situation where the squares are both opened with different values
    private boolean unmatchingMove;

    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Matching() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended.
     * 
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int r, int c) {
        // if revealed already or game is over, then invalid move
        if (boardState[r][c] || gameOver) {
            // printGameState();
            return false;
        }
        if (oddTurn) {
            unmatchingMove = false;
            boardState[r][c] = true;
            // storing the row and column for the first card
            currentCoordinates.add(0, r);
            currentCoordinates.add(1, c);
            currentCoordinates.add(2, 0);
            currentCoordinates.add(3, 0);
            // set the next turn to be an even turn
            oddTurn = false;
            // printGameState();
            return true;
        } else {
            // storing the row and column for the second card
            currentCoordinates.set(2, r);
            currentCoordinates.set(3, c);
            if (board[r][c] == board[currentCoordinates.get(0)][currentCoordinates.get(1)]) {
                unmatchingMove = false;
                boardState[r][c] = true;
                oddTurn = true;
                // printGameState();
                return true;
            } else {
                unmatchingMove = true;
                boardState[currentCoordinates.get(0)][currentCoordinates.get(1)] = false;
                boardState[r][c] = false;
                oddTurn = true;
                // printGameState();
                return true;
            }

        }
    }

    /**
     * hasWon checks whether the game has reached a win condition (all cards
     * revealed).
     *
     * @return true if play has won, false otherwise
     */
    public boolean hasWon() {
        for (int j = 0; j <= 3; j++) {
            for (int k = 0; k <= 3; k++) {
                if (!boardState[j][k]) {
                    return false;
                }
            }
        }
        gameOver = true;
        return true;
    }

    /**
     * printGameState prints the current game state for debugging.
     */
    public void printGameState() {
        System.out.println("------------------------------");
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < boardState[0].length; i++) {
            for (int j = 0; j < boardState.length; j++) {
                System.out.print(boardState[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println(
                "first card: (" + currentCoordinates.get(0) + ", " + currentCoordinates.get(1) + ")"
        );
        System.out.println(
                "second card: (" + currentCoordinates.get(2) + ", " + currentCoordinates.get(3)
                        + ")"
        );

        System.out.println("oddTurn: " + oddTurn);

        for (int i = 0; i < currentCoordinates.size(); i++) {
            System.out.print(currentCoordinates.get(i) + " ");
        }
        System.out.println();

        System.out.println("hasWon: " + hasWon());

    }

    /**
     * save saves the current game to file to play later.
     */
    public void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("files/saved.txt", false));
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bw.write(board[j][i] + " ");
                }
                bw.write("\n");
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bw.write(boardState[j][i] + " ");
                }
                bw.write("\n");
            }
            bw.write(String.valueOf(oddTurn) + "\n");
            for (int i = 0; i < currentCoordinates.size(); i++) {
                bw.write(String.valueOf(currentCoordinates.get(i)) + " ");
            }
            bw.write("\n");
            bw.write(String.valueOf(unmatchingMove) + "\n");
            bw.write(String.valueOf(gameOver) + "\n");
            bw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * load loads an existing game from file for the player to continue playing.
     */
    public void load() {
        try {
            BufferedReader br = new BufferedReader((new FileReader("files/saved.txt")));
            if (br == null) {
                throw new IllegalArgumentException();
            }
            try {
                for (int i = 0; i < 4; i++) {
                    String[] line = br.readLine().split(" ");
                    for (int j = 0; j < 4; j++) {
                        board[j][i] = Integer.parseInt(line[j]);
                    }
                }
                for (int i = 0; i < 4; i++) {
                    String[] line = br.readLine().split(" ");
                    for (int j = 0; j < 4; j++) {
                        boardState[j][i] = Boolean.parseBoolean(line[j]);
                    }
                }
                oddTurn = Boolean.parseBoolean(br.readLine());
                String[] coord = br.readLine().split(" ");
                for (int j = 0; j < currentCoordinates.size(); j++) {
                    currentCoordinates.set(j, Integer.parseInt(coord[j]));
                }
                unmatchingMove = Boolean.parseBoolean(br.readLine());
                gameOver = Boolean.parseBoolean(br.readLine());
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            RunMatching.fileNotFound();
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[4][4]; // value of integers
        for (int i = 1; i <= 8; i++) {
            int x = 0;
            while (x < 2) {
                Random rand = new Random();
                int randRow = rand.nextInt(4);
                int randCol = rand.nextInt(4);
                if (board[randRow][randCol] == 0) {
                    board[randRow][randCol] = i;
                    x++;
                }
            }
        }
        boardState = new boolean[4][4];
        for (int j = 0; j <= 3; j++) {
            for (int k = 0; k <= 3; k++) {
                boardState[j][k] = false;
            }
        }
        oddTurn = true;
        currentCoordinates = new LinkedList<Integer>();
        currentCoordinates.add(0);
        currentCoordinates.add(0);
        currentCoordinates.add(0);
        currentCoordinates.add(0);
        unmatchingMove = false;
        gameOver = false;
    }

    /**
     * undo returns the game to the state of its previous move
     */
    public void undo() {

        if (oddTurn) {
            boardState[currentCoordinates.get(0)][currentCoordinates.get(1)] = true;
            boardState[currentCoordinates.get(2)][currentCoordinates.get(3)] = false;
            currentCoordinates.set(2, 0);
            currentCoordinates.set(3, 0);
            oddTurn = false;
            unmatchingMove = false;
            gameOver = false;
        } else {
            boardState[currentCoordinates.get(0)][currentCoordinates.get(1)] = false;
            currentCoordinates.removeFirst();
            currentCoordinates.removeFirst();
            currentCoordinates.removeFirst();
            currentCoordinates.removeFirst();
            int oddr = currentCoordinates.get(0);
            int oddc = currentCoordinates.get(1);
            int evenr = currentCoordinates.get(2);
            int evenc = currentCoordinates.get(3);
            oddTurn = true;
            playTurn(oddr, oddc);
            playTurn(evenr, evenc);
            if (!(board[oddr][oddc] == board[evenr][evenc])) {
                currentCoordinates.removeFirst();
                currentCoordinates.removeFirst();
                currentCoordinates.removeFirst();
                currentCoordinates.removeFirst();
            }

            boolean currentCoordinatesAllZero = true;
            for (int i = 0; i < currentCoordinates.size(); i++) {
                if (!(currentCoordinates.get(i) == 0)) {
                    currentCoordinatesAllZero = false;
                }
            }

            if (currentCoordinatesAllZero) {
                boardState = new boolean[4][4];
                for (int j = 0; j <= 3; j++) {
                    for (int k = 0; k <= 3; k++) {
                        boardState[j][k] = false;
                    }
                }
                oddTurn = true;
                currentCoordinates = new LinkedList<Integer>();
                currentCoordinates.add(0);
                currentCoordinates.add(0);
                currentCoordinates.add(0);
                currentCoordinates.add(0);
                unmatchingMove = false;
                gameOver = false;
            }

        }
    }

    // returns the value of that particular card
    public int getCell(int r, int c) {
        return board[r][c];
    }

    // returns whether that card is revealed or not
    public boolean getCellState(int r, int c) {
        return boardState[r][c];
    }

    // returns whether the turn is odd or not
    public boolean isOddTurn() {
        return oddTurn;
    }

    // returns the row of the first of two cards (odd turn)
    public int getOddr() {
        return currentCoordinates.get(0);
    }

    // returns the column of the first of two cards (odd turn)
    public int getOddc() {
        return currentCoordinates.get(1);
    }

    // returns the row of the second of two cards (even turn)
    public int getEvenr() {
        return currentCoordinates.get(2);
    }

    // returns the column of the second of two cards (even turn)
    public int getEvenc() {
        return currentCoordinates.get(3);
    }

    // returns whether the cards are both opened with different values
    public boolean isUnmatchingMove() {
        return unmatchingMove;
    }

    /* **** ****** **** BELOW USED FOR TESTING ONLY **** ****** **** */

    public int[][] getBoard() {
        return board;
    }

    public boolean[][] getBoardState() {
        return boardState;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Matching t = new Matching();
        // t.playTurn(1, 1);
        t.printGameState();
    }
}
