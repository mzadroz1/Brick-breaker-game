package proz.project.controller;

import java.awt.*;
import java.awt.event.KeyEvent;

import proz.project.model.*;
import proz.project.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private Board board;
    private Paddle paddle;
    private Timer timer;
    private View view;

    public static final int MOVE_DELTA = 10;
    private final int INITIAL_DELAY = 500;
    private final int PERIOD_INTERVAL = 6;


    public Controller(Board b) {
        board = b;
        paddle = board.getPaddle();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
    }

    public void setView(View v) {
        this.view = v;
    }

    public void moveLeft() {
        paddle.x -= MOVE_DELTA;
        checkLeftBorder();
        view.updateView();
    }

    public void moveRight() {
        paddle.x += MOVE_DELTA;
        checkRightBorder();
        view.updateView();
    }

    public void shoot() {
        if(paddle.getAmmo() > 0) {
        ArrayList<Missile> missiles = paddle.getMissiles();
        for(int i = 0; i< missiles.size(); i++) {
            if(missiles.get(i).getVisible()!= true) {
                missiles.get(i).setVisible(true);
                missiles.get(i).setX(paddle.getX()+paddle.getImageWidth()/2);
                missiles.get(i).setY(paddle.getY());
                moveMissile();
                paddle.reduceAmmo();
                //missiles.remove(i);
                break;
            }
        }
        }
    }

    public void moveMissile() {
        ArrayList<Missile> missiles = paddle.getMissiles();
        for(int i = 0; i<missiles.size(); i++) {
            if(missiles.get(i).getVisible()) {
                missiles.get(i).setY(missiles.get(i).getY() - 1);
            }

        }
    }

    public void moveBall() {
        Ball b = board.getBall();
        b.setX(b.getX()+b.getxDir());
        b.setY(b.getY() + b.getyDir());
        if(b.getX() < 0)
            b.setxDir(1);
        if(b.getX() >= view.getWidth() - b.getImageWidth())
            b.setxDir(-1);
        if(b.getY() < 0)
            b.setyDir(1);
    }

    public void moveBonus() {
        ArrayList<Bonus> bonuses = board.getBonus();
        for(int i = 0; i<bonuses.size(); i++) {
            if(bonuses.get(i).getVisible()) {
                bonuses.get(i).setY(bonuses.get(i).getY() + 1);
            }

        }
    }

    private void checkRightBorder() {
        final int lastPossibleX = view.getWidth() - paddle.getImageWidth();
        if (paddle.x >= lastPossibleX) {
            paddle.x = lastPossibleX;
        }
    }

    private void checkLeftBorder() {
        if (paddle.x < 0) {
            paddle.x = 0;
        }
    }

    private void stopGame() {
        timer.cancel();
    }

    private void checkCollision() {


        Ball ball = board.getBall();


        if (ball.getRect().getMaxY() >= 550) {
            stopGame();
        }


        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) (ball.getRect().getMinX() + ball.getImageWidth()/2);
            int piece = (int) (paddle.getImageWidth() * 0.2);

            int first = paddleLPos + piece;
            int second = paddleLPos + piece*2;
            int third = paddleLPos + piece*3;
            int fourth = paddleLPos + piece*4;

            if (ballLPos < first) {
                ball.setxDir(-1);
                ball.setyDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setyDir(-1);
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setxDir(0);
                ball.setyDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setyDir(-1 );
            }

            if (ballLPos > fourth) {
                ball.setxDir(1);
                ball.setyDir(-1);
            }
        }



        ArrayList<Brick> bricks = board.getBricks();
        int destroyedCount = 0;
        for(int i = 0; i < bricks.size(); i++) {
            if(bricks.get(i).isDestroyed()) destroyedCount++;
        }
        if(destroyedCount == bricks.size())
            stopGame();


        for (int i = 0; i < bricks.size(); i++) {

            int brickLeft = (int) bricks.get(i).getRect().getMinX();
            int brickRight = (int) bricks.get(i).getRect().getMaxX();
            int brickTop = (int) bricks.get(i).getRect().getMinY();
            int brickBottom = (int) bricks.get(i).getRect().getMaxY();
            int ballRight = (int) ball.getRect().getMaxX();
            int ballLeft = (int) ball.getRect().getMinX() ;
            int ballBottom = (int) ball.getRect().getMaxY();
            int ballTop = (int) ball.getRect().getMinY();
            int ballX = (int) ball.getRect().getMaxX() - ball.getImageWidth()/2;
            int ballY = (int) ball.getRect().getMaxY() - ball.getImageHeight()/2;
            //int ballXdir = ball.getxDir();
            //int ballYdir = ball.getY()

           if ((ball.getRect()).intersects(bricks.get(i).getRect())) {

                if(!bricks.get(i).isDestroyed()) {

                      if(ball.getxDir() == 0)
                          ball.setyDir(-1 * ball.getyDir());

                      else {
                          if(ballLeft <= brickRight && ballLeft >= brickRight - 1)
                              ball.setxDir(1);
                              else if(ballRight >= brickLeft && ballRight <= brickLeft + 1)
                                ball.setxDir(-1);
                          else if(ballTop <= brickBottom && ballTop >= brickBottom -1)
                              ball.setyDir(1);
                              else if (ballBottom >= brickTop && ballBottom <= brickTop +1)
                          ball.setyDir(-1);}



                      //bricks.get(i).initBrick(bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getHp()-1,bricks.get(i).getBonus());
                    bricks.get(i).setDestroyed();
                      if(bricks.get(i).isDestroyed() && bricks.get(i).getBonus()==1) {
                          board.getBonus().get(i).setVisible(true);
                      }
                }



            }
        }

        ArrayList<Bonus> bonuses = board.getBonus();

        for(int i = 0; i< bonuses.size(); i++) {
            if(bonuses.get(i).getVisible() && bonuses.get(i).getRect().intersects(paddle.getRect()) ) {
                if(bonuses.get(i).getType() == 1)
                    paddle.setSize();
                else
                    paddle.addAmmo();
                bonuses.get(i).setVisible(false);
            }
        }

        ArrayList<Missile> missiles = paddle.getMissiles();

        for(int i = 0; i<missiles.size(); i++) {
            for(int j = 0; j<bricks.size(); j++) {
                if(!bricks.get(j).isDestroyed() && missiles.get(i).getVisible() && missiles.get(i).getRect().intersects(bricks.get(j).getRect())) {
                    bricks.get(j).setDestroyed();
                    missiles.get(i).setVisible(false);
                    //missiles.remove(i);
                }
            }
        }
    }



    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            checkCollision();
            view.updateView();
        }
    }
}
