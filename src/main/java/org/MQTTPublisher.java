package org;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.stream.Collectors;

public class MQTTPublisher implements PropertyChangeListener {
    private String broker = "tcp://test.mosquitto.org:1883";
    private String topic;
    private final static String CLIENT_ID = "jgs-subscriber-" + System.currentTimeMillis();

    private MqttClient client;

    public MQTTPublisher(String broker, String topic) {
        this.broker = broker;
        this.topic = topic;
        new Thread(this::connectClient).start();
    }

    public void connectClient() {
        try {
            client = new MqttClient(broker, CLIENT_ID, new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // RoomID
    public void setTopic(String topic) {
        this.topic = topic;
    }

    private String encodePoint(Point p) {
        return p.x + "," + p.y;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (!client.isConnected()) return;

            // 1. Player is guessing a coordinate
            if (evt.getPropertyName().equals("myGuess")) {
                Point coord = (Point) evt.getNewValue();
                String msg = "GUESS:" + encodePoint(coord);
                // TODO: HERE WE NEED TOPIC TO CONTAIN THE ROOM CODE IN THE BLACKBOARD
                client.publish(topic + Blackboard.getInstance().getMyPlayer(), new MqttMessage(msg.getBytes()));
            }

            // 2. Player responding to opponent guess
            else if (evt.getPropertyName().equals("guessResult")) {
                PlayerMoveResult result = (PlayerMoveResult) evt.getNewValue();

                String msg = "RESULT:" + result.getResult() + ":" + result.getCoordinate();

                // If SUNK, append all coordinates
                if (result.getResult() == MoveResult.SUNK) {
                    msg += ":" + result.getSunkShipCoords()
                            .stream()
                            .map(this::encodePoint)
                            .collect(Collectors.joining(";"));
                }

                client.publish(topic + Blackboard.getInstance().getMyPlayer(), new MqttMessage(msg.getBytes()));
            }

            // 3. Player has finished placing their ships
            else if (evt.getPropertyName().equals("shipsPlaced")) {
                String msg = "SHIPS_PLACED";
                client.publish(topic + Blackboard.getInstance().getMyPlayer(), new MqttMessage(msg.getBytes()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
