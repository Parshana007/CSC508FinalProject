package org;

public class GameFlow {
    private Phase phase;
    private String turn; // ME or OPP
    private boolean opponentReady = false;

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

    public void setOpponentReady(boolean opponentReady) {
        this.opponentReady = opponentReady;
//        this.setPhase(Phase.PLACEMENT);
    }

    public boolean isOpponentReady() {
        return opponentReady;
    }

}
