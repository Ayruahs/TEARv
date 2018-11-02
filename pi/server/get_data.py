import sys
import datetime
import json
import os

# Get sensors' data from Pi and send it to the app

def run(requestId):
    now = datetime.datetime.now()
    ffile = '/home/pi/cs307/TEARv/pi/sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    if os.path.isfile("/home/pi/cs307/TEARv/pi/sensor_data/count.txt"):
        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "r")
        count= countFile.readline()
        print count
        if len(count) == 0:
            return 0
        else:
            count = int(count)
    else:
        print 'no count.txt'
        return 0

    #check if there is reading error
    errorfile = '/home/pi/cs307/TEARv/pi/sensor_data/error_log'
    try:
        print 'check sensor error'
        with open(errorfile) as f:
            error = json.load(f)
            time = error['time']
            if datetime.datetime.utcnow().isoformat() - time < 5:
                print 'error'
                return -1
            else:
                # delete error file
                os.remove(errorfile)
                print 'old error'

    except IOError:
        print 'no error'
        pass

    print 'getting data..'
    data = {}
    data['results'] = []
    data['id'] = 0
    counter = 0
    print data

    wfile = open(ffile, "a")
    wfile.write("]")

    with open(ffile) as f1:
        results = json.load(f1)

    if count > 200:
        for i in range(200):
            #format json string to return
            data['results'].append(results[count - i])
        data['count']  = 200

    else:
        for i in range(count):
            data['results'].append(results[i])
        data['count']  = count

    print data
    return data


if __name__ == "__main__":
    run(0)

