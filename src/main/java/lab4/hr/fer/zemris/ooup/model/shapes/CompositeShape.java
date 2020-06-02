package lab4.hr.fer.zemris.ooup.model.shapes;

import lab4.hr.fer.zemris.ooup.listeners.GraphicalObjectListener;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;
import lab4.hr.fer.zemris.ooup.visitors.GeometricalObjectVisitor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.MAX_VALUE;
import static java.util.stream.Collectors.toList;

public class CompositeShape implements GraphicalObject {

    private List<GraphicalObject> children;
    private boolean selected;
    private List<GraphicalObjectListener> listeners = new ArrayList<>();

    public CompositeShape(List<GraphicalObject> children) {
        this.children = children;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionChanged();
    }

    @Override
    public int getNumberOfHotPoints() {
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        throw new UnsupportedOperationException("Composite has no hotpoints");
    }

    @Override
    public void setHotPoint(int index, Point point) {
        throw new UnsupportedOperationException("Composite has no hotpoints");
    }

    @Override
    public boolean isHotPointSelected(int index) {
        throw new UnsupportedOperationException("Composite has no hotpoints");
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        throw new UnsupportedOperationException("Composite has no hotpoints");
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        throw new UnsupportedOperationException("Composite has no hotpoints");
    }

    @Override
    public void translate(Point delta) {
        for (int i = 0; i < children.size(); i++)
            children.get(i).translate(delta);
        notifyObjectChanged();
    }

    @Override
    public Rectangle getBoundingBox() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < children.size(); i++)
            rectangles.add(children.get(i).getBoundingBox());

        if (rectangles.isEmpty())
            return null;

        int xMin = rectangles.stream().mapToInt(Rectangle::getX).min().getAsInt();
        int xMax = rectangles.stream().mapToInt(e -> e.getX() + e.getWidth()).max().getAsInt();
        int yMin = rectangles.stream().mapToInt(Rectangle::getY).min().getAsInt();
        int yMax = rectangles.stream().mapToInt(e -> e.getY() + e.getHeight()).max().getAsInt();
        return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return children.stream().mapToDouble(e -> e.selectionDistance(mousePoint)).min().orElse(MAX_VALUE);
    }

    @Override
    public void render(Renderer r) {
        children.forEach(e -> e.render(r));
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        children.forEach(e -> e.accept(v));
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    public void notifyObjectChanged() {
        listeners.forEach(e -> e.graphicalObjectChanged(this));
    }

    public void notifySelectionChanged() {
        listeners.forEach(e -> e.graphicalObjectSelectionChanged(this));
    }

    @Override
    public String getShapeName() {
        return "composite";
    }

    @Override
    public GraphicalObject duplicate() {
        return new CompositeShape(children.stream().map(GraphicalObject::duplicate).collect(toList()));
    }

    public List<GraphicalObject> getChildren() {
        return children;
    }
}
