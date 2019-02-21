package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.HashMap;

public class Sensors {
    private HashMap<String, AbstractSensor> mapping;

    public Sensors() {
        this.mapping = new HashMap<String, AbstractSensor>();
    }

    public void addSensor(String ID, AbstractSensor sensor){
        mapping.put(ID, sensor);
    }
    public void connect() throws MqttException {
        for(String s : mapping.keySet())
            mapping.get(s).connect();
    }
    public void disconnect() throws MqttException {
        for(String s : mapping.keySet())
            mapping.get(s).disconnect();
    }
    public void listen() throws MqttException {
        for(String s : mapping.keySet())
            mapping.get(s).listen();
    }

    private void run(AbstractSensor aSensor) throws InterruptedException {
        try {
            aSensor.connect();
            aSensor.listen();
            aSensor.publish();
            Thread.sleep(1000);
            aSensor.disconnect();
        } catch(
                MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
