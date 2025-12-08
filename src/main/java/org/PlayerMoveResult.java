package org;

import java.util.ArrayList;
import java.util.List;

public class PlayerMoveResult {
    private MoveResult result;
    private String coordinate;
    private List<String> sunkShipCoords;

    public PlayerMoveResult(MoveResult result, String coordinate) {
        this(result, coordinate, new ArrayList<>());
    }

    public PlayerMoveResult(MoveResult result, String coordinate, List<String> sunkShipCoords) {
        this.result = result;
        this.coordinate = coordinate;
        this.sunkShipCoords = sunkShipCoords;
    }

    public MoveResult getResult() { return result; }
    public String getCoordinate() { return coordinate; }
    public List<String> getSunkShipCoords() { return sunkShipCoords; }
}
