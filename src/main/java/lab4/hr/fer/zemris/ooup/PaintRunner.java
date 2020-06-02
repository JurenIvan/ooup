package lab4.hr.fer.zemris.ooup;

import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.shapes.LineSegment;
import lab4.hr.fer.zemris.ooup.model.shapes.Oval;

import javax.swing.*;
import java.util.List;

public class PaintRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(List.of(new LineSegment(new Point(0,0),new Point(50,50)), new Oval(new Point(0,0),new Point(50,50)))).setVisible(true));
    }
}
