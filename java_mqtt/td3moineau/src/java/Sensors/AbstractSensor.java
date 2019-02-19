package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//permet d'agir sur un sensor via mqtt
public class AbstractSensor {
    //--
    protected String topic        = "miage1/menez/sensors/";
    protected String content      = "AbstractSensoris not meant to be used";
    protected int qos             = 2;
    protected String broker       = "tcp://localhost:1883";
    protected String clientId     = "Java";
    protected MemoryPersistence persistence = new MemoryPersistence();
    //--
    protected MqttClient client;

    public void connect() throws MqttException {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.print("Connecting to broker: "+broker + " on topic "+topic+"... ");
        if(client==null) client = new MqttClient(broker, clientId, persistence);
        client.connect(connOpts);
        System.out.println("Connected");
    }

    public void publish() throws MqttException {
        System.out.print("Publishing message: "+content+"... ");
        MqttMessage messageMqtt = new MqttMessage(content.getBytes());
        messageMqtt.setQos(qos);
        client.publish(topic, messageMqtt);
        System.out.println("Message published");
    }

    public void disconnect() throws MqttException {
        client.disconnect();
        System.out.println("Disconnected");
        System.exit(0);
    }
}
