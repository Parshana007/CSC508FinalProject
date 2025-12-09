package org;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class OpponentState {
    private HitMiss myHitMiss;  // my guess of opp ships

    public OpponentState() {
        myHitMiss = new HitMiss();
    }

    public void addHit(Point coordinate) {
        myHitMiss.addHit(coordinate);
    }

    public List<Point> getHits() {
        return myHitMiss.getHits();
    }

    public void addMiss(Point coordinate) {
        myHitMiss.addMiss(coordinate);
    }

    public List<Point> getMisses() {
        return myHitMiss.getMisses();
    }

    public void addSunk(List<Point> coordinates) {
        Ship ship = new Ship(coordinates);
        myHitMiss.addSunkShip(ship);
    }

    public List<Ship> getSunkShips() {
        return myHitMiss.getSunkShips();
    }

}

