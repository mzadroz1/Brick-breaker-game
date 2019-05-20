package proz.project.model;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private ArrayList<Bonus> bonus;
    private int lvl = 0;

    public Board() {
        lvl = 0;
        initBoard();
    }

    public void initBoard() {
        lvl++;
        ball = new Ball();
        paddle = new Paddle();
        bricks = new ArrayList<>();
        bonus = new ArrayList<>();
        Random rand = new Random();

        if (lvl == 1)
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 11; j++) {

                    if ((i == 0 && (j != 3 && j != 5 && j!=9)) ||
                            (((i >= 1 && i <= 3) || (i >= 5 && i <= 7)) && (j == 0 || j == 4 || j == 7 || j == 10)) ||
                            ((i == 4) && (j == 0 || j == 1 || j == 4 || j == 7 || j == 10)) ||
                            ((i == 8) && (j != 3 && j != 5 && j != 6 && j != 8 && j != 9))

                    ) {
                        int hp;
                        if (j <= 3 || j ==10) hp = 1;
                        else if (j >= 6 && j <= 8) hp = 2;
                        else hp = 3;
                        bricks.add(new Brick(j * 71, i * 24 + 10, /*(i + j) % 3 +*/ hp, rand.nextInt(2)));
                        bonus.add(new Bonus(j * 71, i * 24 + 10, false, rand.nextInt(2) + 1));
                    }
                }
            }

        if (lvl == 2)
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 11; j++) {

                    if ((i == 0 && (j != 3 && j != 7)) ||
                            ((i == 1 || i == 2) && (j == 1 || j == 4 || j == 6 || j == 10)) ||
                            ((i == 3) && (j == 1 || j == 4 || j == 6 || j == 9)) ||
                            ((i == 4) && (j == 1 || j == 4 || j == 6 || j > 7)) ||
                            ((i == 5 || i == 6) && (j == 1 || j == 4 || j == 6 || j == 9)) ||
                            ((i == 7) && (j == 1 || j == 4 || j == 6 || j == 8)) ||
                            ((i == 8) && (j != 3 && j != 5 && j != 7))

                    ) {
                        int hp;
                        if (j < 3) hp = 1;
                        else if (j > 3 && j < 7) hp = 2;
                        else hp = 3;
                        bricks.add(new Brick(j * 71, i * 24 + 10, /*(i + j) % 3 +*/ hp, rand.nextInt(2)));
                        bonus.add(new Bonus(j * 71, i * 24 + 10, false, rand.nextInt(2) + 1));
                    }
                }
            }

        if (lvl == 3) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 11; j++) {

                    if ((i == 0 && (j != 1 && j != 2 && j != 4 && j != 8)) ||
                            (i == 1 && (j != 4 && j != 6 && j != 8 && j != 10)) ||
                            ((i >= 2 && i <= 4) && (j == 0 || j == 3 || j == 5 || j == 7 || j == 9)) ||
                            ((i == 5) && (j == 0 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9)) ||
                            ((i == 6 || i == 7) && (j == 7)) ||
                            ((i == 8) && (j == 5 || j == 6 || j == 7))

                    ) {
                        int hp;
                        if (j < 4) hp = 1;
                        else if (j > 4 && j < 8) hp = 2;
                        else hp = 3;
                        bricks.add(new Brick(j * 71, i * 24 + 10, /*(i + j) % 3 +*/ hp, rand.nextInt(2)));
                        bonus.add(new Bonus(j * 71, i * 24 + 10, false, rand.nextInt(2) + 1));
                    }
                }
            }
            bricks.add(new Brick(106, 2 * 24 + 10, /*(i + j) % 3 +*/ 1, rand.nextInt(2)));
            bonus.add(new Bonus(106, 2 * 24 + 10, false, rand.nextInt(2) + 1));
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

    public int getLvl() {
        return lvl;
    }

    public ArrayList<Bonus> getBonus() {
        return bonus;
    }
}
