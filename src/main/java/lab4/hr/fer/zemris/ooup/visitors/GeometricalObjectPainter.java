package lab4.hr.fer.zemris.ooup.visitors;

import lab4.hr.fer.zemris.ooup.model.primitives.Rectangle;
import lab4.hr.fer.zemris.ooup.model.shapes.LineSegment;
import lab4.hr.fer.zemris.ooup.model.shapes.Oval;
import lab4.hr.fer.zemris.ooup.renderer.G2DRendererImpl;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;

import java.awt.*;

import static java.awt.Color.BLUE;


public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    private final Graphics g2d;
    private Renderer renderer;

    public GeometricalObjectPainter(Graphics g2d) {
        this.g2d = g2d;
        this.renderer = new G2DRendererImpl(g2d);
    }

    @Override
    public void visit(LineSegment line) {
        g2d.setColor(BLUE);
        g2d.drawLine(line.getHotPoint(0).getX(), line.getHotPoint(0).getY(), line.getHotPoint(1).getX(), line.getHotPoint(1).getY());
    }

    @Override
    public void visit(Oval oval) {
        g2d.setColor(BLUE);
        Rectangle bb = oval.getBoundingBox();
        g2d.drawOval(bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
    }
}
