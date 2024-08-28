import paho.mqtt.client as mqtt
import time
import random
import os
import json

import logging
from sys import stdout

# Define logger
logger = logging.getLogger('mylogger')

logger.setLevel(logging.DEBUG)
logFormatter = logging.Formatter \
    (" %(asctime)s %(levelname)-8s %(message)s")
consoleHandler = logging.StreamHandler(stdout)
consoleHandler.setFormatter(logFormatter)
logger.addHandler(consoleHandler)

# get env variables set in docker compose
building = os.getenv("BUILDING", "home")
room = os.getenv("ROOM", "bedroom")

broker = "mqtt-broker"
port = 1883
temperature = 20.0 + random.uniform(-5, 5) # Starting temperature
topic = building + "/" + room + "/temperature"
client = "thermometer-" + building + "-" + room

logger.info("connecting to broker ...")

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, client)
client.connect(broker, port)

logger.info("connected to broker")

deviation = 0
i = 0


time.sleep(random.uniform(1, 10))
while True:
    # Simulate temperature variation
    temperature += random.uniform(-0.1 + deviation, 0.1 + deviation)

    # Create JSON payload
    payload = {
        "building": building,
        "room": room,
        "celsius": round(temperature, 2)
    }
    json_payload = json.dumps(payload)

    client.publish(topic, json_payload)
    logger.info(f"published {json_payload}")
    i += 1
    if i % 50 == 0:
        deviation = random.uniform(-0.05, 0.05)
        i = 0
    time.sleep(10)
