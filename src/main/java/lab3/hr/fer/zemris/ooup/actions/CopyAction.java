package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.SelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CopyAction extends AbstractAction implements SelectionObserver {

    private final TextEditor textEditor;

    public CopyAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textEditor.getTextEditorModel().getSelectionRange() != null) {
            textEditor.getTextEditorModel().copy();
        }
    }

    @Override
    public void updateSelectionStatus(boolean hasSelection) {
        setEnabled(textEditor.getTextEditorModel().getSelectionRange() != null);
    }
}
