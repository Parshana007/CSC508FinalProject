package org;

import java.util.ArrayList;
import java.util.List;

public class HitMiss {
    private List<String> hits;
    private List<String> misses;
    private List<Ship> sunkShips;

    public HitMiss() {
        this.hits = new ArrayList<String>();
        this.misses = new ArrayList<String>();
        this.sunkShips = new ArrayList<>();
    }

    public void addHit(String coordinate) {
        this.hits.add(coordinate);
    }

    public List<String> getHits() {
        return hits;
    }

    public void addMiss(String coordinate) {
        this.misses.add(coordinate);
    }

    public List<String> getMisses() {
        return misses;
    }

    public void addSunkShip(Ship ship) {
        this.sunkShips.add(ship);
    }

    public List<Ship> getSunkShips() {
        return this.sunkShips;
    }
}
