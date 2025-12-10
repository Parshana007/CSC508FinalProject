package org;

/**
 * Represents the possible results of a player's guess on the opponent's grid.
 */

public enum MoveResult {
    HIT,   // The shot successfully hit a ship.
    MISS,  // The shot landed in the water, missed all ships.
    SUNK   // The shot hit a ship, and that ship is now sunk.
}
