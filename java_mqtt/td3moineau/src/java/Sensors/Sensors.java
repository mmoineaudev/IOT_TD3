package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;
import sun.management.Sensor;

import java.util.HashMap;

public class Sensors implements Runnable{
    private HashMap<String, AbstractSensor> mapping;
    private boolean connected = false;
    public Sensors() {
        this.mapping = new HashMap<String, AbstractSensor>();
    }

    public void addSensor(String ID, AbstractSensor sensor){
        mapping.put(ID, sensor);
    }
    public void connect() throws MqttException {
        for(String s : mapping.keySet()) {
            try {
                mapping.get(s).connect();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    public void disconnect() throws MqttException {
        for(String s : mapping.keySet())
            mapping.get(s).disconnect();
    }
    public void listen() throws MqttException {
        for(String s : mapping.keySet())
            mapping.get(s).listen();
    }
    public void run(){
        try {
            this.addSensor("TEMP", new TempSensor());
            this.addSensor("LIGHT", new LightSensor());
            for (String s : mapping.keySet()) {
                    System.out.print("running "+s+"...");
                    if(!mapping.get(s).client.isConnected()) {
                            mapping.get(s).connect();
                    }
                    run(mapping.get(s));
            }
        } catch (MqttException e) {
            e.printStackTrace();//todo remettre l'ancien
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void run(AbstractSensor aSensor) {
        try {
            Thread t = new Thread(aSensor);
            t.run();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
