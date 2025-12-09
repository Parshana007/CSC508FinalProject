package org;

public enum Phase {
   PLACEMENT, // placing the ships on a grid
    GUESSING, // placing guesses on where opponents ships are
    WAITFOROPPONENTROOMID,
    // waiting to enter room code opp, waiting for placement opp, waiting for guess opp
    WAITFOROPPONENTPLACEMENT,
    WAITFOROPPONENTGUESS
}
