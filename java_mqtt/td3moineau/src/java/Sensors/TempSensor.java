package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TempSensor extends AbstractSensor implements SensorTrigger{
    public TempSensor() throws MqttException {
        super();
        this.client = new MqttClient(broker, clientId, persistence);
        this.topic = super.topic+"temp";

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
        if(value!=null) {
            try{
                double numericValue = Double.parseDouble(value.toString());
            }catch (Exception e){
                System.out.println("Skipping "+value+" : "+e.getMessage());
                return;
            }
            if (Double.parseDouble(value.toString()) < 20) {//todo agir sur les seuils
                System.out.println("La pièce a besoin de chauffage");
            } else if (Double.parseDouble(value.toString()) >= 20) {
                System.out.println("La pièce n'a pas besoin de chauffage");
            }
        }
    }
}
