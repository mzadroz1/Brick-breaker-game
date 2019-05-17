package proz.project.model;

import java.util.ArrayList;

public class Board {

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        ball = new Ball();
        paddle = new Paddle();
        bricks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                bricks.add( new Brick(j * 110 + 23, i * 52 + 15, /*(i+j)%3 + 1*/ 3));
            }
        }
    }

    public Ball getBall() {

        return ball;
    }

    public Paddle getPaddle() {

        return paddle;
    }

    public ArrayList<Brick> getBricks() {

        return bricks;
    }
}
