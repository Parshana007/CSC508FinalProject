package org;

import java.awt.*;
import java.util.List;

/**
 *  Represents a ship occupied by a set of points on the grid.
 */

public class Ship {
    List<Point> coordinates;

    public Ship(List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Point> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<Point> coordinates) {
        this.coordinates = coordinates;
    }
}
