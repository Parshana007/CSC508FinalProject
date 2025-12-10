package org;

/**
 * Tracks the current phase of the game; keeps players in sync.
 */

public class GameFlow {
    private Phase phase;
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

    public void setOpponentReadyRoom(boolean opponentReadyRoom) {
        this.opponentReadyRoom = opponentReadyRoom;
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
