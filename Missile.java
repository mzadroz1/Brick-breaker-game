package proz.project.model;

import javax.swing.*;

public class Missile extends GameObject {

    private boolean visible;

    public Missile(int x, int y, boolean visible) {

        initMissile(x,y,visible);
    }

    private void initMissile(int x, int y, boolean visible) {
        setX(x);
        setY(y);
        this.visible = visible;
        loadImage();
        getImageDimensions();

    }

    private void loadImage(){
        ImageIcon img = new ImageIcon("images/missile.png");
        setImage(img.getImage());
    }

    public void setVisible(boolean value) {
        visible = value;
    }

    public boolean getVisible() {
        return visible;
    }

}


