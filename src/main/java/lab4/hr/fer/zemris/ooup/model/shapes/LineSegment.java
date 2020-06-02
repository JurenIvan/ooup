package lab4.hr.fer.zemris.ooup.model.shapes;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;
import lab4.hr.fer.zemris.ooup.utils.GeometryUtil;
import lab4.hr.fer.zemris.ooup.visitors.GeometricalObjectVisitor;

import java.util.List;

import static lab4.hr.fer.zemris.ooup.utils.GeometryUtil.*;

public class LineSegment extends AbstractGraphicalObject {

    public LineSegment(Point startPoint, Point endPoint) {
        super(List.of(startPoint, endPoint).stream().toArray(Point[]::new));
    }

    public LineSegment() {
        super(new Point[]{new Point(0, 0), new Point(10, 0)});
    }

    @Override
    public Rectangle getBoundingBox() {
        int xMin = Math.min(getHotPoint(0).getX(), getHotPoint(1).getX());
        int xMax = Math.max(getHotPoint(0).getX(), getHotPoint(1).getX());
        int yMin = Math.min(getHotPoint(0).getY(), getHotPoint(1).getY());
        int yMax = Math.max(getHotPoint(0).getY(), getHotPoint(1).getY());
        return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }
}
