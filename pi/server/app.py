from flask import Flask
import controls
import get_data
import io
import json
import time
import RPi.GPIO as gpio

app = Flask(__name__)

@app.route('/api/moveForward', methods=['GET'])
def move_forward():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/turnLeft', methods=['GET'])
def turn_left():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/turnRight', methods=['GET'])
def turn_right():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/moveBackward', methods=['GET'])
def move_backward():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/moveUpRight', methods=['GET'])
def upRight():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)

    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/moveUpLeft', methods=['GET'])
def upLeft():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/moveBackLeft', methods=['GET'])
def backLeft():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/moveBackRight', methods=['GET'])
def backRight():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(0.1)
    gpio.cleanup()

@app.route('/api/getSensorData', methods=['GET'])
def get_sensor_data(requestId):
    #print ("temp: ")
    #send result back
    #
    #now = datetime.datetime.now()
    id = request.args.get('id')
    result = get_data.run(id)
    return flask.jsonify(result)

@app.route('/api/lightsOn', methods=['GET'])
def lights_on():
    init()
    gpio.output(22, True)
    time.sleep(5)

@app.route('/api/lightsOff', methods=['GET'])
def lights_off():
    init()
    gpio.output(22, False)
    time.sleep(5)

@app.route('/api/test')
def test():
    print ("Pi accessible")

def init():
    gpio.setmode(gpio.BOARD)
    gpio.setup(7, gpio.OUT)
    gpio.setup(11, gpio.OUT)
    gpio.setup(13, gpio.OUT)
    gpio.setup(15, gpio.OUT)
    gpio.setup(22, gpio.OUT)

    gpio.output(7, False)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, False)
    gpio.output(22, False)
    #gpio.cleanup()

if __name__ == "__main__":
#    controls.init()
    
    app.run(debug=False, host="0.0.0.0", port=8000)
