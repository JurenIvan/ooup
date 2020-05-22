package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.StackObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction implements StackObserver {
    private final TextEditor textEditor;

    public RedoAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textEditor.getTextEditorModel().getUndoManager().getRedoStack().isEmpty()) {
            textEditor.getTextEditorModel().getUndoManager().redo();
        }
    }

    @Override
    public void updateStackView(boolean present) {
        setEnabled(present);
    }
}
