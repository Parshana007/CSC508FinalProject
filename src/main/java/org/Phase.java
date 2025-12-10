package org;

public enum Phase {
   PLACEMENT, // placing the ships on a grid
    GUESSING, // my turn to place guesses of where I think shuips are
    WAITFOROPPONENTROOMID,
    WAITFOROPPONENTPLACEMENT,
    WAITFOROPPONENTGUESS, // opponents turn to place guesses not my turn
    WONGAME,
    LOSTGAME
}
