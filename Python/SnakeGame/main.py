from turtle import Turtle, Screen
from snake import Snake
from food import Food
from scoreboard import Scoreboard
import time

# VARIABLES
game_is_on = True
score = 0

# SCREEN
screen = Screen()
screen.setup(600, 600)
screen.bgcolor('black')
screen.title('Whitesnake')
screen.tracer(0)  # Turns of animation. Needs '.update()' later.
# snake_speed = int(screen.textinput(title='Snake speed', prompt="Set snake speed (1-30):"))
# while snake_speed not in range(1, 31):
#     snake_speed = int(screen.textinput(title='Snake speed', prompt="Invalid number. Set snake speed (1-30):"))

# OBJECT CREATION
snake = Snake()
screen.listen()
food = Food()
scoreboard = Scoreboard(0)

screen.onkey(snake.up, 'Up')
screen.onkey(snake.down, 'Down')
screen.onkey(snake.left, 'Left')
screen.onkey(snake.right, 'Right')

# SNAKE MOVEMENT
while game_is_on:
    screen.update()
    time.sleep(0.1)  # Adds 0.1 sec delay.
    snake.move()

    # FOOD COLLISION DETECTION
    if snake.head.distance(food) < 15:
        food.refresh()
        snake.extend()
        scoreboard.increase_score()

    # WALL COLLISION DETECTION
    if snake.head.xcor() > 280 or snake.head.xcor() < -280 or snake.head.ycor() > 280 or snake.head.ycor() < -280:
        game_is_on = False
        scoreboard.game_over()

    # TAIL COLLISION DETECTION
    for segment in snake.segments[1:]:
        if snake.head.distance(segment) < 10:  # Slicing to avoid the head activating 'game_over'
            game_is_on = False
            scoreboard.game_over()


screen.exitonclick()
