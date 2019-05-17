package proz.project.model;

import javax.swing.*;

public class Ball extends GameObject {

    private int xDir;
    private int yDir;

    public Ball()  {
        initBall();
    }

    public void setxDir(int dir) {
        xDir = dir;
    }

    public int getxDir() {
        return xDir;
    }

    public void setyDir(int dir) {
        yDir = dir;
    }

    public int getyDir() {
        return yDir;
    }


    private void initBall() {
        xDir = 1;
        yDir = 1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {
        ImageIcon img = new ImageIcon("images/ball.png");
        image = img.getImage();
    }

    private void resetState() {
        x = 200;
        y = 300;
    }


}
