package lab4.hr.fer.zemris.ooup.model;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;

import java.util.List;

public class Oval extends AbstractGraphicalObject {

    public Oval(Point down, Point right) {
        super(List.of(down, right).stream().toArray(Point[]::new));
    }

    public Oval() {
        super(new Point[]{new Point(10, 0), new Point(0, 10)});
    }

    @Override
    public Rectangle getBoundingBox() {
        Point d = getHotPoint(0);
        Point r = getHotPoint(1);

        return new Rectangle(2 * d.getX() - r.getX(),
                2 * r.getY() - d.getY(),
                2 * (r.getX() - d.getX()),
                2 * (d.getY() - r.getY()));
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return -1;  //todo (find point on eclipse where intersect and calculate)
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(getHotPoint(0), getHotPoint(1));
    }
}
