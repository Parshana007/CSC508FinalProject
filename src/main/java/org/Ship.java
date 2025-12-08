package org;

import java.util.List;

public class Ship {
    List<String> coordinates;

    public Ship(List<String> coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(List<String> coordinates) {
        this.coordinates = coordinates;
    }
}
