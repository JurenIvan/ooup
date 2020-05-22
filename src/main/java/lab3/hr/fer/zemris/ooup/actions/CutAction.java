package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.SelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CutAction extends AbstractAction implements SelectionObserver {
    private final TextEditor textEditor;

    public CutAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textEditor.getTextEditorModel().getSelectionRange() != null) {
            textEditor.getTextEditorModel().cut();
        }
    }


    @Override
    public void updateSelectionStatus(boolean hasSelection) {
        setEnabled(hasSelection);
    }
}
