package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.model.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToEndAction extends AbstractAction {

    private final TextEditor textEditor;

    public CursorToEndAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Location location = textEditor.getTextEditorModel().getCursorLocation();
        var lines = textEditor.getTextEditorModel().getLines();
        location.setLine(lines.size()-1);
        location.setColumn(lines.get(lines.size() - 1).length());
        textEditor.getTextEditorModel().notifyCursorObservers();
    }
}
