package org;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        System.out.println("this opponent hits: " + this.opponentState.getHits());
        System.out.println("this opponent misses: " + this.opponentState.getMisses());

        firePropertyChange("opponentStateUpdated", null, result);

        // Check if I won
        if (this.opponentState.allShipsSunk()) {
//            firePropertyChange("gameOver", null, "me");
            this.gameFlow.setPhase(Phase.WONGAME);
            firePropertyChange("phase", null, Phase.WONGAME);
            firePropertyChange("gameOver", null, "opponentWon");
        }

    }

    public void initializeShips() {
        List<Ship> ships = getPlayerState().getMyShips();
        if (!ships.isEmpty()) {
            return;
        }

        ships = new ArrayList<>();

        List<Point> ship1Points = new ArrayList<>();
        ship1Points.add(new Point(1, 1));
        ship1Points.add(new Point(2, 1));
        ship1Points.add(new Point(3, 1));
        ship1Points.add(new Point(4, 1));
        ship1Points.add(new Point(5, 1));
        ships.add(new Ship(ship1Points));

        List<Point> ship2Points = new ArrayList<>();
        ship2Points.add(new Point(2, 3));
        ship2Points.add(new Point(2, 4));
        ship2Points.add(new Point(2, 5));
        ship2Points.add(new Point(2, 6));
        ships.add(new Ship(ship2Points));

        List<Point> ship3Points = new ArrayList<>();
        ship3Points.add(new Point(4, 3));
        ship3Points.add(new Point(5, 3));
        ship3Points.add(new Point(6, 3));
        ships.add(new Ship(ship3Points));

        List<Point> ship4Points = new ArrayList<>();
        ship4Points.add(new Point(7, 5));
        ship4Points.add(new Point(7, 6));
        ships.add(new Ship(ship4Points));

        List<Point> ship5Points = new ArrayList<>();
        ship5Points.add(new Point(5, 5));
        ships.add(new Ship(ship5Points));

        getPlayerState().setMyShips(ships);
    }


    public void submitShipsPlacement() {
        firePropertyChange("shipsPlaced", null, null);
        System.out.println("Ships Placed" + this.playerState.getMyShips());
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

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getBroker() {
        return broker;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getFullTopic() {
        return topic + roomID + "/" + myPlayer;
    }

}
