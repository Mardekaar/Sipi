from turtle import Turtle

ALIGNMENT = 'center'
FONT = ('Courier', 20, 'normal')  # We put these here for better control.


class Scoreboard(Turtle):

    def __init__(self, score):
        """Takes bool for 'collision'."""
        super().__init__()
        self.score = score
        self.penup()
        self.hideturtle()
        self.color('white')
        self.goto(0, 270)
        self.update_scoreboard()

    def update_scoreboard(self):
        self.write(f"Score: {self.score}", False, ALIGNMENT, font=FONT)

    def increase_score(self):
        self.score += 1
        self.clear()
        self.write(f"Score: {self.score}", False, ALIGNMENT, font=FONT)

    def game_over(self):
        self.goto(0, 0)
        self.write("GAME OVER", False, ALIGNMENT, font=FONT)

