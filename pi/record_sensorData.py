# Built for Purdue Air Sense Project translated to TEARv (CS 30700 - Team 36)
import time
import datetime
import Adafruit_DHT
import pytz
import json
import os

TZ = pytz.timezone("America/New_York")

def main():
    sensor = Adafruit_DHT.DHT22
    now = datetime.datetime.now()
    ffile = './sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    while True:
        print ffile

        with open(ffile, 'a+') as json_file:
            data = json.load(json_file)
            if data.get("results") == "":
                print 'json is empty'
                count = 0
            else:
                count = data['count']
        time1 = datetime.datetime.utcnow().isoformat()
        humidity, temperature = Adafruit_DHT.read_retry(sensor, 9)

        if humidity is not None and temperature is not None:
                count = count + 1
                data['results'].append({count:{
                    'time': time1,
                    'temperature': round(temperature, 2),
                    'humidity': round(humidity, 2),
                    'device': 9,
                    'id': count
                }})

        else:
            print('Failed to get reading. Try again!')
            error = {}
            error['tempSensor'] = 'error'
            error['timestamp'] = time1

            #with open('./sensor_data/error_log', 'w+') as error_log:
            json.dump(error, json_file)

        print 'finished json'
        data['count'] = count
        with open(ffile, 'a+') as json_file:
            json.dump(data, json_file)

        time.sleep(5)



if __name__ == "__main__":
    main()
