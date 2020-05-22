package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.StackObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction implements StackObserver {
    private final TextEditor textEditor;

    public UndoAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textEditor.getTextEditorModel().getUndoManager().getUndoStack().isEmpty()) {
            textEditor.getTextEditorModel().getUndoManager().undo();
        }
    }

    @Override
    public void updateStackView(boolean present) {
        setEnabled(present);
    }
}
