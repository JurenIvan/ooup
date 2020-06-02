package lab4.hr.fer.zemris.ooup.state;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.model.shapes.CompositeShape;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static java.lang.Double.MAX_VALUE;

public class SelectShapeState extends StateAdapter {

    private static final int CONST = 3;
    private final DocumentModel model;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        var go = model.findSelectedGraphicalObject(mousePoint);
        if (ctrlDown && go != null) {
            go.setSelected(true);
        } else if (go != null) {
            model.deselect();
            go.setSelected(true);
        } else {
            model.deselect();
            model.notifyListeners();
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (model.getSelectedObjects().size() != 1)
            return;

        GraphicalObject go = model.findSelectedGraphicalObject(mousePoint);
        if (go == null)
            return;

        int index = -1;
        double closest = MAX_VALUE;
        for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
            double dist = go.getHotPointDistance(i, mousePoint);
            if (dist < closest) {
                index = i;
                closest = dist;
            }
        }
        if (closest > 20)
            return;

        go.setHotPoint(index, mousePoint);
        model.notifyListeners();
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case 107:
                if (model.getSelectedObjects().size() == 1)
                    model.increaseZ(model.getSelectedObjects().get(0));
                break;
            case 109:
                if (model.getSelectedObjects().size() == 1)
                    model.decreaseZ(model.getSelectedObjects().get(0));
                break;
            case VK_UP:
                model.getSelectedObjects().forEach(e -> movePoints(e, 0, -5));
                break;
            case VK_DOWN:
                model.getSelectedObjects().forEach(e -> movePoints(e, 0, 5));
                break;
            case VK_LEFT:
                model.getSelectedObjects().forEach(e -> movePoints(e, -5, 0));
                break;
            case VK_RIGHT:
                model.getSelectedObjects().forEach(e -> movePoints(e, 5, 0));
                break;
            case VK_G:
                List<GraphicalObject> selectedCopy = new ArrayList<>(model.getSelectedObjects());
                for (GraphicalObject graphicalObject : selectedCopy) model.removeGraphicalObject(graphicalObject);
                model.addGraphicalObject(new CompositeShape(selectedCopy));
                break;
            case VK_U:
                if (model.getSelectedObjects().size() != 1 || !(model.getSelectedObjects().get(0) instanceof CompositeShape))
                    break;
                List<GraphicalObject> upacked = (((CompositeShape) model.getSelectedObjects().get(0)).getChildren());
                model.removeGraphicalObject(model.getSelectedObjects().get(0));
                upacked.forEach(model::addGraphicalObject);
                break;
        }
        model.notifyListeners();
    }

    private void movePoints(GraphicalObject e, int x, int y) {
        for (int j = 0; j < e.getNumberOfHotPoints(); j++)
            e.setHotPoint(j, e.getHotPoint(j).translate(new Point(x, y)));
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject object) {
        if (!object.isSelected())
            return;
        drawLittleRectangle(r, object.getBoundingBox());
    }

    @Override
    public void afterDraw(Renderer r) {
        if (model.getSelectedObjects().size() != 1)
            return;

        GraphicalObject selected = model.getSelectedObjects().get(0);
        for (int i = 0; i < selected.getNumberOfHotPoints(); i++) {
            Point p = selected.getHotPoint(i);
            drawLittleRectangle(r, new Rectangle(p.getX() - CONST, p.getY() - CONST, 2 * CONST, 2 * CONST));
        }
    }

    private void drawLittleRectangle(Renderer r, Rectangle bb) {
        r.drawLine(new Point(bb.getX(), bb.getY()), new Point(bb.getX() + bb.getWidth(), bb.getY()));
        r.drawLine(new Point(bb.getX() + bb.getWidth(), bb.getY()), new Point(bb.getX() + bb.getWidth(), bb.getY() + bb.getHeight()));
        r.drawLine(new Point(bb.getX() + bb.getWidth(), bb.getY() + bb.getHeight()), new Point(bb.getX(), bb.getY() + bb.getHeight()));
        r.drawLine(new Point(bb.getX(), bb.getY() + bb.getHeight()), new Point(bb.getX(), bb.getY()));
    }

    @Override
    public void onLeaving() {
        model.deselect();
    }
}
