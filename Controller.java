package proz.project.controller;

import java.awt.event.KeyEvent;

import proz.project.model.*;
import proz.project.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private Board board;
    private Timer timer;
    private View view;
    private boolean gameOver;
    private boolean gameStarted = false;
    private boolean paused = false;
    private String msg = "";

    static final int MOVE_DELTA = 3;
    static final int OBJECTS_MOVE_DELTA = 1;

    public HashMap<Integer, Boolean> pressedKeys;

    public Controller(Board b) {
        if(gameStarted) {
            resetController(b);
        }
        pressedKeys = new HashMap<>();
    }

    public void resetController(Board b) {
        board = b;
        gameOver = false;
        timer = new Timer();
        final int INITIAL_DELAY = 50;
        final int PERIOD_INTERVAL = 6;
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
        msg = "Game Over";
    }

    public void setView(View v) {
        this.view = v;
    }

    private void moveLeft() {
        board.getPaddle().setX(board.getPaddle().getX()-MOVE_DELTA);
        checkLeftBorder();
    }

    private void moveRight() {
        board.getPaddle().setX(board.getPaddle().getX()+MOVE_DELTA);
        checkRightBorder();
    }

    private void shoot() {
        if(board.getPaddle().getAmmo() > 0 && !board.getPaddle().isReloading()) {
        ArrayList<Missile> missiles = board.getPaddle().getMissiles();
        for(int i = 0; i< missiles.size(); i++) {
            if(!missiles.get(i).getVisible()) {
                missiles.get(i).setVisible(true);
                missiles.get(i).setX(board.getPaddle().getX()+board.getPaddle().getImageWidth()/2 - missiles.get(i).getImageWidth()/2);
                missiles.get(i).setY(board.getPaddle().getY());
                moveMissile();
                board.getPaddle().reduceAmmo();
                break;
            }
        }
        board.getPaddle().setReload(true);
        timer.schedule(new Reload(),200);
        }

    }

    private void pauseGame() {
        setPause(true);
        //timer.cancel();
    }

    public void unpauseGame() {
        setPause(false);
        pressedKeys.remove(KeyEvent.VK_ESCAPE);
    }

    private class Reload extends TimerTask {
        @Override
        public void run() {
            board.getPaddle().setReload(false);
        }
    }

    private void keyHandler(){
        for(Integer keyCode : pressedKeys.keySet()){
            if (keyCode == KeyEvent.VK_LEFT) {
                if(!pressedKeys.containsKey(KeyEvent.VK_RIGHT)) {

                    moveLeft();
                }
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if(!pressedKeys.containsKey(KeyEvent.VK_LEFT)) {

                    moveRight();
                }
            }

            if (keyCode == KeyEvent.VK_SPACE) {
                shoot();
            }

            if (keyCode == KeyEvent.VK_ESCAPE) {
                pauseGame();
            }

        }
    }


    private void moveMissile() {
        ArrayList<Missile> missiles = board.getPaddle().getMissiles();
        for(int i = 0; i<missiles.size(); i++) {
            if(missiles.get(i).getVisible()) {
                missiles.get(i).setY(missiles.get(i).getY() - OBJECTS_MOVE_DELTA);
            }

        }
    }

    private void moveBall() {
        Ball b = board.getBall();

        if(b.getX() <= 0)
            b.setxDir(1);
        if(b.getX() >= view.getWidth() - b.getImageWidth())
            b.setxDir(-1);
        if(b.getY() <= 0)
            b.setyDir(1);
        b.setX(b.getX()+b.getxDir());
        b.setY(b.getY() + b.getyDir());
    }

    private void moveBonus() {
        ArrayList<Bonus> bonuses = board.getBonus();
        for(int i = 0; i<bonuses.size(); i++) {
            if(bonuses.get(i).getVisible()) {
                bonuses.get(i).setY(bonuses.get(i).getY() + OBJECTS_MOVE_DELTA);
            }

        }
    }

    private void checkRightBorder() {
        final int lastPossibleX = view.getWidth() - board.getPaddle().getImageWidth();
        if (board.getPaddle().getX() >= lastPossibleX) {
            board.getPaddle().setX(lastPossibleX);
        }
    }

    private void checkLeftBorder() {
        if (board.getPaddle().getX() < 0) {
            board.getPaddle().setX(0);
        }
    }

    private void stopGame() {
        timer.cancel();
    }

    private void paddleCollision() {

        Ball ball = board.getBall();

        if ((ball.getRect()).intersects(board.getPaddle().getRect())) {

            int paddleLPos = (int) board.getPaddle().getRect().getMinX();
            int ballLPos = (int) (ball.getRect().getMinX() + ball.getImageWidth()/2);
            int piece = (int) (board.getPaddle().getImageWidth() * 0.2);

            int first = paddleLPos + piece;
            int second = paddleLPos + piece*2;
            int third = paddleLPos + piece*3;
            int fourth = paddleLPos + piece*4;

            if (ballLPos < first) {
                ball.setxDir(-1);
            }
            if (ballLPos >= first && ballLPos < second) {
                //stays the same
            }
            if (ballLPos >= second && ballLPos < third) {
                ball.setxDir(0);
            }
            if (ballLPos >= third && ballLPos < fourth) {
                //stays the same
            }
            if (ballLPos > fourth) {
                ball.setxDir(1);
            }
            ball.setyDir(-1);
        }
    }

    private void bricksCollision() {

        Ball ball = board.getBall();
        ArrayList<Brick> bricks = board.getBricks();

        for (int i = 0; i < bricks.size(); i++) {

            int brickLeft = (int) bricks.get(i).getRect().getMinX();
            int brickRight = (int) bricks.get(i).getRect().getMaxX();
            int brickTop = (int) bricks.get(i).getRect().getMinY();
            int brickBottom = (int) bricks.get(i).getRect().getMaxY();
            int ballRight = (int) ball.getRect().getMaxX();
            int ballLeft = (int) ball.getRect().getMinX() ;
            int ballBottom = (int) ball.getRect().getMaxY();
            int ballTop = (int) ball.getRect().getMinY();


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
                            ball.setyDir(-1);
                    }

                    bricks.get(i).setDestroyed();
                    if(bricks.get(i).isDestroyed() && bricks.get(i).getBonus()==1) {
                        if(board.getBonus().get(i).getType()==1 && board.getPaddle().getSize()>=2)
                            continue;
                        board.getBonus().get(i).setVisible(true);
                    }
                }
            }
        }
        moveBall();
    }

    private void missilesCollision() {

        missilesOutOfScreen();

        ArrayList<Missile> missiles = board.getPaddle().getMissiles();
        ArrayList<Brick> bricks = board.getBricks();

        for(int i = 0; i<missiles.size(); i++) {
            for(int j = 0; j<bricks.size(); j++) {
                if(missiles.size()!= 0  && i<missiles.size() && !bricks.get(j).isDestroyed() && missiles.get(i).getVisible() && missiles.get(i).getRect().intersects(bricks.get(j).getRect())) {
                    bricks.get(j).setDestroyed();
                    missiles.get(i).setVisible(false);
                    missiles.remove(i);
                    if(bricks.get(j).isDestroyed() && bricks.get(j).getBonus()==1) {
                        if(board.getBonus().get(j).getType()==1 && board.getPaddle().getSize()==2)
                            continue;
                        board.getBonus().get(j).setVisible(true);
                    }
                }
            }
        }
        moveMissile();
    }

    private void missilesOutOfScreen() {
        ArrayList<Missile> missiles = board.getPaddle().getMissiles();
        for(int i = 0; i<missiles.size(); i++) {
            if (missiles.get(i).getY() == 0) {
                missiles.get(i).setVisible(false);
                missiles.remove(i);
            }
        }
    }

    private void bonusesCollision() {

        ArrayList<Bonus> bonuses = board.getBonus();

        for(int i = 0; i< bonuses.size(); i++) {
            if(bonuses.get(i).getVisible() && bonuses.get(i).getRect().intersects(board.getPaddle().getRect()) ) {
                if(bonuses.get(i).getType() == 1)
                    board.getPaddle().setSize();
                else
                    board.getPaddle().addAmmo();
                bonuses.get(i).setVisible(false);
            }
        }
        moveBonus();
    }


    private void checkCollision() {

        bricksCollision();

        paddleCollision();

        bonusesCollision();

        missilesCollision();

    }

    private void isGameOver() {

        ArrayList<Brick> bricks = board.getBricks();
        int destroyedCount = 0;
        for(int i = 0; i < bricks.size(); i++) {
            if(bricks.get(i).isDestroyed()) destroyedCount++;
        }
        if(destroyedCount == bricks.size()) {
            msg = "Victory!";
            gameOver = true;
            stopGame();
        }

        Ball ball = board.getBall();
        if (ball.getRect().getMinY() >= board.getPaddle().getRect().getMaxY()) {
            gameOver = true;
            stopGame();
        }
    }

    void updateGame() {
        if(!isPaused()) {
            checkCollision();

            isGameOver();
            try {
                keyHandler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        view.updateView();
    }


    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            updateGame();
        }
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isPaused() {
        return paused;
    }

    private void setPause(boolean paused) {
        this.paused = paused;
    }
}
