package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.observers.ClipboardObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasteAndTakeAction extends AbstractAction implements ClipboardObserver {
    private final TextEditor textEditor;

    public PasteAndTakeAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!textEditor.getTextEditorModel().getClipboardStack().isEmpty()) {
            textEditor.getTextEditorModel().pasteAndPop();
        }
    }

    @Override
    public void updateClipboard() {
        setEnabled(!textEditor.getTextEditorModel().getClipboardStack().isEmpty());
    }
}
