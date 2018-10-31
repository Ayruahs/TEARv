# Built for Purdue Air Sense Project translated to TEARv (CS 30700 - Team 36)

import datetime
import Adafruit_DHT
import pytz
import json
import os

TZ = pytz.timezone("America/New_York")

def main():
    sensor = Adafruit_DHT.DHT22
    now = datetime.datetime.now()
    file = './sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    while True:
        if not os.path.isfile(file):
            data = {'results': [], 'count': 0}
            os.makedirs(file)
            with open(file, 'a+') as file:
                json.dump(data, file)
        else:
            data = json.load(file, 'r')
            count = data['count']
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

                with open('./sensor_data/error_log', 'w+') as error_log:
                    json.dump(error, error_log)

            with open(file, 'a+') as file:
                json.dump(data, file)

            time.sleep(5)



if __name__ == "__main__":
    main()
