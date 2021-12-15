=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: kimfung
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D array - one 2d array of integers storing the values of the squares (from 1 - 8)
                this is appropriate because all the values of the squares are integers

                one 2d array of booleans storing whether they have been revealed or not (true for revealed)
                this is appropriate because all the squares can only either be revealed or not

  2. collection (linkedlist) - stores the coordinates of the two cards being dealt with along with the history of
                                all the coordinates of cards from previous rounds (for undo functionality)

                                this is appropriate because the set method allows for easy replacement without
                                having to add/delete, and allows the size to increase as the game moves on

  3. JUnit testing -  test if anything that is involved with playTurn updates correctly, including multiple edge cases
                      for when the user tries to select a square that has already been selected before, as well as the
                      game's initial state such as the board size, range, and frequency of values (2 for each)

                      this is appropriate because it ensures that the logic of the game model is appropriate and that
                      the model is working properly, independent of the GUI

  4. File I/O - store game state, so that the user can pause a game and come back later shall they wish.
                will be saving the state of all instance variables in a text file when the save button is clicked.
                when they come back, the game can just simply read the file and parse the data to load and display the
                existing game. when a file isn't found, a window pops up to say that a file isn't found.

                this is appropriate because file i/o can help store and retrieve game state even when the game is
                closed and the user returns another time to continue playing.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  RunMatching contains the run() method and mainly deals with JFrames and JPanels displayed on the screen.
  It also starts the game, and contains a function that displays an error window for later on when the file to load
  a game from is not found. this class sets up the top-level frame and widgets for the GUI.

  Matching contains most of the methods of the game, including playTurn() which decides what happens in every round,
  hasWon() which decides whether the user wins, save() which saves the game to file, load() which loads a game from
  file, reset() which resets the current game, and undo() which returns the game to the state of its previous move.
  this class is the model for the matching game in the model-view-controller design framework.

  GameBoard instantiates a Matching object (model of the game), and as the user clicks on the board, the model gets
  updated, which repaints the board itself and updates its status JLabel to reflect the game state at a given time.
  It also repaints the board during reset, load, and undo.

  GameTest is where all the JUnit testing happens to test the game model!

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I found implementing the undo functionality the most challenging because it required a lot of testing with both the
  game logic and to make sure the GUI updates to the undo functionality appropriately.

  I was also just unfamiliar with Swing and the model-view-controller design framework in general which took me a while
  to get my head across.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think my design was well implemented because it follows the model-view-controller design framework.
  Apart from the GameTest class, there is a class for each of the model, view, and controller accordingly and that
  the private is encapsulated because there are no methods which would modify any of the contents of the private
  variables, and that all the instance variables in the game model are private.

  I would refactor by making the game even more efficient through better logic and work on organization with helper
  functions and use of loops. I would also introduce a feature that allows multiple files to be saved and have the user
  being able to choose which of those games to load, with each of them named separately.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  Implementing Random Numbers - https://www.geeksforgeeks.org/generating-random-numbers-in-java/
  LinkedList - https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
  Collection.frequency - https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
  Integer.parseInt - https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html
  Boolean.parseBoolean - https://docs.oracle.com/javase/7/docs/api/java/lang/Boolean.html
  Color(int r, int g, int b) - https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
  JOptionPane.showMessageDialog - https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html

