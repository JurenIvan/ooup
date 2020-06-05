package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.state.SelectShapeState;
import lab4.hr.fer.zemris.ooup.state.StateManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectShapeAction extends AbstractAction {

    private final DocumentModel model;
    private final StateManager stateManager;

    public SelectShapeAction(DocumentModel model, StateManager stateManager) {
        super("Select");
        this.model = model;
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stateManager.setState(new SelectShapeState(model));
    }
}
