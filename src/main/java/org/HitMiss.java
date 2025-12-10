package org;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Represents one player's set of guesses on the other player's grid.
 */

public class HitMiss {
    private List<Point> hits;
    private List<Point> misses;
    private List<Ship> sunkShips;

    public HitMiss() {
        this.hits = new ArrayList<Point>();
        this.misses = new ArrayList<Point>();
        this.sunkShips = new ArrayList<>();
    }

    public void addHit(Point point) { hits.add(point); }
    public List<Point> getHits() { return hits; }

    public void addMiss(Point point) { misses.add(point); }
    public List<Point> getMisses() { return misses; }

    public void addSunkShip(Ship ship) { sunkShips.add(ship); }
    public List<Ship> getSunkShips() { return sunkShips; }
}
