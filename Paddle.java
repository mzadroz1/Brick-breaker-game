package proz.project.model;

import javax.swing.*;
import java.util.ArrayList;

public class Paddle extends GameObject{

    private int size;
    private int ammo;
    private ArrayList<Missile> missiles;

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {
        missiles = new ArrayList<>();
        size = 0;
        loadImage();
        getImageDimensions();
        resetState();
        ammo = 0;
    }

    private void loadImage(){
        if(size == 0) {
            ImageIcon img = new ImageIcon("images/paddle.png");
            setImage(img.getImage());
        }
        else if(size == 1) {
            ImageIcon img = new ImageIcon("images/paddle1.png");
            setImage(img.getImage());
        }
        else  if(size == 2) {
            ImageIcon img = new ImageIcon("images/paddle2.png");
            setImage(img.getImage());
        }

    }

    public int getSize() {
        return size;
    }

    private void resetState() {
        setX(400);
        setY(500);
    }

    public void setSize() {
        this.size += 1;
        loadImage();
        getImageDimensions();
    }

    public int getAmmo() {
        return ammo;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void addAmmo() {
        int mX = this.getX() + this.getImageWidth()/2;
        this.ammo += 5;
        for(int i =0; i<5; i++)
            this.missiles.add(new Missile(mX,this.getY(),false));
    }

    public void reduceAmmo() {
        if(ammo!=0)
            ammo-=1;
    }
}
