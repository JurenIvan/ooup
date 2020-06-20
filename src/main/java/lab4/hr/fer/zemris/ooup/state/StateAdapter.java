package lab4.hr.fer.zemris.ooup.state;

import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;

public class StateAdapter implements State {

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
    }

    @Override
    public void mouseDragged(Point mousePoint) {
    }

    @Override
    public void keyPressed(int keyCode) {
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
    }

    @Override
    public void afterDraw(Renderer r) {
    }

    @Override
    public void onLeaving() {
    }
}
