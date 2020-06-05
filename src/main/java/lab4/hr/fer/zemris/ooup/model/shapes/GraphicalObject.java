package lab4.hr.fer.zemris.ooup.model.shapes;

import lab4.hr.fer.zemris.ooup.listeners.GraphicalObjectListener;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;
import lab4.hr.fer.zemris.ooup.visitors.GeometricalObjectVisitor;

import java.util.List;
import java.util.Stack;

public interface GraphicalObject {

    boolean isSelected();

    void setSelected(boolean selected);

    int getNumberOfHotPoints();

    Point getHotPoint(int index);

    void setHotPoint(int index, Point point);

    boolean isHotPointSelected(int index);

    void setHotPointSelected(int index, boolean selected);

    double getHotPointDistance(int index, Point mousePoint);

    void translate(Point delta);

    Rectangle getBoundingBox();

    double selectionDistance(Point mousePoint);

    void render(Renderer r);

    void accept(GeometricalObjectVisitor v);

    public void addGraphicalObjectListener(GraphicalObjectListener l);

    public void removeGraphicalObjectListener(GraphicalObjectListener l);

    String getShapeName();

    GraphicalObject duplicate();

    //     Podrška za snimanje i učitavanje
    public String getShapeID();

    public void load(Stack<GraphicalObject> stack, String data);

    public void save(List<String> rows);
}
