package org.cis120.matching;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunMatching implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Matching Game");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Status Panel");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Top toolbar
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Instructions button
        final JButton instr = new JButton("Info");
        instr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String instr = "Welcome to the Matching Game!\n\nThe goal here is to match pairs of numbers by "
                        +
                        "uncovering (clicking on) two cards at a time!\n" +
                        "The status panel at the bottom will tell you to either to pick the first or second card, "
                        +
                        "or that you won!\nIn this game, if the pair of cards you revealed in that round is correct, "
                        +
                        "they will be revealed forever,\nbut if they are not correct, they will be hidden once again "
                        +
                        "once you start the next round and choose a new card.\nYou win once all the pairs of cards "
                        +
                        "have been found!\n\nThere is also an option to save the current game, load a saved game, or "
                        +
                        "even reset the current game shall you wish.\nWishing you the best of luck! :)";
                JOptionPane.showMessageDialog(
                        null, instr, "Matching Game: Instructions",
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        });
        control_panel.add(instr);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.save();
            }
        });
        control_panel.add(save);

        // Load button
        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.load();
            }
        });
        control_panel.add(load);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();

    }

    // Error window for when file is not found while trying to load a game
    public static void fileNotFound() {
        final JFrame notFoundFrame = new JFrame("Warning");
        notFoundFrame.setLocation(300, 300);
        notFoundFrame.setSize(450, 100);
        notFoundFrame.add(new JLabel("File Not Found!", SwingConstants.CENTER));
        notFoundFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        notFoundFrame.setVisible(true);
    }

}