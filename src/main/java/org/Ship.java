package org;

import java.awt.*;
import java.util.List;

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
