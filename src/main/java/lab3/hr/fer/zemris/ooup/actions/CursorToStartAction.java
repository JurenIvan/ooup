package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.model.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToStartAction extends AbstractAction {

    private final TextEditor textEditor;

    public CursorToStartAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditor.getTextEditorModel().setCursorLocation(new Location(0, 0));
        textEditor.getTextEditorModel().notifyCursorObservers();
    }
}
