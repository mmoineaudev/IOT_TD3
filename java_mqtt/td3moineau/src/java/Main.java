import Sensors.AbstractSensor;
import Sensors.LedSensor;
import Sensors.TempSensor;
import Sensors.Sensors;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    static int nbSecondsRunning = 30;
    public static void main(String[] args) throws MqttException, InterruptedException {
        Sensors sensors = new Sensors();
        //sensors.addSensor("LED", new LedSensor());
        sensors.addSensor("TEMP", new TempSensor());
        sensors.connect();
        int i =0;
        while (i++<nbSecondsRunning) {
            sensors.run();
            Thread.sleep(1000);
        }
        sensors.disconnect();
    }



}