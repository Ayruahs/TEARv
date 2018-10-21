import RPi.GPIO as gpio
import time
import sys
import Tkinter as tk


def init():
    gpio.setmode(gpio.BOARD)
    gpio.setup(7, gpio.OUT)
    gpio.setup(11, gpio.OUT)
    gpio.setup(13, gpio.OUT)
    gpio.setup(15, gpio.OUT)

def forward(sleepTime):
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(sleepTime)
    gpio.cleanup()

def reverse(sleepTime):
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(sleepTime)
    gpio.cleanup()

def pivotRight(sleepTime):
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(sleepTime)
    gpio.cleanup()

def pivotLeft(sleepTime):
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(sleepTime)
    gpio.cleanup()

def upRight(sleepTime):
    gpio.output(7, False)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(sleepTime)
    gpio.cleanup()

def upLeft(sleepTime):
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, True)
    gpio.output(15, False)
    time.sleep(sleepTime)
    gpio.cleanup()

def backLeft(sleepTime):
    gpio.output(7, True)
    gpio.output(11, True)
    gpio.output(13, False)
    gpio.output(15, True)
    time.sleep(sleepTime)
    gpio.cleanup()

def backRight(sleepTime):
    gpio.output(7, True)
    gpio.output(11, False)
    gpio.output(13, True)
    gpio.output(15, True)
    time.sleep(sleepTime)
    gpio.cleanup()

def keyInput(event):
    init()
    print ('Key: ', event.char)
    keyPress = event.char
    sleepTime = 0.030

    if keyPress.lower() == 'w':
        forward(sleepTime)
    elif keyPress.lower() == 's':
        reverse(sleepTime)
    elif keyPress.lower() == 'q':
        upLeft(sleepTime)
    elif keyPress.lower() == 'e':
        upRight(sleepTime)
    elif keyPress.lower() == 'z':
        pivotLeft(sleepTime)
    elif keyPress.lower() == 'c':
        pivotRight(sleepTime)
    elif keyPress.lower() == 'a':
        backLeft(sleepTime)
    elif keyPress.lower() == 'd':
        backRight(sleepTime)
    else:
        time.sleep(sleepTime)
        gpio.cleanup()

command = tk.Tk()
command.bind('<KeyPress>', keyInput)
command.mainloop()

