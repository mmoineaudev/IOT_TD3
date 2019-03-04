package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class LightSensor extends AbstractSensor implements SensorTrigger{

    public LightSensor() throws MqttException {
        super();
        this.client = new MqttClient(broker, clientId, persistence);
        this.topic = super.topic+"light";
    }

    @Override
    public void connect() throws MqttException, InterruptedException {
        super.connect();
    }

    @Override
    public void publish() throws MqttException {
        super.publish();
    }

    public void triggered(MqttMessage value) {
        System.out.println("value = " + value);
        if(value!=null)
            if(value.toString()=="ON"){
                //TODO
            }else if(value.toString()=="OFF"){
                //TODO
            }
    }
}
