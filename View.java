package proz.project.view;

import proz.project.controller.Controller;
import proz.project.model.Board;

public interface View {
    void updateView();

    void setModel(Board board);
    void setController(Controller c);

    int getWidth();
}
