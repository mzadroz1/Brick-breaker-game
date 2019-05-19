package proz.project.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
import java.sql.Timestamp;
import proz.project.controller.Controller;
import proz.project.model.*;

public class SwingView extends JPanel implements View {
    private static final long serialVersionUID = -7729510720848698723L;

    private Board board;
    private Controller controller;
    private Image imgBackground;

    public SwingView() {
        addKeyListener(createKeyListener());
        setFocusable(true);
        ImageIcon img = new ImageIcon("images/background.png");
        imgBackground = img.getImage();
    }

    /*@Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }*/


    private KeyListener createKeyListener(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Integer keyCode = e.getKeyCode();
                controller.pressedKeys.remove(keyCode);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    controller.shoot();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Integer keyCode = e.getKeyCode();
                controller.pressedKeys.put(keyCode, timestamp);
            }
        };
    }


    @Override
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
        paintBall(g);
        paintBricks(g);
        paintBonuses(g);
        paintMissiles(g);
        paintPaddle(g);

        if(controller.getGameOver()) {
            gameOverMenu(g);
        }
        else {
            Font font = new Font("Helvetica", Font.PLAIN, 26);
            g.setFont(font);
            g.setPaint(Color.white);
            g.drawString("Ammo: " + board.getPaddle().getAmmo(), 10, 550);
        }
    }

    private void gameOverMenu(Graphics2D g) {
        fillBackground(g);
        Font font = new Font("Helvetica", Font.PLAIN, 46);
        g.setFont(font);
        g.setPaint(Color.white);
        g.drawString(controller.getMsg(),280,250);

    }

    private void paintBricks(Graphics2D g) {

        ArrayList<Brick> bricks = board.getBricks();
        for (int i = 0; i < board.getBricks().size(); i++) {
            if (!bricks.get(i).isDestroyed()) {
                g.drawImage(bricks.get(i).getImage(), bricks.get(i).getX(),
                        bricks.get(i).getY(), bricks.get(i).getImageWidth(),
                        bricks.get(i).getImageHeight(), this);
            }
        }
    }

    private void paintBonuses(Graphics2D g) {
        ArrayList<Bonus> bonuses = board.getBonus();
        for(int i=0; i<bonuses.size();i++) {
            if(bonuses.get(i).getVisible()) {
                g.drawImage(bonuses.get(i).getImage(), bonuses.get(i).getX(),
                        bonuses.get(i).getY(),bonuses.get(i).getImageWidth(),
                        bonuses.get(i).getImageHeight(), this);
            }
        }
        controller.moveBonus();
    }

    private void paintMissiles(Graphics2D g) {
        ArrayList<Missile> missiles = board.getPaddle().getMissiles();
        for(int i=0; i<missiles.size();i++) {
            if(missiles.get(i).getVisible()) {
                g.drawImage(missiles.get(i).getImage(), missiles.get(i).getX(),
                        missiles.get(i).getY(),missiles.get(i).getImageWidth(),
                        missiles.get(i).getImageHeight(), this);
            }
        }
        controller.moveMissile();
    }

    private void paintPaddle(Graphics2D g) {
        Paddle p = board.getPaddle();
        g.drawImage(p.getImage(),p.getX(),p.getY(),null);
    }

    private void paintBall(Graphics2D g) {
        Ball b = board.getBall();
        g.drawImage(b.getImage(),b.getX(),b.getY(),this);
        controller.moveBall();
    }

    private void fillBackground(Graphics2D g) {
        g.drawImage(imgBackground,0,0,this);
    }

    @Override
    public void updateView() {
        repaint();
    }

    @Override
    public void setModel(Board b) {
        this.board = b;
    }

    @Override
    public void setController(Controller c) {
        this.controller = c;
    }

    public int getHight() {
        return imgBackground.getHeight(null);
    }

}
