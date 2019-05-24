package proz.project.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import proz.project.controller.Controller;
import proz.project.model.*;

public class SwingView extends JPanel implements View {
    private static final long serialVersionUID = -7729510720848698723L;

    private Board board;
    private Controller controller;

    public SwingView() {
        addKeyListener(createKeyListener());
        setFocusable(true);
    }


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

                if(e.getKeyCode() == KeyEvent.VK_C)
                    board.getPaddle().addAmmo();

                Integer keyCode = e.getKeyCode();
                controller.pressedKeys.put(keyCode, true);
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

        if(controller.getGameOver() || !controller.isGameStarted() || controller.isPaused()) {
            paintMenu(g);
        }
        else {
            paintAmmoAmount(g);
        }
    }

    private void paintMenu(Graphics2D g) {
        chooseMenu(g);
        setLayout(null);
        createButtons();
    }

    private void paintAmmoAmount(Graphics2D g) {
        Font font = new Font("Helvetica", Font.PLAIN, 26);
        g.setFont(font);
        g.setPaint(Color.white);
        g.drawString("Ammo: " + board.getPaddle().getAmmo(), 10, 550);
    }

    private void paintEscToContinue(Graphics2D g) {
        Font font = new Font("Helvetica", Font.PLAIN, 46);
        g.setFont(font);
        g.setPaint(Color.white);
        FontMetrics fm = g.getFontMetrics();
        String msg = "Press ESC to continue";
        g.drawString( msg, getWidth()/2 - fm.stringWidth(msg)/2, getHeight()*8/10);
    }


    private void chooseMenu(Graphics2D g) {

        if(!controller.isPaused())
            fillBackground(g);

        if(!controller.isGameStarted()) {
            ImageIcon img = new ImageIcon("images/logo.png");
            g.drawImage(img.getImage(),getWidth()/2 - img.getImage().getWidth(null)/2,getImgHeight()*2/10,
                    img.getImage().getWidth(null),img.getImage().getHeight(null),this);
        }

        if(controller.isPaused()) {
            ImageIcon img = new ImageIcon("images/pause.png");
            g.drawImage(img.getImage(),getWidth()/2 - img.getImage().getWidth(null)/2,getImgHeight()/10,
                    img.getImage().getWidth(null),img.getImage().getHeight(null),this);
            paintEscToContinue(g);
        }

        else if(controller.getMsg().equals("Game Over")) {
            ImageIcon img = new ImageIcon("images/game-over.png");
            g.drawImage(img.getImage(),getWidth()/2 - img.getImage().getWidth(null)/2,getImgHeight()/10,
                    img.getImage().getWidth(null),img.getImage().getHeight(null),this);
        }
        else if(controller.getMsg().equals("Victory!")) {
            if(board.getLvl() == 3) {
                ImageIcon img = new ImageIcon("images/you-win.png");
                g.drawImage(img.getImage(),getWidth()/2 - img.getImage().getWidth(null)/2,getImgHeight()/10,
                        img.getImage().getWidth(null),img.getImage().getHeight(null),this);
            }
            else {
                ImageIcon img = new ImageIcon("images/level-cleared.png");
                g.drawImage(img.getImage(),getWidth()/2 - img.getImage().getWidth(null)/2,getImgHeight()/10,
                        img.getImage().getWidth(null),img.getImage().getHeight(null),this);
            }
        }
    }

    private void createButtons() {
        if(controller.isPaused()) {
            createContinueButton();
        }
        else {
        JButton newGameButton = new JButton();
        if(controller.getMsg().equals("Game Over") || board.getLvl() == 3 || !controller.isGameStarted()){
            newGameButton.setText("New Game");
            newGameButton.setActionCommand("New game");

        }
        else {
            newGameButton.setText("Next level");
            newGameButton.setActionCommand("Next level");
        }

        newGameButton.setBounds(getWidth() / 2 - 150, getHeight()*6/10, 300, getHeight()/10);
        this.add(newGameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("Exit");
        exitButton.setBounds(getWidth() / 2 - 150, getHeight()*8/10, 300, getHeight()/10);
        add(exitButton);
        SwingView v = this;

        ActionListener mainMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("New game".equals(e.getActionCommand())) {

                    v.removeAll();
                    Board b = new Board();
                    controller.setGameStarted(true);
                    controller.resetController(b);
                    setModel(b);
                    controller.setView(v);
                    v.requestFocus();
                }
                if("Next level".equals(e.getActionCommand())) {
                    v.removeAll();
                    board.initBoard();
                    controller.resetController(board);
                    controller.setView(v);
                    v.requestFocus();
                }
                if ("Exit".equals(e.getActionCommand())) {
                    System.exit(0);
                }
            }
        };

        newGameButton.addActionListener(mainMenuListener);
        exitButton.addActionListener(mainMenuListener);}
    }

    private void createContinueButton() {

       SwingView v = this;

       KeyListener PauseListener = new KeyListener() {
           @Override
           public void keyTyped(KeyEvent e) {

           }

           @Override
           public void keyPressed(KeyEvent e) {
               if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                   v.removeKeyListener(this);
                   controller.unpauseGame();

               }
           }
           @Override
           public void keyReleased(KeyEvent e) {

           }
       };
       addKeyListener(PauseListener);
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
    }

    private void paintPaddle(Graphics2D g) {
        Paddle p = board.getPaddle();
        g.drawImage(p.getImage(),p.getX(),p.getY(),null);
    }

    private void paintBall(Graphics2D g) {
        Ball b = board.getBall();
        g.drawImage(b.getImage(),b.getX(),b.getY(),this);
    }

    private void fillBackground(Graphics2D g)
    {
        ImageIcon img = new ImageIcon("images/background.png");
        g.drawImage(img.getImage(),0,0,this);
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

    public Controller getController() {
        return controller;
    }

    public int getImgHeight() {
        ImageIcon img = new ImageIcon("images/background.png");
        return img.getImage().getHeight(null);
    }

}
