package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.state.AddShapeState;
import lab4.hr.fer.zemris.ooup.state.StateManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddShapeAction extends AbstractAction {

    private GraphicalObject graphicalObject;
    private DocumentModel model;
    private StateManager stateManager;

    public AddShapeAction(GraphicalObject graphicalObject, DocumentModel model, StateManager stateManager) {
        super(graphicalObject.getShapeName());
        this.graphicalObject = graphicalObject;
        this.model = model;
        this.stateManager = stateManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stateManager.setState(new AddShapeState(graphicalObject, model));
    }
}
