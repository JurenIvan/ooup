package lab4.hr.fer.zemris.ooup.renderer;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;

import java.awt.*;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class G2DRendererImpl implements Renderer {

    private Graphics g2d;

    public G2DRendererImpl(Graphics g2d) {
        this.g2d = g2d;
    }

    @Override
    public void drawLine(Point s, Point e) {
        g2d.setColor(BLUE);
        g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
    }

    @Override
    public void fillPolygon(Point[] points) {
        g2d.setColor(BLUE);
        int[] x = new int[points.length];
        int[] y = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            x[i] = points[i].getX();
            y[i] = points[i].getY();
        }

        g2d.fillPolygon(x, y, points.length);
        g2d.setColor(RED);
        g2d.drawPolygon(x, y, points.length);
    }
}
