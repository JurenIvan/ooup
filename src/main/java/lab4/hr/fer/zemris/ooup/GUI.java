package lab4.hr.fer.zemris.ooup;

import lab4.hr.fer.zemris.ooup.actions.*;
import lab4.hr.fer.zemris.ooup.components.CanvasComponent;
import lab4.hr.fer.zemris.ooup.model.primitives.Point;
import lab4.hr.fer.zemris.ooup.model.shapes.GraphicalObject;
import lab4.hr.fer.zemris.ooup.state.IdleState;
import lab4.hr.fer.zemris.ooup.state.State;
import lab4.hr.fer.zemris.ooup.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;
import static java.awt.event.KeyEvent.VK_ESCAPE;

public class GUI extends JFrame {

    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 800;

    private final DocumentModel model = new DocumentModel();
    private final CanvasComponent canvas = new CanvasComponent(model, this);
    private final List<GraphicalObject> objects;
    private final StateManager stateManager = new StateManager();

    public GUI(List<GraphicalObject> objects) {
        this.objects = objects;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);
        model.addDocumentModelListener(canvas);

        initGui();
    }

    private void initGui() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, CENTER);

        setupKeyboardListeners();
        setMouseListeners();
        createToolbar(cp);

    }

    private void setMouseListeners() {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stateManager.getCurrentState().mouseDown(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stateManager.getCurrentState().mouseUp(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                stateManager.getCurrentState().mouseDragged(new Point(e.getX(), e.getY()));
            }
        });
    }

    private void setupKeyboardListeners() {
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != VK_ESCAPE)
                    stateManager.getCurrentState().keyPressed(e.getKeyCode());
                else
                    stateManager.setState(new IdleState());
            }
        });
    }

    private void createToolbar(Container cp) {
        JToolBar tb = new JToolBar();

        ButtonGroup group = new ButtonGroup();
        for (GraphicalObject object : objects) {
            JToggleButton button = new JToggleButton(new AddShapeAction(object, model, stateManager));
            tb.add(button);
            group.add(button);
        }
        JToggleButton selectButton = new JToggleButton(new SelectShapeAction(model, stateManager));
        tb.add(selectButton);
        group.add(selectButton);

        JToggleButton deleteButton = new JToggleButton(new DeleteShapeAction(model, stateManager));
        tb.add(deleteButton);
        group.add(deleteButton);

        JButton exportButton = new JButton(new SvgExportAction(model, this));
        tb.add(exportButton);

        JButton saveButton = new JButton(new SaveAction(model, this));
        tb.add(saveButton);


        tb.setFocusable(false);
        cp.add(tb, PAGE_START);
    }

    public State currentState() {
        return stateManager.getCurrentState();
    }
}
