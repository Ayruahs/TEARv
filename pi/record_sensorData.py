# Built for Purdue Air Sense Project translated to TEARv (CS 30700 - Team 36)

import time
import datetime
import Adafruit_DHT
import pytz
import json
import os
import statistics
import io
import uuid

TZ = pytz.timezone("America/New_York")

def main():
    sensor = Adafruit_DHT.DHT22
    now = datetime.datetime.now()
    file = '/sensor_data/' + now.year + now.month + now.day + '/' + now.year + '-' + now.month + '-' + now.day

    if not file.isfile():
        data = {}
        data['results'] = []
        data['count'] = 0
        count = 0
    else:
        data = json.load(file, 'r')
        count = data['count']

        while True:
            time = datetime.datetime.utcnow().isoformat()
            humidity, temperature = Adafruit_DHT.read_retry(sensor, 9)

            if humidity is not None and temperature is not None:
                count = count + 1
                data['results'].append({
                    'time': time,
                    'temperature': round(temperature, 2),
                    'humidity': round(humidity, 2),
                    'device': 9,
                    'id': count
                })

            else:
                print('Failed to get reading. Try again!')
                error = {}
                error['tempSensor'] = 'error'
                error['timestamp'] = time

                with open(file+'/error_log', 'a+') as error_log:
                    json.dump(error, error_log)

            with open(file, 'a+') as file:
                json.dump(data, file)

            time.sleep(5)



if __name__ == "__main__":
    main()
