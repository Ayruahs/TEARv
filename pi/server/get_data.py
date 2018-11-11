import sys
from flask import Flask
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
            return -2
        else:
            count = int(count)
            if count <= requestId:
                return 0
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
    print data

    with open(ffile, 'rb+') as filehandle:
        filehandle.seek(-2, os.SEEK_END)
        last_c = filehandle.read(1)
        print 'last char'
        print last_c
        if last_c == ",":
            filehandle.seek(-2, os.SEEK_END)
            filehandle.write("]")

    with open(ffile) as f1:
        results = json.load(f1)

    length = count-requestId
    if length > 200:
        length = 200

    start = requestId

    for i in range(length):
        #format json string to return
        start = start + i
        data['results'].append(results[start])
    data['count']  = length
    data['lastId'] = start

    print data
    return Flask.jsonify(data)


if __name__ == "__main__":
    run(0)

