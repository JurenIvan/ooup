package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.SelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteSelectionAction extends AbstractAction implements SelectionObserver {
    private final TextEditor textEditor;

    public DeleteSelectionAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textEditor.getTextEditorModel().getSelectionRange() != null)
            textEditor.getTextEditorModel().deleteRange(textEditor.getTextEditorModel().getSelectionRange());
    }

    @Override
    public void updateSelectionStatus(boolean hasSelection) {
        setEnabled(hasSelection);
    }
}
