sudo service mosquitto stop; sudo service mosquitto start
xterm -hold -e "mosquitto_sub -h localhost -t miage1/menez/sensors/temp" &
xterm -hold -e "mosquitto_sub -h localhost -t miage1/menez/sensors/led" &
xterm -hold -e "mosquitto_sub -h localhost -v -t \$SYS/#" &
