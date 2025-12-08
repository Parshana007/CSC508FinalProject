package org;

import java.beans.PropertyChangeSupport;
import java.util.List;


public class Blackboard extends PropertyChangeSupport {
    private static Blackboard instance;
    private PlayerState playerState;
    private OpponentState opponentState;
    private GameFlow gameFlow;

    private Blackboard() {
        super(new Object());
        this.playerState = new PlayerState();
        this.opponentState = new OpponentState();
        this.gameFlow = new GameFlow();
    }

    public static synchronized Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void addMyGuess(String coordinate) {
        firePropertyChange("myGuess", null, coordinate);
    }

    public void checkOppGuess(String coordinate) {
        PlayerMoveResult result = this.playerState.addHitMiss(coordinate);

        firePropertyChange("guessResult", null, result);
    }

    public void addMyGuessResult(MoveResult result, String coordinate, List<String> sunkCoords) {
        switch (result) {
            case HIT -> this.opponentState.addHit(coordinate);
            case MISS -> this.opponentState.addMiss(coordinate);
            case SUNK -> this.opponentState.addSunk(sunkCoords);
        }
    }


    public OpponentState getOpponentState() {
        return opponentState;
    }

    public GameFlow getGameFlow() {
        return gameFlow;
    }

}
