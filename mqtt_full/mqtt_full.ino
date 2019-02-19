/*********
  Based on Rui Santos work :
  https://randomnerdtutorials.com/esp32-mqtt-publish-subscribe-arduino-ide/
  Modified by GM
*********/
#include <WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
#include <OneWire.h>
#include <DallasTemperature.h>

//les branchements
#define LED_PIN 23
#define TEMP_PIN 19
#define LIGTH_PIN 34

//Les id de connexion
const char* ssid = "NetworkComesWithFaith";
const char *password = "0987654321";

//la récupération de la température
OneWire oneWire(TEMP_PIN);
DallasTemperature tempSensor(&oneWire);

WiFiClient espClient;           // Wifi
PubSubClient client(espClient); // MQTT client

/*===== MQTT broker/server and TOPICS ========*/
const char* mqtt_server = "192.168.1.113"; /* "broker.shiftr.io"; */


/** Commandes shell
sudo service mosquitto stop; sudo service mosquitto start
mosquitto_sub -h localhost -t test miage1/menez/sensors/temp
mosquitto_sub -h localhost -t test miage1/menez/sensors/led


mosquitto_pub -h localhost -p 1883 -t channel -m "Le premier message"

**/
#define TOPIC_TEMP "miage1/menez/sensors/temp" 
#define TOPIC_LED  "miage1/menez/sensors/led" 

/*============= GPIO ======================*/
float temperature = 0;
float light = 0;

/*================ WIFI =======================*/
void print_connection_status() {
  Serial.print("WiFi status : \n");
  Serial.print("\tIP address : ");
  Serial.println(WiFi.localIP());
  Serial.print("\tMAC address : ");
  Serial.println(WiFi.macAddress());
}

void connect_wifi() {

  Serial.println("Connecting Wifi...");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.println("Attempting to connect Wifi ..");
    delay(1000);
  }
  Serial.print("Connected to local Wifi\n");
  print_connection_status();
}

/*=============== SETUP =====================*/
void setup() {
  pinMode(LED_PIN, OUTPUT);

  Serial.begin(9600);
  connect_wifi();

  client.setServer(mqtt_server, 1883);
  // set callback when publishes arrive for the subscribed topic
  client.setCallback(mqtt_pubcallback);
}

/*============== CALLBACK ===================*/
void mqtt_pubcallback(char* topic, byte* message,
                      unsigned int length) {
  // Callback if a message is published on this topic.

  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");

  // Byte list to String and print to Serial
  String messageTemp;
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();

  // Feel free to add more if statements to control more GPIOs with MQTT

  // If a message is received on the topic,
  // you check if the message is either "on" or "off".
  // Changes the output state according to the message
  if (String(topic) == TOPIC_LED) {
    Serial.print("Changing output to ");
    if (messageTemp == "on") {
      Serial.println("on");
      set_LED(HIGH);
    }
    else if (messageTemp == "off") {
      Serial.println("off");
      set_LED(LOW);
    }
  }
}

void set_LED(int v) {
  digitalWrite(LED_PIN, v);
}

/*============= SUBSCRIBE =====================*/
void mqtt_mysubscribe(char *topic) {
  while (!client.connected()) { // Loop until we're reconnected
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("esp32", "try", "try")) {
      Serial.println("connected");
      // Subscribe
      client.subscribe(topic);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

float get_Temperature() {
  tempSensor.requestTemperaturesByIndex(0);
  return tempSensor.getTempCByIndex(0);
}

/*================= LOOP ======================*/
void loop() {
  int32_t period = 5000; // 5 sec
  /*--- subscribe to TOPIC_LED if not yet ! */
  if (!client.connected()) {
    mqtt_mysubscribe((char *)(TOPIC_LED));
  }

  /*--- Publish Temperature periodically   */
  delay(period);
  temperature = get_Temperature();
  // Convert the value to a char array
  char tempString[8];
  dtostrf(temperature, 1, 2, tempString);
  // Serial info
  Serial.print("Published Temperature : "); Serial.println(tempString);
  // MQTT Publish
  client.publish(TOPIC_TEMP, tempString);

  client.loop(); // Process MQTT ... une fois par loop()
}