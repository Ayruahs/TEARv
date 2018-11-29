import RPi.GPIO as GPIO
import time
import sys
import Tkinter as tk

control_pins = [31,33,35,37]

def init():
    GPIO.setmode(GPIO.BOARD)
    for pin in control_pins:
        GPIO.setup(pin, GPIO.OUT)
        GPIO.output(pin, 0)

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

def clockwise():
    for i in range(4):
        for halfstep in range(8):
            for pin in range(4):
                GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
            time.sleep(0.001)

def anticlockwise():
    for i in range(4):
        for halfstep in range(8):
            for pin in range(4):
                GPIO.output(control_pins[pin], halfstep_seq2[halfstep][pin])
            time.sleep(0.001)

count = 0

def keyInput(event):
    global count
    init()
    print 'Key: ', event.char
    print 'Count: ', count
    keyPress = event.char

    if keyPress.lower() == 'k' and count > -256:
        print 'ANTI ', count
        anticlockwise()
        count = count - 4
    elif keyPress.lower() == 'l' and count < 256:
        print 'CLOCK ', count
        clockwise()
        count = count + 4
    else:
        time.sleep(0.05)


def resetPos():
    global count
    while count > 0:
        print 'ANTI ', count
        anticlockwise()
        count = count - 4
    while count < 0:
        print 'CLOCK ', count
        clockwise()
        count = count + 4
    GPIO.cleanup()


command = tk.Tk()
command.bind('<KeyPress>', keyInput)
command.mainloop()
resetPos()
