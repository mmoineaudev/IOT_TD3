package Sensors;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


//permet d'agir sur un sensor via mqtt
public class AbstractSensor implements IMqttMessageListener, SensorTrigger, Runnable {
    //--
    protected String topic        = "miage1/menez/sensors/";
    protected String content      = "AbstractSensoris not meant to be used";
    protected int qos             = 2;
    protected String broker       = "tcp://localhost:1883";
    protected String clientId     = "Java";
    protected MemoryPersistence persistence = new MemoryPersistence();
    //--
    protected MqttClient client;

    public AbstractSensor() throws MqttException {
        client = null;

    }

    public void listen() throws MqttException {
        if(client.isConnected()) {
            client.setTimeToWait(5000);
        }else{
            System.out.println("connection to topic error, retrying...");
        }
    }

    public void connect() throws MqttException, InterruptedException {
        if(!client.isConnected()) {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);
            System.out.print("Connecting to broker: " + broker + " on topic " + topic + "... ");
            try{
                client.connect(connOpts);
            }catch(Exception e){
                System.out.println("retrying to connect to "+this.topic+" in 3 secs : "+e.getMessage());
                connect();
                return;
            }
            System.out.println("Connected");
            client.subscribe(topic, this);
        }
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


    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        System.out.println("messageArrived : "+mqttMessage.toString());
        this.triggered(mqttMessage);
    }

    //to be overridden
    public void triggered(MqttMessage value) {
        System.out.println(this.getClass().getSimpleName()+" cannot be triggered");
    }

    public void run() {
        System.out.println(this.getClass().getSimpleName()+ "running !");
        try {
            connect();
            while(true)
                listen();
        } catch (MqttException e) {
            System.out.println("Exception : "+e.getMessage());
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
