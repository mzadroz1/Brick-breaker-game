package proz.project.model;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private ArrayList<Bonus> bonus;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        ball = new Ball();
        paddle = new Paddle();
        bricks = new ArrayList<>();
        bonus = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                Random rand = new Random();
                bricks.add( new Brick(j * 110 + 23, i * 52 + 15, (i+j)%3 + 1,rand.nextInt(2)));
                bonus.add( new Bonus(j * 110 + 23, i * 52 + 15,false,rand.nextInt(2)+1));
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

    public ArrayList<Bonus> getBonus() {
        return bonus;
    }
}
