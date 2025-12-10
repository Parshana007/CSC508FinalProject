package org;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.stream.Collectors;

public class MQTTPublisher implements PropertyChangeListener {
    private String broker;

    private final static String CLIENT_ID = "brokerverse-publisher-" + System.currentTimeMillis();

    private MqttClient client;

    public MQTTPublisher() {
        this.broker = Blackboard.getInstance().getBroker();
        new Thread(this::connectClient).start();
    }

    public void connectClient() {
        try {
            client = new MqttClient(broker, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(true);
            opts.setCleanSession(true);
            client.connect();
            System.out.println("Publisher connected: " + Blackboard.getInstance().getBroker());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private String encodePoint(Point p) {
        return p.x + "," + p.y;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (!client.isConnected()) return;

            if (evt.getPropertyName().equals("roomJoined")) {
                String msg = "JOINED_ROOM";
//                System.out.println("PUBLISH TO: " + Blackboard.getInstance().getFullTopic());
                // retains past messages
                MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
                mqttMessage.setRetained(true);
                client.publish(Blackboard.getInstance().getFullTopic(), mqttMessage);
            }

            // 1. Player is guessing a coordinate
            else if (evt.getPropertyName().equals("myGuess")) {
                Point coord = (Point) evt.getNewValue();
                String msg = "GUESS:" + encodePoint(coord);
                client.publish(Blackboard.getInstance().getFullTopic(), new MqttMessage(msg.getBytes()));
            }

            // 2. Player responding to opponent guess
            else if (evt.getPropertyName().equals("guessResult")) {
                PlayerMoveResult result = (PlayerMoveResult) evt.getNewValue();

                String msg = "RESULT:" + result.getResult() + ":" +
                        encodePoint(result.getCoordinate());

                // If SUNK, append all coordinates
                if (result.getResult() == MoveResult.SUNK) {
                    msg += ":" + result.getSunkShipCoords()
                            .stream()
                            .map(this::encodePoint)
                            .collect(Collectors.joining(";"));
                }

                client.publish(Blackboard.getInstance().getFullTopic(), new MqttMessage(msg.getBytes()));
            }

            // 3. Player has finished placing their ships
            else if (evt.getPropertyName().equals("shipsPlaced")) {
                String msg = "SHIPS_PLACED";
                client.publish(Blackboard.getInstance().getFullTopic(), new MqttMessage(msg.getBytes()));
            }

            else if (evt.getPropertyName().equals("gameOver")) {
                String msg = "GAME_OVER:" + evt.getNewValue();
                client.publish(Blackboard.getInstance().getFullTopic(), new MqttMessage(msg.getBytes()));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
