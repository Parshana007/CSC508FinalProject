package org;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PlayerState {

    private List<Ship> myShips;
    private HitMiss myHitMiss; //the opponents guesses of my ships

    public PlayerState() {
        myShips = new ArrayList<>();
        myHitMiss = new HitMiss();
    }

    public List<Ship> getMyShips() {
        return myShips;
    }

    public void setMyShips(List<Ship> ships) {
        myShips = ships;
    }

    public void addShip(Ship ship) {
        this.myShips.add(ship);
    }

    // Opponent guesses a coordinate player decides if hit/miss/sunk
    public PlayerMoveResult addHitMiss(Point coordinate) {
        for (Ship ship : this.myShips) {
            if (ship.getCoordinates().contains(coordinate)) {
                this.myHitMiss.addHit(coordinate);
                MoveResult r = checkSunk(ship);

                if (r == MoveResult.SUNK) {
                    return new PlayerMoveResult(
                            MoveResult.SUNK,
                            coordinate,
                            ship.getCoordinates()
                    );
                }

                return new PlayerMoveResult(MoveResult.HIT, coordinate);
            }
        }
        this.myHitMiss.addMiss(coordinate);
        return new PlayerMoveResult(MoveResult.MISS, coordinate);
    }

    public MoveResult checkSunk(Ship ship) {
        int shipSize = ship.getCoordinates().size();
        int counter = 0;

        for (Point hit: this.myHitMiss.getHits()) {
            if (ship.getCoordinates().contains(hit)) {
                counter++;
            }
        }

        if (counter == shipSize) {
            this.myHitMiss.addSunkShip(ship);
            return MoveResult.SUNK;
        }
        return MoveResult.HIT;
    }

    //TODO check if myships is the total amount of ships placed
    public boolean allShipsSunk() {
        return this.myHitMiss.getSunkShips().size() == Blackboard.getInstance().getTotalShips();
    }

}
