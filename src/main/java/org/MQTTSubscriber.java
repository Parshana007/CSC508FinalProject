package org;

import org.eclipse.paho.client.mqttv3.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MQTTSubscriber implements MqttCallback {
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
