package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;

public class LedSensor extends AbstractSensor implements SensorTrigger{

    @Override
    public void connect() throws MqttException {
        super.topic = super.topic+"led";
        super.connect();
    }

    @Override
    public void publish() throws MqttException {
        super.publish();
    }

    public void triggered(String value) {
        if(value!=null)
            if(value=="ON"){
                //TODO
            }else if(value=="OFF"){
                //TODO
            }
    }
}
