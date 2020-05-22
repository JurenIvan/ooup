package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.ClipboardObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasteAction extends AbstractAction implements ClipboardObserver {
    private final TextEditor textEditor;

    public PasteAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStackNotEmpty())
            textEditor.getTextEditorModel().paste();
    }

    @Override
    public void updateClipboard() {
        setEnabled(isStackNotEmpty());
    }

    private boolean isStackNotEmpty() {
        return !textEditor.getTextEditorModel().getClipboardStack().isEmpty();
    }
}
