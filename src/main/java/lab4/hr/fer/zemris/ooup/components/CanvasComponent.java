package lab4.hr.fer.zemris.ooup.components;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.GUI;
import lab4.hr.fer.zemris.ooup.listeners.DocumentModelListener;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.renderer.G2DRendererImpl;
import lab4.hr.fer.zemris.ooup.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class CanvasComponent extends JComponent implements DocumentModelListener {

    private final DocumentModel model;
    private final GUI gui;

    public CanvasComponent(DocumentModel model, GUI gui) {
        this.model = model;
        this.gui = gui;
    }

    @Override
    protected void paintComponent(Graphics g) {
   //     GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
        Renderer renderer = new G2DRendererImpl(g);

        for (GraphicalObject object : model.list()) {
            //    object.accept(painter);
            object.render(renderer);
            gui.currentState().afterDraw(renderer, object);
        }
        gui.currentState().afterDraw(renderer);
        requestFocusInWindow();
    }

    @Override
    public void documentChange() {
        repaint();
    }
}
