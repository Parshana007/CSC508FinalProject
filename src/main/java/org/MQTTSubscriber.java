package org;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Arrays;
import java.util.List;

public class MQTTSubscriber implements MqttCallback {


    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("MQTTSubscriber received: " + payload);

        String[] parts = payload.split(":");

        if (parts.length == 2 && parts[0].equals("GUESS")) {
            String coord = parts[1];
            Blackboard.getInstance().checkOppGuess(coord);
        }

        else if (parts[0].equals("RESULT")) {
            MoveResult result = MoveResult.valueOf(parts[1]);
            String coord = parts[2];

            if (result == MoveResult.SUNK && parts.length == 4) {
                String[] shipCoords = parts[3].split(",");
                Blackboard.getInstance().addMyGuessResult(result, coord, Arrays.asList(shipCoords));
            } else {
                Blackboard.getInstance().addMyGuessResult(result, coord, List.of());
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
