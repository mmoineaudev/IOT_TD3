package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;
import sun.management.Sensor;

import java.util.HashMap;

public class Sensors {
    private HashMap<String, AbstractSensor> mapping;
    private boolean connected = false;
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
    public void run() {
        for (String s : mapping.keySet()) {
                System.out.print("running "+s+"...");
                run(mapping.get(s));
        }
    }
    private void run(AbstractSensor aSensor) {
        try {
            aSensor.listen();
            //aSensor.publish();
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
