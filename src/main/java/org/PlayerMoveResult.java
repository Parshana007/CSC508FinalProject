package org;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Represents the possible results of a player's guess. Used to update the grid accordingly.
 */

public class PlayerMoveResult {
    private MoveResult result;
    private Point coordinate;
    private List<Point> sunkShipCoords;

    public PlayerMoveResult(MoveResult result, Point coordinate) {
        this(result, coordinate, new ArrayList<>());
    }

    public PlayerMoveResult(MoveResult result, Point coordinate, List<Point> sunkShipCoords) {
        this.result = result;
        this.coordinate = coordinate;
        this.sunkShipCoords = sunkShipCoords;
    }

    public MoveResult getResult() { return result; }
    public Point getCoordinate() { return coordinate; }
    public List<Point> getSunkShipCoords() { return sunkShipCoords; }
}
