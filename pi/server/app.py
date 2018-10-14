from flask import Flask

app = Flask(__name__)


@app.route('/api/moveForward')
def move_forward():
    pass

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
    app.run(host='0.0.0.0', debug=True)
