package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.state.EraserState;
import lab4.hr.fer.zemris.ooup.state.StateManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EraserAction extends AbstractAction {

    private final DocumentModel model;
    private final StateManager stateManager;

    public EraserAction(DocumentModel model, StateManager stateManager) {
        super("Delete");
        this.model = model;
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stateManager.setState(new EraserState(model));
    }
}
