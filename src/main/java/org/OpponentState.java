package org;

import java.util.ArrayList;
import java.util.List;


public class OpponentState {

    private HitMiss myHitMiss;

    public OpponentState() {
        myHitMiss = new HitMiss();
    }

    public void addHit(String coordinate) {
        this.myHitMiss.addHit(coordinate);
    }

    public void addMiss(String coordinate) {
        this.myHitMiss.addMiss(coordinate);
    }

    public void addSunk(List<String> coordinates) {
        Ship ship = new Ship(coordinates);
        this.myHitMiss.addSunkShip(ship);
    }


}

