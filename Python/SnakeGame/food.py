from turtle import Turtle
import random

FOOD_SHAPES = ['circle', 'square', 'triangle']
FOOD_COLORS = ['red', 'orange', 'yellow', 'green', 'cyan', 'blue', 'purple']


class Food(Turtle):  # Food inherits Turtle

    def __init__(self):
        super().__init__()  # Initializes the Turtle
        self.shape(random.choice(FOOD_SHAPES))
        self.penup()
        self.shapesize(stretch_len=0.5, stretch_wid=0.5)  # Makes it 10x10
        self.color(random.choice(FOOD_COLORS))
        self.speed('fastest')
        self.refresh()

    def refresh(self):  # Creates a new random piece of food.
        random_x = random.randint(-280, 280)
        random_y = random.randint(-280, 280)
        self.shape(random.choice(FOOD_SHAPES))
        self.color(random.choice(FOOD_COLORS))
        self.goto(random_x, random_y)

