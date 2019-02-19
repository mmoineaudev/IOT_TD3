package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;

public class TempSensor extends AbstractSensor implements SensorTrigger{
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
            if(Double.parseDouble(value)<10){
                //TODO
            }else if(Double.parseDouble(value)>=10){
                //TODO
            }
    }
}
