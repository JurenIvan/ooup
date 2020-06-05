package lab4.hr.fer.zemris.ooup.model.shapes;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;
import lab4.hr.fer.zemris.ooup.visitors.GeometricalObjectVisitor;

import java.util.List;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Math.*;
import static lab4.hr.fer.zemris.ooup.utils.GeometryUtil.distanceFromPoint;

public class Oval extends AbstractGraphicalObject {

    private static final int NUM_POINTS = 180;

    public Oval(Point down, Point right) {
        super(List.of(down, right).toArray(new Point[0]));
    }

    public Oval() {
        super(new Point[]{new Point(0, 10), new Point(10, 0)});
    }

    @Override
    public Rectangle getBoundingBox() {
        Point d = getHotPoint(0);
        Point r = getHotPoint(1);

        int x, y, width, height;

        x = 2 * d.getX() - r.getX();
        y = 2 * r.getY() - d.getY();
        width = 2 * (r.getX() - d.getX());
        height = 2 * (d.getY() - r.getY());

        if (height < 0) {
            y = y + height;
            height = -height;
        }

        if (width < 0) {
            x = x + width;
            width = -width;
        }
        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        Rectangle bb = getBoundingBox();
        var center = new Point(bb.getX() + bb.getWidth() / 2, bb.getY() + bb.getHeight() / 2);

        var difVect = mousePoint.difference(center);
        if (difVect.getX() * difVect.getX() + difVect.getY() * difVect.getY() < pow(bb.getWidth() / 2.0, 2) + pow(bb.getHeight() / 2.0, 2)) {
            return 0;
        }
        Point[] points = getPoints();

        double min = MAX_VALUE;
        for (Point point : points) {
            double dist = distanceFromPoint(point.getX(), point.getY(), mousePoint.getX(), mousePoint.getY());
            if (dist < min)
                min = dist;
        }
        return min;
    }

    @Override
    public void render(Renderer r) {
        r.fillPolygon(getPoints());
    }

    private Point[] getPoints() {
        var points = new Point[NUM_POINTS];
        Rectangle bb = getBoundingBox();
        var center = new Point(bb.getX() + bb.getWidth() / 2, bb.getY() + bb.getHeight() / 2);


        for (int i = 0; i < NUM_POINTS; ++i) {
            final double angle = toRadians(((double) i / NUM_POINTS) * 360);

            points[i] = new Point(
                    (int) (cos(angle) * bb.getWidth() / 2 + center.getX()),
                    (int) (sin(angle) * bb.getHeight() / 2 + center.getY())
            );
        }
        return points;
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }
}
