package Sensors;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sensors{
    public static final long TIME_TO_WAIT = 500;
    private HashMap<String, AbstractSensor> mapping;
    public static HashMap<String, Double> values = new HashMap<>();

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

            for(String a : mapping.keySet()) mapping.get(a).connect();

            execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void execute() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(mapping.size());
        PowerSwitch p = new PowerSwitch();
        try {
            p.connect();
            for (;;) {

                for(AbstractSensor r : mapping.values()) {
                    executor.execute(r);
                    p.react();

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            executor.shutdown();
            throw new Exception("Executor is down");
        }
    }

}
