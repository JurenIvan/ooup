package lab4.hr.fer.zemris.ooup.model.shapes;

import lab4.hr.fer.zemris.ooup.listeners.GraphicalObjectListener;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static lab4.hr.fer.zemris.ooup.utils.GeometryUtil.distanceFromPoint;

public abstract class AbstractGraphicalObject implements GraphicalObject {

    private Point[] hotPoints;
    private boolean[] hotPointsSelected;
    private boolean hotPointSelected;
    private boolean selected;

    private final List<GraphicalObjectListener> listeners = new ArrayList<>();

    public AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
    }

    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    public void setHotPoint(int index, Point point) {
        hotPoints[index] = point;
    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return distanceFromPoint(hotPoints[index], mousePoint);
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointsSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointsSelected[index] = selected;
        notifySelectionChanged();
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
    public void translate(Point delta) {
        hotPoints = stream(hotPoints).map(e -> e.translate(delta)).toArray(Point[]::new);
        notifyObjectChanged();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.add(graphicalObjectListener);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.remove(graphicalObjectListener);
    }

    public void notifySelectionChanged() {
        listeners.forEach(e -> e.graphicalObjectSelectionChanged(this));
    }

    public void notifyObjectChanged() {
        listeners.forEach(e -> e.graphicalObjectChanged(this));
    }
}
