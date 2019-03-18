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

    public AbstractSensor() throws MqttException, InterruptedException {
        client = null;
    }

    public void listen() throws MqttException, InterruptedException {

        if(client.isConnected()) {
            client.subscribe(topic, this);
            client.setTimeToWait(Sensors.TIME_TO_WAIT);
            System.out.println("listening on "+topic);

        }else{
            System.out.println("connection to topic error... retrying");
            connect();
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
                e.printStackTrace();
            }

        }
    }

    //supposed to be called in messageArrived
    public void publish() throws MqttException {
        System.out.print("Publishing message: "+content+" to topic "+topic+"... ");
        MqttMessage messageMqtt = new MqttMessage(content.getBytes());
        messageMqtt.setQos(qos);
        messageMqtt.setRetained(true);
        client.publish(topic, messageMqtt);
    }

    public void disconnect() throws MqttException {
        System.out.println("disconnect");
        client.disconnect();
    }


    public void messageArrived(String s, MqttMessage mqttMessage){
        System.out.println("messageArrived on topic [\n"+this.topic+" : "+mqttMessage.toString()+"\n]");
        try{
            double value = Double.parseDouble(mqttMessage.toString());
            setValue(value);
        }catch (NumberFormatException e){

        }
    }

    public void run() {
        try {
            listen();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected void setValue(double value){
        Sensors.values.put(topic, value);
    }

}
