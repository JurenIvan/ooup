package lab4.hr.fer.zemris.ooup;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.shapes.LineSegment;
import lab4.hr.fer.zemris.ooup.model.shapes.Oval;

import javax.swing.*;
import java.util.Map;

public class PaintRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(Map.of("@LINE", new LineSegment(new Point(0, 0), new Point(50, 50)), "@OVAL", new Oval(new Point(0, 50), new Point(50, 0)))).setVisible(true));
    }
}
