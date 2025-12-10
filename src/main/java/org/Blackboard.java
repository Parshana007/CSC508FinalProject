package org;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.UUID;

/**
 *  Blackboard is a singleton that holds the state of both players, the flow of the game, and the room ID used
 *  for connecting to the broker.
 */

public class Blackboard extends PropertyChangeSupport {
    private static Blackboard instance;
    private PlayerState playerState;
    private OpponentState opponentState;
    private GameFlow gameFlow;
    private String myPlayer; //random generated String
    private String roomID;
    private String broker = "tcp://broker.hivemq.com:1883";
    private String topic = "calpoly/csc509/brokerverse/";
    private int totalShips = 5;

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

    public void setMyPlayer(String myPlayer) {
        this.myPlayer = myPlayer.replaceAll(" ", "_");
    }

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

        firePropertyChange("opponentStateUpdated", null, result);

        // Check if I won
        if (this.opponentState.allShipsSunk()) {
            this.gameFlow.setPhase(Phase.WONGAME);
            firePropertyChange("phase", null, Phase.WONGAME);
            firePropertyChange("gameOver", null, "opponentWon");
        }

    }

    public void submitShipsPlacement() {
        firePropertyChange("shipsPlaced", null, null);
    }


    public OpponentState getOpponentState() {
        return opponentState;
    }

    public GameFlow getGameFlow() {
        return gameFlow;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
        Blackboard.getInstance().firePropertyChange("roomID", null, roomID);
    }

    public String getRoomID() {
        return roomID;
    }

    public String getBroker() {
        return broker;
    }

    public String getTopic() {
        return topic;
    }

    public String getFullTopic() {
        return topic + roomID + "/" + myPlayer;
    }

}
