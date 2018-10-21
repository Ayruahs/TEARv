from flask import Flask
import controls

app = Flask(__name__)


@app.route('/api/moveForward')
def move_forward():
    

@app.route('/api/turnLeft')
def turn_left():
    pass

@app.route('/api/turnRight')
def turn_right():
    pass

@app.route('/api/moveBackward')
def move_backward():
    pass


if __name__ == "__main__":
    controls.init()
    app.run(host='0.0.0.0', debug=True)
