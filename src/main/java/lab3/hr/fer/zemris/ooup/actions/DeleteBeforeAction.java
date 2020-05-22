package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;

import java.util.List;

public class DeleteBeforeAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final Location cursorLocation;
    private char deletedChar;


    public DeleteBeforeAction(TextEditorModel textEditorModel) {
        this.cursorLocation = textEditorModel.getCursorLocation().copy();
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void executeDo() {
        List<String> lines = textEditorModel.getLines();

        if (cursorLocation.getColumn() == 0) {
            if (cursorLocation.getLine() != 0) {
                String newLine = lines.get(cursorLocation.getLine() - 1) + lines.get(cursorLocation.getLine());
                deletedChar = '\n';

                lines.remove(cursorLocation.getLine() - 1);
                lines.remove(cursorLocation.getLine() - 1);
                lines.add(cursorLocation.getLine() - 1, newLine);

                textEditorModel.moveCursorLeft();
                textEditorModel.notifyTextObservers();
            }
        } else {
            String oldLine = lines.get(cursorLocation.getLine());
            String newLine = oldLine.substring(0, cursorLocation.getColumn() - 1) + oldLine.substring(cursorLocation.getColumn());
            deletedChar = oldLine.charAt(cursorLocation.getColumn()-1);
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);

            textEditorModel.moveCursorLeft();
            textEditorModel.notifyTextObservers();
        }
    }

    @Override
    public void executeUndo() {
        new InsertCharAction(textEditorModel, deletedChar).executeDo();
        textEditorModel.setCursorLocation(cursorLocation);
    }
}
