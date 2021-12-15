package org.cis120.matching;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Matching mg; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    // Game colors
    public static final Color BACKGROUND_COLOR = new Color(222, 242, 241);
    public static final Color BORDER_COLOR = new Color(145, 212, 209);
    public static final Color NUMBER_COLOR = new Color(32, 85, 102);
    public static final Color COVER_COLOR = new Color(118, 176, 173);

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        mg = new Matching(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                mg.playTurn(p.x / 100, p.y / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        mg.reset();
        status.setText("Pick your first card!");
        repaint();
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Saves the game's current state to file.
     */
    public void save() {
        mg.save();
    }

    /**
     * Loads a saved game state from file and replaces the existing state of a game
     * (if any).
     */
    public void load() {
        mg.load();
        updateStatus();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Returns the game to the state of its previous move.
     */
    public void undo() {
        mg.undo();
        updateStatus();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (mg.hasWon()) {
            status.setText("Congratulations! All pairs have been found!");
        } else if (mg.isOddTurn()) {
            status.setText("Pick your first card!");
        } else {
            status.setText("Pick your second card!");
        }
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Sets up the background
        // super.paintComponent(g);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draws board grid
        g.setColor(BORDER_COLOR);
        for (int i = 100; i < 400; i = i + 100) {
            g.drawLine(i, 0, i, 400);
            g.drawLine(0, i, 400, i);
        }

        final int size = g.getFont().getSize();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                // Draw board values
                int state = mg.getCell(i, j);
                g.setColor(NUMBER_COLOR);
                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), size * 2));
                g.drawString(Integer.toString(state), i * 100 + 40, j * 100 + 60);

                // Draw covers
                boolean revealed = mg.getCellState(i, j);
                if (mg.isUnmatchingMove() && (i == mg.getOddr() && j == mg.getOddc())) {
                    // System.out.println(i + " " + j + ", Odd Move, Value = " + mg.getCell(i, j));
                } else if (mg.isUnmatchingMove() && (i == mg.getEvenr() && j == mg.getEvenc())) {
                    // System.out.println(i + " " + j + ", Even Move, Value = " + mg.getCell(i, j));
                } else if (!revealed) {
                    g.setColor(COVER_COLOR);
                    g.fillRect(i * 100 + 3, j * 100 + 3, 94, 94);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
