from flask import Flask
import controls

app = Flask(__name__)


@app.route('/api/moveForward')
def move_forward():
    controls.forward(1)

@app.route('/api/turnLeft')
def turn_left():
    controls.pivotLeft(1)

@app.route('/api/turnRight')
def turn_right():
    controls.pivotRight(1)

@app.route('/api/moveBackward')
def move_backward():
    controls.reverse(1)


if __name__ == "__main__":
    controls.init()
    app.run(host='0.0.0.0', debug=True)
