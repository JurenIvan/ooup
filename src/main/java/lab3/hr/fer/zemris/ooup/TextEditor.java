package lab3.hr.fer.zemris.ooup;

import lab3.hr.fer.zemris.ooup.actions.*;
import lab3.hr.fer.zemris.ooup.components.StatusBarComponent;
import lab3.hr.fer.zemris.ooup.components.TextComponent;
import lab3.hr.fer.zemris.ooup.model.UndoManager;
import lab3.hr.fer.zemris.ooup.plugins.PluginAction;
import lab3.hr.fer.zemris.ooup.plugins.PluginFactory;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static javax.swing.KeyStroke.getKeyStroke;

@Data
public class TextEditor extends JFrame {

    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 800;

    private final TextEditorModel textEditorModel;
    private final TextComponent textComponent;
    private final UndoManager undoManager = UndoManager.getInstance();

    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textEditorModel.notifyTimeObservers();
        }
    });

    public TextEditor(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
        this.textComponent = new TextComponent(textEditorModel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);

        initGui();
        timer.start();
    }

    private void initGui() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(textComponent, BorderLayout.CENTER);

        setupKeyboardListeners();

        createMenus(cp);
        createStatusbar(cp);
        loadPlugins();
        fireNotifiers();
    }

    private void loadPlugins() {

    }

    private void fireNotifiers() {
        textEditorModel.notifyTimeObservers();
        textEditorModel.notifyCursorObservers();
        textEditorModel.notifyTextObservers();
        textEditorModel.notifySelectionObservers();
        textEditorModel.getClipboardStack().notifyObservers();
        textEditorModel.getUndoManager().notifyUndoObservers();
        textEditorModel.getUndoManager().notifyRedoObservers();
    }


    private void setupKeyboardListeners() {
        initCursorListeners();
        initDeleteListeners();
        initInputListeners();
        initCopyPasteListeners();
        initStackListeners();
    }

    private void initDeleteListeners() {
        textComponent.getInputMap().put(getKeyStroke(VK_DELETE, 0, false), "delete");
        textComponent.getActionMap().put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textEditorModel.getSelectionRange() != null)
                    textEditorModel.deleteRange(textEditorModel.getSelectionRange());
                else textEditorModel.deleteAfter();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_BACK_SPACE, 0, false), "backspace");
        textComponent.getActionMap().put("backspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textEditorModel.getSelectionRange() != null)
                    textEditorModel.deleteRange(textEditorModel.getSelectionRange());
                else textEditorModel.deleteBefore();
            }
        });
    }

    private void initCursorListeners() {
        textComponent.getInputMap().put(getKeyStroke(VK_UP, 0, false), "up");
        textComponent.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorUp();
                textEditorModel.setSelectionRange(null);
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_DOWN, 0, false), "down");
        textComponent.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorDown();
                textEditorModel.setSelectionRange(null);
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_LEFT, 0, false), "left");
        textComponent.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorLeft();
                textEditorModel.setSelectionRange(null);
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_RIGHT, 0, false), "right");
        textComponent.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorRight();
                textEditorModel.setSelectionRange(null);
            }
        });

        textEditorModel.addCursorObserver(textComponent);  //think of textComponent.revalidate();

        textComponent.getInputMap().put(getKeyStroke(VK_UP, SHIFT_DOWN_MASK, false), "shift_up");
        textComponent.getActionMap().put("shift_up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveSelectionUp();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_DOWN, SHIFT_DOWN_MASK, false), "shift_down");
        textComponent.getActionMap().put("shift_down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveSelectionDown();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_LEFT, SHIFT_DOWN_MASK, false), "shift_left");
        textComponent.getActionMap().put("shift_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveSelectionLeft();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_RIGHT, SHIFT_DOWN_MASK, false), "shift_right");
        textComponent.getActionMap().put("shift_right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveSelectionRight();
            }
        });

        textEditorModel.addTextObserver(textComponent);
    }

    private void initInputListeners() {
        textComponent.addKeyListener(new KeyAdapter() {

            final List<Integer> usedEvents = List.of(VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_CAPS_LOCK, VK_BACK_SPACE, VK_DELETE, VK_SHIFT, VK_CONTROL, VK_ALT, VK_ALT_GRAPH, VK_ESCAPE, VK_NUM_LOCK);
            final List<Integer> fKeys = List.of(VK_F1, VK_F2, VK_F3, VK_F4, VK_F5, VK_F6, VK_F7, VK_F8, VK_F9, VK_F10, VK_F11, VK_F12);

            @Override
            public void keyPressed(KeyEvent e) {
//                Character.isDigit(e.getKeyChar()) || Character.isAlphabetic(e.getKeyChar());
                if (!usedEvents.contains(e.getKeyCode())
                        && !fKeys.contains(e.getKeyCode())
                        && !e.isControlDown())
                    textEditorModel.insert(e.getKeyChar());
            }
        });
    }

    private void initCopyPasteListeners() {
        textComponent.getInputMap().put(getKeyStroke(VK_C, CTRL_DOWN_MASK, false), "ctrl_c");
        textComponent.getActionMap().put("ctrl_c", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.copy();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_X, CTRL_DOWN_MASK, false), "ctrl_x");
        textComponent.getActionMap().put("ctrl_x", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.cut();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_V, CTRL_DOWN_MASK, false), "ctrl_v");
        textComponent.getActionMap().put("ctrl_v", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.paste();
            }
        });

        textComponent.getInputMap().put(getKeyStroke(VK_V, CTRL_DOWN_MASK | SHIFT_DOWN_MASK, false), "ctrl_shift_v");
        textComponent.getActionMap().put("ctrl_shift_v", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.pasteAndPop();
            }
        });
    }


    private void initStackListeners() {
        textComponent.getInputMap().put(getKeyStroke(VK_Z, CTRL_DOWN_MASK, false), "ctrl_z");
        textComponent.getActionMap().put("ctrl_z", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.undo();
            }
        });
        textComponent.getInputMap().put(getKeyStroke(VK_Y, CTRL_DOWN_MASK, false), "ctrl_y");
        textComponent.getActionMap().put("ctrl_y", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.redo();
            }
        });
    }


    private void createMenus(Container cp) {
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu moveMenu = new JMenu("Move");
        JMenu pluginsMenu = new JMenu("Plugins");


        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(moveMenu);
        mb.add(pluginsMenu);

        fileMenu.add(new JMenuItem(new OpenAction("Open", this)));
        fileMenu.add(new JMenuItem(new SaveAction("Save", this)));
        fileMenu.add(new JMenuItem(new ExitAction("Exit", this)));

        var undoAction = new UndoAction("Undo", this);
        editMenu.add(new JMenuItem(undoAction));
        textEditorModel.getUndoManager().addUndoObserver(undoAction);

        var redoAction = new RedoAction("Redo", this);
        editMenu.add(new JMenuItem(redoAction));
        textEditorModel.getUndoManager().addRedoObserver(redoAction);

        var cutAction = new CutAction("Cut", this);
        editMenu.add(new JMenuItem(cutAction));
        textEditorModel.addSelectionObserver(cutAction);

        var copyAction = new CopyAction("Copy", this);
        editMenu.add(new JMenuItem(copyAction));
        textEditorModel.addSelectionObserver(copyAction);

        var pasteAction = new PasteAction("Paste", this);
        editMenu.add(new JMenuItem(pasteAction));
        textEditorModel.getClipboardStack().addObserver(pasteAction);

        var pasteAndTakeAction = new PasteAndTakeAction("Paste and take", this);
        editMenu.add(new JMenuItem(pasteAndTakeAction));
        textEditorModel.getClipboardStack().addObserver(pasteAndTakeAction);

        var deleteSelection = new DeleteSelectionAction("Delete selection", this);
        editMenu.add(new JMenuItem(deleteSelection));
        textEditorModel.addSelectionObserver(deleteSelection);

        editMenu.add(new JMenuItem(new ClearDocument("Clear document", this)));

        moveMenu.add(new JMenuItem(new CursorToStartAction("Cursor to document start", this)));
        moveMenu.add(new JMenuItem(new CursorToEndAction("Cursor to document end", this)));


        JToolBar tb = new JToolBar();

        tb.add(new JButton(undoAction));
        tb.add(new JButton(redoAction));
        tb.add(new JButton(copyAction));
        tb.add(new JButton(cutAction));
        tb.add(new JButton(pasteAction));

        //  cp.add(tb, BorderLayout.PAGE_START);

        for (var pluginName : PluginFactory.availablePlugins()) {
            pluginsMenu.add(new JMenuItem(new PluginAction(pluginName,textEditorModel)));
        }
    }

    private void createStatusbar(Container cp) {
        StatusBarComponent statusBar = new StatusBarComponent(textEditorModel);
        cp.add(statusBar, BorderLayout.SOUTH);

        textEditorModel.addSelectionObserver(statusBar);
        textEditorModel.addTextObserver(statusBar);
        textEditorModel.addCursorObserver(statusBar);
        textEditorModel.addTimeObserver(statusBar);
    }

    @Override
    public boolean requestFocusInWindow() {
        return textComponent.requestFocusInWindow();
    }

}
