package proz.project.model;

import javax.swing.*;
import java.awt.*;

public class GameMainMenu extends JPanel {
    @Override
    protected void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
    }

    private void fillBackground(Graphics2D g) {
        ImageIcon img = new ImageIcon("images/background.png");
        Image imgBackground;
        imgBackground = img.getImage();
        g.drawImage(imgBackground,0,0,this);

        ImageIcon img1 = new ImageIcon("images/logo.png");
        g.drawImage(img1.getImage(),400-img1.getImage().getWidth(null)/2,100,this);
    }


}

