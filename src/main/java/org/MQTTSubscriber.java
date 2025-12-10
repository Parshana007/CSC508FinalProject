package org;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.beans.PropertyChangeEvent;

public class MQTTSubscriber implements MqttCallback, PropertyChangeListener {
    private String CLIENT_ID = "brokerverse-subscriber-";
    private MqttClient client;

    public MQTTSubscriber() {
        CLIENT_ID = CLIENT_ID + UUID.randomUUID();
        try {
            client = new MqttClient(Blackboard.getInstance().getBroker(), CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(true);
            opts.setCleanSession(true);
            client.connect(opts);
            client.setCallback(this);

//            client.subscribe(Blackboard.getInstance().getRoomID() + "/#");

//            String roomTopic = Blackboard.getInstance().getTopic() + Blackboard.getInstance().getRoomID();
//            client.subscribe(roomTopic + "/#");
//            System.out.println("Subscribed to: " + roomTopic + "/#");
            System.out.println("Subscriber connected: " + Blackboard.getInstance().getBroker());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private Point decodePoint(String s) {
        String[] parts = s.split(",");
        return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public void subscribeToRoom() {
        System.out.println("Trying to subscribe, connected=" + client.isConnected());
        String roomID = Blackboard.getInstance().getRoomID();

        System.out.println("Trying to subscribe, connected=" + roomID);

        if (roomID == null || roomID.isEmpty()) return;

        String roomTopic = Blackboard.getInstance().getTopic() + roomID; // e.g., "calpoly/csc509/brokerverse/12"

        try {
            System.out.println("Trying to subscribe, connected=" + client.isConnected());

            if (!client.isConnected()) return;

            client.subscribe(roomTopic + "/#"); // subscribe to all subtopics of this room
            System.out.println("Subscribed to: " + roomTopic + "/#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChange: " + evt.getPropertyName());
        if (!"roomID".equals(evt.getPropertyName())) return;
        // Room code was just set, subscribe now
        subscribeToRoom();
// Small delay to ensure subscription
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Blackboard.getInstance().firePropertyChange("roomJoined", null, true);
    }



    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("MQTTSubscriber received: " + payload);

        String[] parts = payload.split(":");
        System.out.println("Myplayer: " + Blackboard.getInstance().getMyPlayer());
        System.out.println("topic here " + topic);

        String myPlayerStr = String.valueOf(Blackboard.getInstance().getMyPlayer());
        System.out.println(topic);
        System.out.println(myPlayerStr);
        System.out.println(topic.trim().contains(myPlayerStr));

        String lastPart = topic.substring(topic.lastIndexOf("/") + 1);
        System.out.println(lastPart); // Output: 1

        if (!lastPart.contains(myPlayerStr)) { // TO ENSURE I AM NOT READING MY OWN MSGS
            System.out.println("IN IF");

            if (parts.length == 2 && parts[0].equals("GUESS")) {
                Point coord = decodePoint(parts[1]);
                // Calculate hit/miss and send result
                Blackboard.getInstance().checkOppGuess(coord);
                // Switch opponent's phase to GUESSING for this player
                Blackboard.getInstance().getGameFlow().setPhase(Phase.GUESSING);
            } else if (parts[0].equals("RESULT")) {
                MoveResult result = MoveResult.valueOf(parts[1]);

                if (result == MoveResult.SUNK && parts.length == 4) {
                    String[] shipCoordStrings = parts[3].split(";");
                    List<Point> pts = Arrays.stream(shipCoordStrings)
                            .map(this::decodePoint)
                            .toList();
                    Blackboard.getInstance().addMyGuessResult(result, decodePoint(parts[2]), pts);
                } else {
                    Blackboard.getInstance().addMyGuessResult(result, decodePoint(parts[2]), List.of());
                }
            } else if (parts[0].equals("SHIPS_PLACED")) {
                Blackboard.getInstance().getGameFlow().setOpponentReadyPlacement(true);
                if (Blackboard.getInstance().getGameFlow().getPhase().equals(Phase.WAITFOROPPONENTPLACEMENT)) {
                    Blackboard.getInstance().getGameFlow().setPhase(Phase.GUESSING);
                }
            }
            else if (parts[0].equals("JOINED_ROOM")) {
//                System.out.println("SUBSCRIBED TO: " + Blackboard.getInstance().getRoomID() + "/#");
//                System.out.println("TOPIC = " + topic + ", PAYLOAD = " + payload);
                Blackboard.getInstance().getGameFlow().setOpponentReadyRoom(true);
                Blackboard.getInstance().getGameFlow().setPhase(Phase.PLACEMENT);
                System.out.println("Changed to phase: " + Blackboard.getInstance().getGameFlow().getPhase());
//                }
            }
            else if (parts[0].equals("GAME_OVER")) {
                // Opponent just won
                if (parts[1].equals("opponentWon")) {
                    Blackboard.getInstance().getGameFlow().setPhase(Phase.LOSTGAME);
                }
            }

        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }
}
