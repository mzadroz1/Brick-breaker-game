package proz.project.model;

import javax.swing.*;

public class Brick extends GameObject {

    private int hp;

    public Brick(int x, int y, int hp) {

        initBrick(x,y,hp);
    }

    public void initBrick(int x, int y, int hp) {

        this.x = x;
        this.y = y;
        this.hp = hp;

        loadImage();
        getImageDimensions();
    }

    private void loadImage(){
        if(hp==1) {
        ImageIcon img = new ImageIcon("images/brick.png");
        image = img.getImage(); }
        if(hp==2) {
            ImageIcon img = new ImageIcon("images/brick2.png");
            image = img.getImage(); }
        if(hp==3) {
            ImageIcon img = new ImageIcon("images/brick3.png");
            image = img.getImage(); }

    }

    public boolean isDestroyed() {
        if(hp==0)
        return true;
        else return false;
    }

    public void setDestroyed(boolean value) {
        hp-=1;
    }

    public int getHp() {
        return hp;
    }

}
