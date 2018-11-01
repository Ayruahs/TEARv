import sys
import datetime
import json
import os

# Get sensors' data from Pi and send it to the app

def run(day, start, end):
    now = datetime.datetime.now()
    ffile = '/home/pi/cs307/TEARv/pi/sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    if os.path.isfile("/home/pi/cs307/TEARv/pi/sensor_data/count.txt"):
        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "r")
        count= countFile.readline()
        print count
        if len(count) == 0:
            return 0
    else:
        print 'no count.txt'
        return 0

    #check if there is reading error
    errorfile = 'cs307/TEARv/pi/sensor_data/sensor_data/error_log'
    try:
        print 'check sensor error'
        with open(errorfile) as f:
            error = json.load(f)
            time = error['time']
            if datetime.datetime.utcnow().isoformat() - time < 5:
                return -1
            else:
                # delete error file
                os.remove(errorfile)

    except IOError:
        pass

    print 'getting data..'
    data = {{'results':[], 'count': count}}
    print data

    with open(ffile) as f1:
        results = json.load(f1, 'r')

    if count > 200:
        for i in range(200):
            #format json string to return
            data['results'].append(results[count - i])

    else:
        for i in range(count):
            data['results'].append(results[i])

    print data
    return data

