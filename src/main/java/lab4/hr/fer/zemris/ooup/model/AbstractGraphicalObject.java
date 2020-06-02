package lab4.hr.fer.zemris.ooup.model;

import lab4.hr.fer.zemris.ooup.GeometryUtil;
import lab4.hr.fer.zemris.ooup.listeners.GraphicalObjectListener;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.*;

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
        return GeometryUtil.distanceFromPoint(hotPoints[index], mousePoint);
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointsSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointsSelected[index] = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void translate(Point delta) {
        hotPoints = stream(hotPoints).map(e -> e.translate(delta)).toArray(Point[]::new);
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.remove(graphicalObjectListener);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener graphicalObjectListener) {
        listeners.remove(graphicalObjectListener);
    }
}
