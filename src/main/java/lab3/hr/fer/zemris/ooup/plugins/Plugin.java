package lab3.hr.fer.zemris.ooup.plugins;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.ClipboardStack;
import lab3.hr.fer.zemris.ooup.model.UndoManager;

public interface Plugin {

    String getName();
    String getDescription();
    void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);

}
