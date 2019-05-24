package proz.project.model;

import javax.swing.*;

public class Brick extends GameObject {

    private int hp;
    private int bonus;

    public Brick(int x, int y, int hp, int bonus) {

        initBrick(x,y,hp,bonus);
    }

    private void initBrick(int x, int y, int hp, int bonus) {

        setX(x);
        setY(y);
        this.hp = hp;
        this.bonus = bonus;

        loadImage();
        getImageDimensions();
    }

    private void loadImage(){
        if(hp==1) {
        ImageIcon img = new ImageIcon("images/brick.png");
        setImage(img.getImage()); }
        if(hp==2) {
            ImageIcon img = new ImageIcon("images/brick2.png");
            setImage(img.getImage()); }
        if(hp==3) {
            ImageIcon img = new ImageIcon("images/brick3.png");
            setImage(img.getImage()); }

    }

    public boolean isDestroyed() {
        return hp == 0;
    }

    public void setDestroyed() {
        if(hp!=0)
        hp-=1;
        loadImage();
    }

    public int getHp() {
        return hp;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }


}
