package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;
import lab3.hr.fer.zemris.ooup.observers.EditAction;

import java.util.List;

public class DeleteAfterAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final Location cursorLocation;
    private char deletedChar;

    public DeleteAfterAction(TextEditorModel textEditorModel) {
        this.cursorLocation = textEditorModel.getCursorLocation().copy();
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void executeDo() {
        List<String> lines = textEditorModel.getLines();

        if (cursorLocation.getColumn() == lines.get(cursorLocation.getLine()).length()) {
            if (cursorLocation.getLine() != lines.size() - 1) {
                deletedChar = '\n';
                String newLine = lines.get(cursorLocation.getLine()) + lines.get(cursorLocation.getLine() + 1);

                lines.remove(cursorLocation.getLine());
                lines.remove(cursorLocation.getLine());
                lines.add(cursorLocation.getLine(), newLine);

                textEditorModel.notifyTextObservers();
            }
        } else {
            String oldLine = lines.get(cursorLocation.getLine());
            String newLine = oldLine.substring(0, cursorLocation.getColumn()) + oldLine.substring(cursorLocation.getColumn() + 1);
            deletedChar = oldLine.charAt(cursorLocation.getColumn());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);

            textEditorModel.notifyTextObservers();
        }
    }

    @Override
    public void executeUndo() {
        new InsertCharAction(textEditorModel, deletedChar).executeDo();
        textEditorModel.setCursorLocation(cursorLocation);
    }
}
