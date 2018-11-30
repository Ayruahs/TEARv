# Built for Purdue Air Sense Project translated to TEARv (CS 30700 - Team 36)
import time
import datetime
import statistics
import Adafruit_DHT
import pytz
import json
import os

TZ = pytz.timezone("America/New_York")

def main():
    sensor = Adafruit_DHT.DHT22
    now = datetime.datetime.now()
    ffile = '/home/pi/cs307/TEARv/pi/sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    if not os.path.isfile(ffile):
        os.remove("/home/pi/cs307/TEARv/pi/sensor_data/count.txt")
        f = open(ffile, "w+")
        f.write("[")
        f.close()
    
    if os.path.exists("/home/pi/cs307/TEARv/pi/sensor_data/count.txt"):
        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "r")
        count = countFile.readline()
        count = int(count)
    else:
        count = 0
        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "w+")
    countFile.close()
        
    print 'before collecting'
    print count

    if (count > 0):
        with open(ffile, "a+") as f2:
            f2.seek(-2, os.SEEK_END)
#            f2.write(",")
   
    while True:
        counter = 0
        humidity1 = [0, 0, 0, 0]
        temperature1 = [0, 0, 0, 0]
        
        while (counter < 4):
            humidity1[counter], temperature1[counter] = Adafruit_DHT.read_retry(sensor, 9) 
            if humidity1[counter] is not None and temperature1[counter] is not None:
                counter = counter + 1
            
        temperature, humidity = statistics.median(temperature1),statistics.median(humidity1)    
        time1 = datetime.datetime.utcnow().isoformat()
        
        if humidity is not None and temperature is not None:
            print 'collecting'
            countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "w+")

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
            with open(ffile, 'a+') as json_file:
                json.dump(data, json_file)
                json_file.write(",")

            countFile.write(str(count))
            countFile.close()

        else:
            print('Failed to get reading. Try again!')
            error = {{['tempSensor']:'error', ['timestamp']:time1}}

            with open('/home/pi/cs307/TEARv/pi/sensor_data/error_log', 'w+') as error_log:
                json.dump(error, error_log)
            break

        time.sleep(2)

    print 'error'
    countFile.close()

if __name__ == "__main__":
    main()

