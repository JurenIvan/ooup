package lab4.hr.fer.zemris.ooup.utils;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;

import static java.lang.Math.*;

// looked at https://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
public class GeometryUtil {

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        return sqrt(distToSegmentSquared(p.getX(), p.getY(), s.getX(), s.getY(), e.getX(), e.getY()));
    }

    public static double distToSegmentSquared(double px, double py, double lx1, double ly1, double lx2, double ly2) {
        double line_dist = distanceFromPointSquared(lx1, ly1, lx2, ly2);
        if (line_dist == 0) return distanceFromPointSquared(px, py, lx1, ly1);
        double t = ((px - lx1) * (lx2 - lx1) + (py - ly1) * (ly2 - ly1)) / line_dist;
        t = max(min(1, t), 0);
        return distanceFromPointSquared(px, py, lx1 + t * (lx2 - lx1), ly1 + t * (ly2 - ly1));
    }

    public static double distanceFromPoint(double x1, double y1, double x2, double y2) {
        return sqrt(distanceFromPointSquared(x1, y1, x2, y2));
    }

    public static double distanceFromPoint(Point point1, Point point2) {
        return sqrt(pow(point1.getX() - point2.getX(), 2) + pow(point1.getY() - point2.getY(), 2));
    }

    public static double distanceFromPointSquared(double x1, double y1, double x2, double y2) {
        return pow(x1 - x2, 2) + pow(y1 - y2, 2);
    }
}
