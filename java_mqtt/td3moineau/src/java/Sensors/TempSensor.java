package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TempSensor extends AbstractSensor implements SensorTrigger{
    public TempSensor() throws MqttException {
        super();
        this.topic = super.topic+"temp";
        System.out.println("topic = " + topic);
        this.client = new MqttClient(broker, clientId, persistence);
    }

    @Override
    public void connect() throws MqttException {
        super.connect();
    }

    @Override
    public void publish() throws MqttException {
        super.publish();
    }

    public void triggered(MqttMessage value) {
        System.out.println("value = " + value);
        if(value!=null)
            if(Double.parseDouble(value.toString())<10){
                //TODO
            }else if(Double.parseDouble(value.toString())>=10){
                //TODO
            }
    }
}
