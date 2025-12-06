package org;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTSubscriber implements MqttCallback {


    @Override
    public void messageArrived(String s, MqttMessage message) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }
}
