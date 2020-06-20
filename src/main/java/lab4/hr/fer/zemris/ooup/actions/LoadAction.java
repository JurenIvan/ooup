package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.GUI;
import lab4.hr.fer.zemris.ooup.model.shapes.CompositeShape;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.model.shapes.LineSegment;
import lab4.hr.fer.zemris.ooup.model.shapes.Oval;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.util.Map.of;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;

public class LoadAction extends AbstractAction {

    private final GUI gui;
    private final DocumentModel model;
    private static final Map<String, GraphicalObject> prototypes = of("@OVAL", new Oval(), "@LINE", new LineSegment(), "@COMP", new CompositeShape(new ArrayList<>()));

    public LoadAction(DocumentModel model, GUI gui) {
        super("Load .svg");
        this.model = model;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        List<String> rows = new LinkedList<>();
        model.list().forEach(e -> e.save(rows));

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Load file");
        if (jfc.showOpenDialog(gui) != APPROVE_OPTION) {
            showMessageDialog(gui, "Nothing loaded", "Warning", INFORMATION_MESSAGE);
        } else {
            try {
                Stack<GraphicalObject> stack = new Stack<>();
                List<String> lines = Files.readAllLines(jfc.getSelectedFile().toPath());
                lines.forEach(e -> prototypes.get(e.substring(0, 5)).load(stack, e));
                model.clear();
                stack.forEach(model::addGraphicalObject);
                model.notifyListeners();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(gui, "Nothing loaded", "Error", ERROR_MESSAGE);
            }
        }
    }

}
