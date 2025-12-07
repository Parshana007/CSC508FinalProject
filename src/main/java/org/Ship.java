package org;

import java.awt.*;
import java.util.List;

public class Ship {
    // when this is empty, the ship is sunk
    List<Point> remainingSquares;

    public Ship(List<Point> coords) {
        this.remainingSquares = coords;
    }
}
