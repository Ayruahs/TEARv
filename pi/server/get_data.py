import sys
import datetime
import json
import os

# Get sensors' data from Pi and send it to the app

def run(day, start, end):
    now = datetime.datetime.now()
    ffile = './sensor_data/' + str(now.year) + '-' + str(now.month) + '-' + str(now.day)
    countFile = open("./sensor_data/count.txt", "w+")
    count= countFile.readline()
    if count == "":
        return 0


    #check if there is reading error
    errorfile = './sensor_data/error_log'
    try:
        with open(errorfile) as f:
            error = json.load(errorfile, 'r')
            time = error['tempSensor']['time']
            if datetime.datetime.utcnow().isoformat() - time < 5:
                return -1
            else:
                # delete error file
                os.remove(errorfile)
    except IOError:

    data = {{'results':[], 'count': count}}

    with open(ffile):
        results = json.load(ffile, 'r')

    for i in range(200):
        #format json string to return

    return

