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
        if not os.path.isfile(ffile):
            data = {'results': [], 'count': 0}
            os.makedirs(ffile)
            with open(ffile, 'a+') as json_ffile:
                json.dump(data, ffile)
            print 'ffile ' + ffile + 'created'
        else:
            print ffile
            with open(ffile, 'a+') as json_ffile:
                try:
                    data = json.load(json_ffile)
                except ValueError:
                    count = 0
                    data = {}
                    data['results'] = []
                    data['count'] = 0
                    print 'json is empty'
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
                    data['count'] = count

                else:
                    print('Failed to get reading. Try again!')
                    error = {}
                    error['tempSensor'] = 'error'
                    error['timestamp'] = time1

                    #with open('./sensor_data/error_log', 'w+') as error_log:
                    json.dump(error, json_ffile)

                print 'finished json'

                #with open(ffile, 'a+') as ffile:
                json.dump(data, json_ffile)

            time.sleep(5)



if __name__ == "__main__":
    main()
