package proz.project.model;

import javax.swing.*;

public class Paddle extends GameObject{

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {
        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage(){
        ImageIcon img = new ImageIcon("images/paddle1.png");
        image = img.getImage();
    }

    private void resetState() {
        x = 400;
        y = 500;
    }
}
