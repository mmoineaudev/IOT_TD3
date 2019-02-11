# IOT_TD3

Installation de mosquitto

## Test fonctionnel du client et du serveur

- On s'abonne au canal de communication

```
asus@azerty:~$ mosquitto_sub -h localhost -t tpmqtt/tests
```

- Sur deux autres terminaux

```
asus@azerty:~$ mosquitto_pub -h localhost -p 1883 -t tpmqtt/tests -m "Le premier message"
```

```
asus@azerty:~$ mosquitto_pub -h localhost -p 1883 -t tpmqtt/tests -m "Le deuxieme message"
```

- De retour sur le premier

```
asus@azerty:~$ mosquitto_sub -h localhost -t tpmqtt/tests
Le premier message
Le deuxieme message
```

## Dump du serveur

```
asus@azerty:~$ mosquitto_sub -h localhost -v -t \$SYS/#

$SYS/broker/load/sockets/5min 0.49
$SYS/broker/load/connections/5min 0.49
$SYS/broker/load/messages/received/15min 0.95
$SYS/broker/load/messages/sent/15min 4.02
$SYS/broker/load/publish/sent/15min 3.29
$SYS/broker/load/bytes/received/15min 16.49
$SYS/broker/load/bytes/sent/15min 131.91
$SYS/broker/load/sockets/15min 0.27
$SYS/broker/load/connections/15min 0.27
$SYS/broker/heap/current 26712
$SYS/broker/heap/maximum 27080
$SYS/broker/messages/received 20
$SYS/broker/messages/sent 66
$SYS/broker/publish/messages/sent 50
$SYS/broker/bytes/received 355
$SYS/broker/bytes/sent 2023
$SYS/broker/publish/bytes/sent 242

```

## Un serveur public ?

### Liens utiles

- https://www.hivemq.com/

- https://iot.eclipse.org/getting-started/

- https://docs.shiftr.io/interfaces/mqtt/

### Application

On utilise :

- https://github.com/knolleary/pubsubclient

- documentation : https://pubsubclient.knolleary.net/api.html

On l'ajoute aux librairies de l'IDE

## TODO : Expérimentation
