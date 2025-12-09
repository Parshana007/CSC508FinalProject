package org;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.UUID;

public class Blackboard extends PropertyChangeSupport {
    private static Blackboard instance;
    private PlayerState playerState;
    private OpponentState opponentState;
    private GameFlow gameFlow;
    private String myPlayer; //random generated String
    private boolean gameOver = false;
    private int totalShips = 6;

    private Blackboard() {
        super(new Object());
        this.playerState = new PlayerState();
        this.opponentState = new OpponentState();
        this.gameFlow = new GameFlow();
        this.myPlayer = UUID.randomUUID().toString();
    }

    public static synchronized Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public String getMyPlayer() {
        return myPlayer;
    }

    public int getTotalShips() {
        return totalShips;
    }

//    public void setMyPlayer(String myPlayer) {
//        this.myPlayer = myPlayer;
//    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void addMyGuess(Point coordinate) {
        firePropertyChange("myGuess", null, coordinate);
    }


    public void checkOppGuess(Point coordinate) {
        PlayerMoveResult result = this.playerState.addHitMiss(coordinate);

        firePropertyChange("guessResult", null, result);
        // Check if I lost
        if (this.playerState.allShipsSunk()) {
            this.gameFlow.setPhase(Phase.LOSTGAME);
            firePropertyChange("phase", null, Phase.LOSTGAME);
        }
    }

    public void addMyGuessResult(MoveResult result, Point coordinate, List<Point> sunkCoords) {
        switch (result) {
            case HIT -> this.opponentState.addHit(coordinate);
            case MISS -> this.opponentState.addMiss(coordinate);
            case SUNK -> this.opponentState.addSunk(sunkCoords);
        }

        // Check if I won
        if (this.opponentState.allShipsSunk()) {
//            firePropertyChange("gameOver", null, "me");
            this.gameFlow.setPhase(Phase.WONGAME);
            firePropertyChange("phase", null, Phase.WONGAME);
        }

    }


    public OpponentState getOpponentState() {
        return opponentState;
    }

    public GameFlow getGameFlow() {
        return gameFlow;
    }

}
