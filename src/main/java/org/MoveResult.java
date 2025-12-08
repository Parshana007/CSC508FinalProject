package org;

public enum MoveResult {
    HIT,   // The shot successfully hit a ship.
    MISS,  // The shot landed in the water, missed all ships.
    SUNK   // The shot hit a ship, and that ship is now sunk.
}
