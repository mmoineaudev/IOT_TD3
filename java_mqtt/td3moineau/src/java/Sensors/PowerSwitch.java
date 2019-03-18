package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static Sensors.Sensors.values;

public class PowerSwitch extends AbstractSensor{
    private double temperature = -270;
    private double enlightment= -1;


    public PowerSwitch() throws MqttException, InterruptedException {
        this.client = new MqttClient(broker, clientId, persistence);
        this.topic = super.topic+"led";

    }

    @Override
    public void publish() throws MqttException {
        try {
            connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.publish();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        //super.messageArrived(s, mqttMessage);
        //do nothing
    }
    public void react() throws MqttException, InterruptedException {
        //si le message est cohérent
        if (values.size()==2) {
            enlightment=values.get(new LightSensor().topic);
            temperature=values.get(new TempSensor().topic);
            //si la lumière est allumée et qu'il fait froid
            if(enlightment>2000 && temperature < 25){
                heat();
            }else {
                //si la lumière est éteinte et qu'il fait froid
                //on n'allume pas le chauffage
                //si la lumiere est éteinte et qu'il fait chaud
                //on n'allume pas le chauffage

                //si la lumière est allumée et qu'il fait chaud
                //on n'allume pas le chauffage
                cool();
            }
        }
    }

    private void cool() throws MqttException {
        content = "off";
        publish();
    }

    private void heat() throws MqttException {
        content = "on";
        publish();
    }

    private boolean isValidDoubleValue(String message) {
        try{
            Double.parseDouble(message);
        }catch(Exception e){
            return false;
        }return true;
    }

}
