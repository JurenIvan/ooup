package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearDocument extends AbstractAction {
    private final TextEditor textEditor;

    public ClearDocument(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditor.getTextEditorModel().clear();
    }
}
