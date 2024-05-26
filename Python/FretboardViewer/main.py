#  Fretboard Viewer v0.1
#  Created by Alexandra Zerner on 25.5.2024
#  Visualize scales, chords, and other shapes on a guitar fretboard by clicking on fret positions on strings.
#  Note letters, keys, and modes to be added in future builds.

import sys
import numpy as np
from PyQt6.QtWidgets import (QApplication, QMainWindow, QLabel, QPushButton, QGridLayout, QWidget)
from PyQt6.QtGui import QPixmap

# CONSTANTS
FRET_WIDTH = 42
FRET_HEIGHT = 24
FRET_NUMBER = 24


# CLASSES
class Button(QPushButton):
    def __init__(self, name):
        super().__init__()
        self.name = name
        self.setCheckable(True)
        self.toggled.connect(self.button_toggled)

    def button_toggled(self, checked):  # This takes a bool from self.toggled.connect(self.button_toggled)
        self.update_button_color(checked)

    def update_button_color(self, checked):
        if checked:
            style = ("background-color: rgba(144, 25, 25, 255);"  # Using CSS for the button design.
                     "border: 1px solid gray; "
                     "border-radius: 8; ")
        else:
            style = ("background-color: rgba(0, 0, 0, 0);"
                     "border: 1px solid gray; "
                     "border-radius: 8; ")
        self.setStyleSheet(style)


# GUI ELEMENTS
app = QApplication([])

window = QMainWindow()
window.setWindowTitle("Fretboard Viewer v0.1 by Alexandra Zerner")
window.setFixedSize(1200, 240)

buttons_grid_layout = QGridLayout()  # Grid layout for the fret buttons.
buttons_grid_layout.setContentsMargins(12, 8, 1, 5)

center_widget = QWidget()
center_widget.setFixedWidth(1200)
center_widget.setFixedHeight(240)

background_image = QLabel(center_widget)
background_pixmap = QPixmap("d:/_Download/_GIT/Sipi/Python/FretboardViewer/fretboard.png")
background_image.setPixmap(background_pixmap)
background_image.setFixedSize(1200, 240)
background_image.lower()  # Moves the image to the background layer.

center_widget.setLayout(buttons_grid_layout)

# Creating a 2D array using NumPy to store the buttons:
button_name = []
all_buttons = np.empty((6, FRET_NUMBER + 1), dtype=object)
string_lines = []
frets = []

# Generating the buttons:
for i in range(6):
    for j in range(FRET_NUMBER + 1):
        button_name.append(f"str{i + 1}fr{j + 1}")
        new_button = Button(button_name)
        new_button.setFixedHeight(FRET_HEIGHT)
        new_button.setFixedWidth(FRET_WIDTH - j)
        new_button.setStyleSheet("background-color: rgba(0, 0, 0, 0);"
                                 "border: 1px solid gray; "
                                 "border-radius: 8; ")
        all_buttons[i][j] = new_button

# Adding the buttons to the grid layout:
for i in range(6):
    for j in range(FRET_NUMBER + 1):
        buttons_grid_layout.addWidget(all_buttons[i][j], i, j)

# Adding the central widget to the main window:
window.setCentralWidget(center_widget)
window.show()

# EXIT
app.exec()
sys.exit(app.exec())
