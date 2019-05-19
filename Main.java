package proz.project;

import javax.swing.*;

import proz.project.controller.Controller;
import proz.project.model.Board;
import proz.project.model.GameMainMenu;
import proz.project.view.SwingView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static Board createModel() {
        return new Board();
    }

    private static Controller createController(Board b) {
        return new Controller(b);
    }

    private static SwingView createModelViewController() {
        Board b = createModel();
        Controller c = createController(b);
        SwingView v = new SwingView();
        v.setModel(b);
        v.setController(c);
        c.setView(v);
        return v;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Brick Breaker") {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 600);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(frame.getPreferredSize());
        frame.setResizable(false);
        createMainMenu(frame);
        frame.setVisible(true);
    }

    private static void startGame(JFrame frame) {
        frame.getContentPane().removeAll();
        SwingView v = createModelViewController();
        frame.getContentPane().add(v);
        frame.pack();
        v.requestFocus();
    }



    private static void createMainMenu(JFrame frame) {
        GameMainMenu mainMenu = new GameMainMenu();
        mainMenu.setLayout(null);

        JButton newGameButton = new JButton("New game");
        newGameButton.setActionCommand("New game");
        newGameButton.setBounds(frame.getWidth() / 2 - 150, frame.getHeight()/5, 300, frame.getHeight()/10);
        mainMenu.add(newGameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("Exit");
        exitButton.setBounds(frame.getWidth() / 2 - 150, frame.getHeight()*3/5, 300, frame.getHeight()/10);
        mainMenu.add(exitButton);

        ActionListener mainMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("New game".equals(e.getActionCommand())) {
                    startGame(frame);
                }
                if ("Exit".equals(e.getActionCommand())) {
                    System.exit(0);
                }
            }
        };

        newGameButton.addActionListener(mainMenuListener);
        exitButton.addActionListener(mainMenuListener);
        frame.add(mainMenu);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
