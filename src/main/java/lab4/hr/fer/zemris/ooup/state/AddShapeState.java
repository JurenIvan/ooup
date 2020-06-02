package lab4.hr.fer.zemris.ooup.state;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;

public class AddShapeState extends StateAdapter {

    private final GraphicalObject prototype;
    private final DocumentModel model;

    public AddShapeState(GraphicalObject prototype, DocumentModel model) {
        this.prototype = prototype;
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject graphicalObject = prototype.duplicate();
        graphicalObject.translate(mousePoint);
        model.addGraphicalObject(graphicalObject);
    }
}
