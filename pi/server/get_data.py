import sys
from flask import Flask
import datetime
import json
import os

# Get sensors' data from Pi and send it to the app

def run(requestId):
    now = datetime.datetime.now()
    ffile = '/home/pi/cs307/TEARv/pi/sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)
    data = {}
    data['results'] = []
    data['lastId'] = -1
    data['count'] = 0
    data['error'] = 'false'

    data = {}
    data['results'] = []
    data['lastId'] = -1
    data['count'] = 0
    data['error'] = 'false'

    if os.path.isfile("/home/pi/cs307/TEARv/pi/sensor_data/count.txt"):
        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "r")
        count= countFile.readline()
        print count
        if len(count) == 0 or count <= requestId:
            return data
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
                data['error'] = 'true'
                return data
            else:
                # delete error file
                os.remove(errorfile)
                data['error'] = 'false'
                print 'old error'

    except IOError:
        print 'no error'
        pass


    print 'getting data..'
    print data

#    with open(ffile, 'rb+') as filehandle:
#        filehandle.seek(-2, os.SEEK_END)
#        last_c = filehandle.read(1)
#        print 'last char'
#        print last_c
#        if last_c == ",":
#            filehandle.seek(-2, os.SEEK_END)
#            filehandle.write("]")

    with open(ffile) as f1:
        results1 = f1.read()
        if (results1[len(results1) - 1] == ','):
            temp = list(results1)
            temp[len(results1) - 1] = ']'
            results1 = "".join(temp)
            #print temp
            #print results1
        results = json.loads(results1)

#    with open(ffile, "w") as f2:
#        f2.seek(-2, os.SEEK_END)
#        f2.write(",")

    total = count - requestId

    #if total >= 200:
    #    total = 200

    #diff = count - 200

    start = requestId

    #print results
    #print count

    for i in range(total):
        #format json string to return
        #print results[start]
        data['results'].append(results[start])
        start = start + 1

    data['count']  = total
    data['lastId'] = count
    print data

    return data

if __name__ == "__main__":

    run(0)

##import sys
##import datetime
##import json
##import os
##
### Get sensors' data from Pi and send it to the app
##
##def run():
##    now = datetime.datetime.now()
##    ffile = '/home/pi/cs307/TEARv/pi/sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)
##
##    if os.path.isfile("/home/pi/cs307/TEARv/pi/sensor_data/count.txt"):
##        countFile = open("/home/pi/cs307/TEARv/pi/sensor_data/count.txt", "r")
##        count= countFile.readline()
##        print count
##        if len(count) == 0:
##            return 0
##    else:
##        print 'no count.txt'
##        return 0
##
##    #check if there is reading error
##    errorfile = 'cs307/TEARv/pi/sensor_data/sensor_data/error_log'
##    try:
##        print 'check sensor error'
##        with open(errorfile) as f:
##            error = json.load(f)
##            time = error['time']
##            if datetime.datetime.utcnow().isoformat() - time < 5:
##                return -1
##            else:
##                # delete error file
##                os.remove(errorfile)
##
##    except IOError:
##        pass
##
##    print 'getting data..'
##    data = {'results':[], 'count': count}
##    print data
##
##    with open(ffile) as f1:
##        results = f1.read()
##        print results
##        print '\n\n\n'
##
##    if count > 200:
##        for i in range(200):
##            #format json string to return
##            data['results'].append(results[count - i])
##
##    else:
##        for i in range(count):
##            data['results'].append(results[i])
##
##    print data
##    return data
##
##
##if __name__ == "__main__":
##    run()
