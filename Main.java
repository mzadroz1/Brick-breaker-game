package proz.project;

import javax.swing.*;

import proz.project.controller.Controller;
import proz.project.model.Board;
import proz.project.view.SwingView;

import java.awt.*;

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
        frame.setVisible(true);
        SwingView v = createModelViewController();
        frame.getContentPane().add(v);
        frame.pack();
        v.requestFocus();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
