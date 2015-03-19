#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
ZetCode Tkinter tutorial

This program creates a quit
button. When we press the button,
the application terminates. 

author: Jan Bodnar
last modified: December 2010
website: www.zetcode.com
"""

from Tkinter import *
from PIL import ImageTk, Image
from ttk import Frame, Style, Button
import serial  # importamos modulo serial
import threading
import Queue
import sys, os, time

# Window size:
w = 350
h = 200

#Create root Tk object
root = Tk()

class SerialThread(threading.Thread):
    def __init__(self, queue):
        threading.Thread.__init__(self)
        self.queue = queue

    def run(self):
        bus = serial.Serial("/dev/tty.usbmodem1411")  # indicamos a que dispositivos asignamos el serial
        while True:
            try:
                if bus.inWaiting():
                    read = bus.readline()  # leemos hasta encontrar '\n', Si no hay '\n' se queda esperando
                    read = read.split('\n')  # separamos del la cadena de texto el salto de linea
                    if (read[0][:-1] == 'yellow on'):
                        setupYellowLedOn(root)
                        print('yellow ON received')
                    if (read[0][:-1] == 'yellow off'):
                        setupYellowLedOff(root)
                        print('yellow OFF received')
                    if (read[0][:-1] == 'red on'):
                        setupRedLedOn(root)
                        print('yellow ON received')
                    if (read[0][:-1] == 'red off'):
                        setupRedLedOff(root)
                        print('yellow OFF received')

            except (KeyboardInterrupt, SystemExit) as e:
                print "Interrupt Issued. Exiting Program with error state: %s"%(str(e))
                #exit(1)

class Example(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent
        self.pack(fill=BOTH, expand=1)
        self.centerWindow()
        self.initUI()

        #Not touch it
        self.queue = Queue.Queue()
        thread = SerialThread(self.queue)
        thread.daemon = True #called just before thread.start()
        thread.start()

    def centerWindow(self):
        sw = self.parent.winfo_screenwidth()
        sh = self.parent.winfo_screenheight()

        x = (sw - w) / 2
        y = (sh - h) / 2
        self.parent.geometry('%dx%d+%d+%d' % (w, h, x, y))

        """
        The geometry() method sets a size for the window and positions it on the screen.
        The first two parameters are width and height of the window.
        The last two parameters are x and y screen coordinates.
        """

    def initUI(self):
        self.parent.title("Slave Python GUI - Aleix")
        self.style = Style()
        self.style.theme_use("default")
        self.pack(fill=BOTH, expand=1)

        quitButton = Button(self, text="Quit",
                            command=self.OnQuitButtonClick)

        #We use the place geometry manager to position the button in absolute coordinates. 50x50px from the top-left corner of the window.
        quitButton.place(x=140, y=140)

        setupYellowLedOff(root)
        setupRedLedOff(root)

    def OnQuitButtonClick(self):
        print("You clicked the Quit button!")
        self.quit
        sys.exit()

def setupYellowLedOff(Tk):
    imYellowLedOffTk= ImageTk.PhotoImage(Image.open("yellowoff.jpg")) #from www.geekytheory.com
    label1 = Label(Tk, image=imYellowLedOffTk)
    label1.image = imYellowLedOffTk
    label1.place(x=100, y=20)

def setupYellowLedOn(Tk):
    imYellowLedOnTk= ImageTk.PhotoImage(Image.open("yellowon.jpg")) #from www.geekytheory.com
    label1 = Label(Tk, image=imYellowLedOnTk)
    label1.image = imYellowLedOnTk
    label1.place(x=100, y=20)


def setupRedLedOn(Tk):
    imRedLedOnTk= ImageTk.PhotoImage(Image.open("redon.jpg")) #from www.geekytheory.com
    label1 = Label(Tk, image=imRedLedOnTk)
    label1.image = imRedLedOnTk
    label1.place(x=180, y=20)

def setupRedLedOff(Tk):
    imRedLedOffTk= ImageTk.PhotoImage(Image.open("redoff.jpg")) #from www.geekytheory.com
    label1 = Label(Tk, image=imRedLedOffTk)
    label1.image = imRedLedOffTk
    label1.place(x=180, y=20)


def main():

    Example(root)
    # start the program
    root.mainloop()
    #Here nothing will be executed


if __name__ == '__main__':
    main() 