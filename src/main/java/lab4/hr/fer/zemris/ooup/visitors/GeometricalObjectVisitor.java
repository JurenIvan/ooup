package lab4.hr.fer.zemris.ooup.visitors;

import lab4.hr.fer.zemris.ooup.model.shapes.LineSegment;
import lab4.hr.fer.zemris.ooup.model.shapes.Oval;

public interface GeometricalObjectVisitor {

    void visit(LineSegment lineSegment);

    void visit(Oval oval);
}
