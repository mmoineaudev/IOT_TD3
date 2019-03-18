package Sensors;

import org.eclipse.paho.client.mqttv3.MqttException;
import sun.management.Sensor;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.*;

public class Sensors{
    public static final long TIME_TO_WAIT = 1000;
    private HashMap<String, AbstractSensor> mapping;
    private boolean connected = false;
    public Sensors() {
        this.mapping = new HashMap<String, AbstractSensor>();
    }

    public void addSensor(String ID, AbstractSensor sensor){
        mapping.put(ID, sensor);
    }

    public void run(){
        try {
            this.addSensor("TEMP", new TempSensor());
            this.addSensor("LIGHT", new LightSensor());

            execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void execute() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(mapping.size());
        PowerSwitch powerSwitch = new PowerSwitch();
        for (;;) {
            for(AbstractSensor r : mapping.values()) {
                try {

                    r.addPowerSwitch(powerSwitch);
                    executor.execute(r);
                    executor.execute(powerSwitch);
                } catch (Exception ex) {
                    //executor.shutdown();
                    throw new Exception("Executor is down : \n"+ ex.getMessage()+"\n"+ ex.getCause());
                }
            }
        }

    }

}
