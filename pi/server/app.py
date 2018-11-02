from flask import Flask
import controls
import get_data
import io
import json
import time
import RPi.GPIO as gpio

app = Flask(__name__)

@app.route('/api/moveForward')
def move_forward():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/turnLeft')
def turn_left():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/turnRight')
def turn_right():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/moveBackward')
def move_backward():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/moveUpRight')
def upRight():
    init()
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/moveUpLeft')
def upLeft():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/moveBackLeft')
def backLeft():
    init()
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/moveBackRight')
def backRight():
    init()
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(1)
    gpio.cleanup()

@app.route('/api/getSensorData')
def get_sensor_data():
    print ("temp: ")
    #send result back
    #
    now = datetime.datetime.now()
    result = get_data.run(0)



@app.route('/api/test')
def test():
    print ("Pi accessible")

def init():
    gpio.setmode(gpio.BOARD)
    gpio.setup(7, gpio.OUT)
    gpio.setup(11, gpio.OUT)
    gpio.setup(13, gpio.OUT)
    gpio.setup(15, gpio.OUT)
    gpio.output(7, False)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, False)
    #gpio.cleanup()

if __name__ == "__main__":
#    controls.init()
    
    app.run(debug=False, host="0.0.0.0")
