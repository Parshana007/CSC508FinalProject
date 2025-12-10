package org;

/**
 * Represents the possible phases of the game. Used by ScreenManager to update the screen accordingly.
 */

public enum Phase {
    WAITFOROPPONENTROOMID, // waiting for opponent to join the room
    WAITFOROPPONENTPLACEMENT, // waiting for opponent to submit their ship placements
    PLACEMENT, // placing my ships on a grid
    GUESSING, // my turn to place guesses of where I think opponent's ships are
    WAITFOROPPONENTGUESS, // opponent's turn to place guesses of where my ships are
    WONGAME, // I won
    LOSTGAME // I lost
}
