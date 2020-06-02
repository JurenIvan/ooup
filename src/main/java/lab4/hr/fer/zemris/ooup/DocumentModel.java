package lab4.hr.fer.zemris.ooup;

import lab4.hr.fer.zemris.ooup.listeners.DocumentModelListener;
import lab4.hr.fer.zemris.ooup.listeners.GraphicalObjectListener;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.MAX_VALUE;
import static java.util.Collections.unmodifiableList;

public class DocumentModel {

    public final static double SELECTION_PROXIMITY = 10;

    // Kolekcija svih grafičkih objekata:
    private final List<GraphicalObject> objects = new ArrayList<>();

    // Read-Only proxy oko kolekcije grafičkih objekata:
    private final List<GraphicalObject> roObjects = unmodifiableList(objects);

    // Kolekcija prijavljenih promatrača:
    private final List<DocumentModelListener> listeners = new ArrayList<>();

    // Kolekcija selektiranih objekata:
    private final List<GraphicalObject> selectedObjects = new ArrayList<>();

    // Read-Only proxy oko kolekcije selektiranih objekata:
    private final List<GraphicalObject> roSelectedObjects = unmodifiableList(selectedObjects);

    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            if (go.isSelected())
                selectedObjects.add(go);
            else
                selectedObjects.remove(go);

            notifyListeners();
        }
    };

    public DocumentModel() {

    }

    public void clear() {
        objects.forEach(e -> e.removeGraphicalObjectListener(goListener));
        objects.clear();
        selectedObjects.clear();
        notifyListeners();
    }

    // Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);

        if (obj.isSelected())   // todo why ?! pazite je li već selektiran
            selectedObjects.add(obj);

        obj.addGraphicalObjectListener(goListener);
        notifyListeners();
    }

    // Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
    public void removeGraphicalObject(GraphicalObject obj) {
        objects.remove(obj);
        if (obj.isSelected()) obj.setSelected(false); // todo why ?! pazite je li već selektiran
        obj.removeGraphicalObjectListener(goListener);
        notifyListeners();
    }

    public List<GraphicalObject> list() {
        return roObjects;
    }

    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    public void increaseZ(GraphicalObject go) {
        var index = objects.indexOf(go);
        if (index == objects.size())
            return;
        objects.remove(index);
        objects.add(index+1, go);
    }

    public void decreaseZ(GraphicalObject go) {
        var index = objects.indexOf(go);
        if (index == 0)
            return;
        objects.add(index - 1, go);
        objects.remove(index + 1);
    }

    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        double closest = MAX_VALUE;
        int selectedIndex = -1;

        for (int i = 0; i < objects.size(); i++) {
            var distance = objects.get(i).selectionDistance(mousePoint);
            if (distance == 0) return objects.get(i);
            if (distance < closest) {
                closest = distance;
                selectedIndex = i;
            }
        }
        return closest < SELECTION_PROXIMITY ? objects.get(selectedIndex) : null;
    }

    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        for (int i = 0; i < object.getNumberOfHotPoints(); i++)
            if (object.getHotPointDistance(i, mousePoint) < SELECTION_PROXIMITY) return i;
        return -1;
    }

    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        listeners.forEach(DocumentModelListener::documentChange);
    }

    public void deselect(GraphicalObject graphicalObject) {
        selectedObjects.remove(graphicalObject);
        notifyListeners();
    }

    public void deselect() {
        for (int i = 0; i < objects.size(); i++)
            objects.get(i).setSelected(false);
        selectedObjects.clear();
        notifyListeners();
    }
}
