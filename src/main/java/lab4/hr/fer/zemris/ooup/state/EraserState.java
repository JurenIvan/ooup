package lab4.hr.fer.zemris.ooup.state;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EraserState extends StateAdapter {

    private final List<Point> trail = new LinkedList<>();
    private final DocumentModel model;
    private final StateManager stateManager;

    public EraserState(DocumentModel model, StateManager stateManager) {
        this.model = model;
        this.stateManager = stateManager;
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        trail.stream()
                .map(trailPoint -> model.list().stream().filter(go -> go.selectionDistance(trailPoint) < 3).collect(toList()))
                .flatMap(Collection::stream)
                .filter(e -> e != null)
                .forEach(model::removeGraphicalObject);
        stateManager.setState(new IdleState());
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        trail.add(mousePoint);
        model.notifyListeners();
    }

    @Override
    public void afterDraw(Renderer r) {
        for (int i = 1; i < trail.size(); i++)
            r.drawLine(trail.get(i), trail.get(i - 1));

        model.notifyListeners();
    }
}
