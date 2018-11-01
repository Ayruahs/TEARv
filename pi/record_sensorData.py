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

    countFile = open("./sensor_data/count.txt", "w+")
    count= countFile.readline()
    if count == "":
        count = 0
    else:
        count = int(count)
    print 'before collecting'
    print count

    while True:
        print 'file'
        print ffile
        
        time1 = datetime.datetime.utcnow().isoformat()
        humidity, temperature = Adafruit_DHT.read_retry(sensor, 9)
        
        if humidity is not None and temperature is not None:
            print 'collecting'

            count = count + 1
            print count
            data = {count:[]}
            data[count].append({
                'time': time1,
                'temperature': round(temperature, 2),
                'humidity': round(humidity, 2),
                'device': 9,
                'id': count
            })
            print data
            with open(ffile, 'a+') as json_file:
                json.dump(data, json_file)

            countFile.write(str(count))

        else:
            print('Failed to get reading. Try again!')
            error = {{['tempSensor']:'error', ['timestamp']:time1}}

            with open('./sensor_data/error_log', 'w+') as error_log:
                json.dump(error, error_log)
            break

        print 'finished json'

        time.sleep(5)

    print 'error'
    countFile.close()

if __name__ == "__main__":
    main()

