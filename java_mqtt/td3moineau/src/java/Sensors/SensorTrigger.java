package Sensors;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface SensorTrigger {
    public void triggered(MqttMessage value);
}
