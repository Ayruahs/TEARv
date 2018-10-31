import sys
import datetime
import json
import os

# Get sensors' data from Pi and send it to the app

def run(day, start, end):
    now = datetime.datetime.now()
    file = './sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)

    if not os.path.isfile(file):
        #return no data
        return -1
    else:
        #check if there is reading error
        errorfile = './sensor_data/error_log'
        if os.path.isfile(errorfile):
            error = json.load(errorfile, 'r')
            time = error['tempSensor']['time']
            if datetime.datetime.utcnow().isoformat() - time < 5:
                return -1
            else:
                # delete error file
                os.remove(errorfile)

        data = json.load(file, 'r')
        count = data['count']

        for i in range(200):
            #format json string to return

    return

