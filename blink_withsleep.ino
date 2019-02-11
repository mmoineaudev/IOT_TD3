/*
 * Blink
 * https://www.youtube.com/watch?v=y1R2y8dCsIg
 * http://www.lucadentella.it/en/2018/01/22/esp32-29-deep-sleep/
 */
// ledPin refers to ESP32 GPIO 19 
const int ledPin = 19;

#define TIME_TO_SLEEP 2           /* Time ESP32 will go to sleep (in seconds) */
#define TIME_TO_UP 2               /* */
#define us_TO_S_FACTOR 1000000     /* Conversion factor for microseconds to seconds */

RTC_DATA_ATTR int bootcount = 1;

// the setup function runs once when you press reset or power the board
void setup() {  
    delay(500);
    
    // initialize digital pin ledPin as an output.
    pinMode(ledPin, OUTPUT);
    digitalWrite(ledPin, HIGH);   // turn the LED on (HIGH is the voltage level)
    delay(TIME_TO_UP * 1000); 
    digitalWrite(ledPin, LOW);
    bootcount ++;
    //Serial.println(bootcount);
    
    // Armement du timer
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * us_TO_S_FACTOR);

    //Deep sleep mode
    esp_deep_sleep_start();
}

// the loop function runs over and over again forever
void loop() {
}
