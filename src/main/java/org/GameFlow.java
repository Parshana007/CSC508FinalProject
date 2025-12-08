package org;

public class GameFlow {
    private String roomID;
    private Phase phase;
    private String turn; // ME or OPP

    public GameFlow() {

    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getTurn() {
        return turn;
    }

}
