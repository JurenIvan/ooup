package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.SelectionRange;
import lab3.hr.fer.zemris.ooup.observers.EditAction;

public class DeleteRangeAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final SelectionRange selectionRange;

    public DeleteRangeAction(TextEditorModel textEditorModel, SelectionRange selectionRange) {
        this.selectionRange = selectionRange;
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void executeDo() {

    }

    @Override
    public void executeUndo() {

    }
}
