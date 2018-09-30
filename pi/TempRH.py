# Author: Rishabh Ramsisaria
# Built for Purdue Air Sense Project translated to TEARv (CS 30700 - Team 36)

import time
import datetime
import Adafruit_DHT
import pytz
import statistics

TZ = pytz.timezone("America/New_York")

def main():
    counter = 0
    sensor = Adafruit_DHT.DHT22
    f = open("tempdata1.csv", "a+")
    humidity1 = [0, 0, 0, 0]
    temperature1 = [0, 0, 0, 0]
    if f.readlines() == []:
        f.write("timestamp, temperature, humidity\n")
    while True:
        while (counter < 4):
            try:
                lc_time = datetime.datetime.now()
                lc_dt = TZ.localize(lc_time, is_dst=None)
                start_time = time.time()
                humidity1[counter], temperature1[counter] = Adafruit_DHT.read_retry(sensor, 9) 		    	
                print("{0} temperature = {1:.1f} humidity = {2:.2f}\n".format(lc_dt.isoformat(),temperature1[counter],humidity1[counter]))
                f.flush()
                time.sleep(15 - (time.time() - start_time))
                counter = counter + 1
       	    except Exception as e:
            	print('Exception')
            	#f.close()
        f.write("{0},{1:.1f},{2:.2f}\n".format(lc_dt.isoformat(),statistics.median(temperature1),statistics.median(humidity1)))
        counter = 0

if __name__ == "__main__":
    main()
