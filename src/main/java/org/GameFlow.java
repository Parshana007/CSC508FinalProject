package org;

public class GameFlow {
    private Phase phase;
    private String turn; // ME or OPP
    private boolean opponentReadyRoom = false;
    private boolean opponentReadyPlacement = false;

    public GameFlow() {

    }

    public void setPhase(Phase phase) {
        this.phase = phase;
        Blackboard.getInstance().firePropertyChange("phase", null, this.phase);
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

    public void setOpponentReadyRoom(boolean opponentReadyRoom) {
        this.opponentReadyRoom = opponentReadyRoom;
//        this.setPhase(Phase.PLACEMENT);
    }

    public boolean isOpponentReadyRoom() {
        return opponentReadyRoom;
    }

    public void setOpponentReadyPlacement(boolean opponentReadyPlacement) {
        this.opponentReadyPlacement = opponentReadyPlacement;
    }

    public boolean isOpponentReadyPlacement() {
        return opponentReadyPlacement;
    }

}
