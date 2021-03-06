package proz.project.model;

import javax.swing.*;

public class Bonus extends GameObject {


    private boolean visible;
    private int type;

    public Bonus(int x, int y, boolean visible, int type) {

        initBonus(x,y,visible,type);
    }


    private void initBonus(int x, int y, boolean visible, int type) {
        setX(x);
        setY(y);
        this.visible = visible;
        this.type = type;
        loadImage();
        getImageDimensions();

    }

    private void loadImage(){
        if(type == 1) {
            ImageIcon img = new ImageIcon("images/expand.png");
            setImage(img.getImage()); }
        if(type == 2) {
            ImageIcon img = new ImageIcon("images/ammo.png");
            setImage(img.getImage()); }

    }

    public void setVisible(boolean value) {
        visible = value;
    }

    public boolean getVisible() {
        return visible;
    }

    public int getType() {
        return type;
    }

}
