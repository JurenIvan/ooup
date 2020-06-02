package lab4.hr.fer.zemris.ooup;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;

public interface Renderer {
    void drawLine(Point s, Point e);

    void fillPolygon(Point[] points);
}
