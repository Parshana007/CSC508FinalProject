package org;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MQTTSubscriber implements MqttCallback {
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
            client.subscribe(Blackboard.getInstance().getRoomID() + "/#");
            System.out.println("Subscriber connected: " + Blackboard.getInstance().getBroker());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private Point decodePoint(String s) {
        String[] parts = s.split(",");
        return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("MQTTSubscriber received: " + payload);

        String[] parts = payload.split(":");

        if (!topic.contains(Blackboard.getInstance().getMyPlayer())) { // TO ENSURE I AM NOT READING MY OWN MSGS
            if (parts.length == 2 && parts[0].equals("GUESS")) {
                Point coord = decodePoint(parts[1]);
                Blackboard.getInstance().checkOppGuess(coord);
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
                Blackboard.getInstance().getGameFlow().setOpponentReady(true);
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
