from flask import Flask, jsonify
import controls
import datetime
import get_data
import io
import json
import time
import RPi.GPIO as gpio

app = Flask(__name__)
app.config['isOn'] = False
app.config['count'] = 0

@app.route('/api/moveForward', methods=['GET'])
def move_forward():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/turnLeft', methods=['GET'])
def turn_left():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/turnRight', methods=['GET'])
def turn_right():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/moveBackward', methods=['GET'])
def move_backward():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/moveUpRight', methods=['GET'])
def upRight():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)

    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/moveUpLeft', methods=['GET'])
def upLeft():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/moveBackLeft', methods=['GET'])
def backLeft():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/moveBackRight', methods=['GET'])
def backRight():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()
    init()

@app.route('/api/twistLeft', methods=['GET'])
def twist_left():
    control_pins = [31,33,35,37]

    halfstep_seq = [
        [1,0,0,0],
        [1,1,0,0],
        [0,1,0,0],
        [0,1,1,0],
        [0,0,1,0],
        [0,0,1,1],
        [0,0,0,1],
        [1,0,0,1]
    ]    
    if(app.config['count'] < 256):
        init()
        for i in range(8):
            for halfstep in range(8):
                for pin in range(4):
                    gpio.output(control_pins[pin], halfstep_seq[halfstep][pin])
                time.sleep(0.001)
        app.config['count'] = app.config['count'] + 8
        #print app.config['count']

        gpio.cleanup()
        init()

@app.route('/api/twistRight', methods=['GET'])
def twist_right():
    control_pins = [31,33,35,37]

    halfstep_seq2 = [
        [1,0,0,1],
        [0,0,0,1],
        [0,0,1,1],
        [0,0,1,0],
        [0,1,1,0],
        [0,1,0,0],
        [1,1,0,0],
        [1,0,0,0]
    ]  

    if(app.config['count'] > -256):
        init()
        for i in range(8):
            for halfstep in range(8):
                for pin in range(4):
                    gpio.output(control_pins[pin], halfstep_seq2[halfstep][pin])
                time.sleep(0.001)
        app.config['count'] = app.config['count'] - 8
        #print app.config['count']

        gpio.cleanup()
        init()

@app.route('/api/returnHome', methods=['GET'])
def twist_return():
    control_pins = [31,33,35,37]

    halfstep_seq = [
        [1,0,0,0],
        [1,1,0,0],
        [0,1,0,0],
        [0,1,1,0],
        [0,0,1,0],
        [0,0,1,1],
        [0,0,0,1],
        [1,0,0,1]
    ]    
    
    halfstep_seq2 = [
        [1,0,0,1],
        [0,0,0,1],
        [0,0,1,1],
        [0,0,1,0],
        [0,1,1,0],
        [0,1,0,0],
        [1,1,0,0],
        [1,0,0,0]
    ]  
    while(app.config['count'] > 0):
        init()
        for i in range(8):
            for halfstep in range(8):
                for pin in range(4):
                    gpio.output(control_pins[pin], halfstep_seq2[halfstep][pin])
                time.sleep(0.001)
        app.config['count'] = app.config['count'] - 8
        #print app.config['count']

    while(app.config['count'] < 0):
        init()
        for i in range(8):
            for halfstep in range(8):
                for pin in range(4):
                    gpio.output(control_pins[pin], halfstep_seq[halfstep][pin])
                time.sleep(0.001)
        app.config['count'] = app.config['count'] + 8
        #print app.config['count']

        gpio.cleanup()

@app.route('/api/getSensorData', methods=['GET'])
def get_sensor_data(requestId):
    #print ("temp: ")
    #send result back
    #
    #now = datetime.datetime.now()
    obj = get_data.run(0)
    return jsonify(obj)

@app.route('/api/lightsOn', methods=['GET'])
def lights_on():
    #init()
    app.config['isOn'] = True
    init()
    gpio.output(18, app.config['isOn'])
    return "SDSD"


    

@app.route('/api/lightsOff', methods=['GET'])
def lights_off():
    app.config['isOn'] = False
    init()
    gpio.output(18, app.config['isOn'])

@app.route('/api/test')
def test():
    print ("Pi accessible")

def init():
    control_pins = [31,33,35,37]

    gpio.setmode(gpio.BOARD)
    gpio.setup(7, gpio.OUT)
    gpio.setup(11, gpio.OUT)
    gpio.setup(13, gpio.OUT)
    gpio.setup(15, gpio.OUT)
    gpio.setup(18, gpio.OUT)

    for pin in control_pins:
        gpio.setup(pin, gpio.OUT)
        gpio.output(pin, 0)


    gpio.output(7, False)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, False)
    gpio.output(18, app.config['isOn'])

if __name__ == "__main__":
#    controls.init()
    
<<<<<<< HEAD
    app.run(debug=False, host="0.0.0.0", port=8000)
=======
    app.run(debug=False, host="0.0.0.0", port=5000)
>>>>>>> data-transfer
