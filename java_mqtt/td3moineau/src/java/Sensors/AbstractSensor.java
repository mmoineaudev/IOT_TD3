package Sensors;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


//permet d'agir sur un sensor via mqtt
public class AbstractSensor implements IMqttMessageListener, Runnable {
    //--
    protected String topic        = "miage1/menez/sensors/";
    protected String content      = "AbstractSensoris not meant to be used";
    protected int qos             = 2;
    protected String broker       = "tcp://localhost:1883";
    protected String clientId     = "Java";
    protected MemoryPersistence persistence = new MemoryPersistence();
    //--
    protected MqttClient client;
    protected PowerSwitch powerSwitch;

    public AbstractSensor() throws MqttException, InterruptedException {
        client = null;
        powerSwitch = null;
    }

    public void listen() throws MqttException, InterruptedException {

        Thread.sleep(Sensors.TIME_TO_WAIT);
    }

    public void connect() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(true);
        connOpts.setConnectionTimeout(10);
        System.out.print("Connecting to broker: " + broker + " on topic " + topic + "... ");
        try{
            client.connect(connOpts);
            client.subscribe(topic, this);
        }catch(Exception e){
            System.out.println(e.getClass().getSimpleName()+" "+e.getMessage()+": "+this.topic+" : "+e.getCause());
            return;
        }
        System.out.println(topic+ " connected");

    }

    //supposed to be called in messageArrived
    public void publish() throws MqttException {
        System.out.print("Publishing message: "+content+" to topic "+topic+"... ");
        MqttMessage messageMqtt = new MqttMessage(content.getBytes());
        messageMqtt.setQos(qos);
        messageMqtt.setRetained(true);
        client.publish(topic, messageMqtt);
        System.out.println("Message published");
    }

    public void disconnect() throws MqttException {
        client.disconnect();
        System.out.println("Disconnected");
    }


    public void messageArrived(String s, MqttMessage mqttMessage){
        System.out.println("messageArrived on topic [\n"+this.topic+" : "+mqttMessage.toString()+"\n]");
    }

    public void run() {
        try {
            connect();
            listen();
            disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addPowerSwitch(PowerSwitch powerSwitch) {
        this.powerSwitch = powerSwitch;
    }
}
