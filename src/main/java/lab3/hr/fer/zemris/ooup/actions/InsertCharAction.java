package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;

import java.util.List;

public class InsertCharAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final Location cursorLocation;
    private final char c;

    public InsertCharAction(TextEditorModel textEditorModel, char c) {
        this.textEditorModel = textEditorModel;
        this.cursorLocation = textEditorModel.getCursorLocation().copy();
        this.c = c;
    }

    @Override
    public void executeDo() {
        List<String> lines = textEditorModel.getLines();

        String oldLine = lines.get(cursorLocation.getLine());
        if (c == 10) {
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), oldLine.substring(0, cursorLocation.getColumn()));
            lines.add(cursorLocation.getLine() + 1, oldLine.substring(cursorLocation.getColumn()));

            textEditorModel.getCursorLocation().setLine(cursorLocation.getLine() + 1);
            textEditorModel.getCursorLocation().setColumn(0);

        } else {
            String newLine = oldLine.substring(0, cursorLocation.getColumn()) + c + oldLine.substring(cursorLocation.getColumn());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);
            textEditorModel.getCursorLocation().setColumn(cursorLocation.getColumn() + 1);
        }
        textEditorModel.notifyTextObservers();
    }

    @Override
    public void executeUndo() {
        List<String> lines = textEditorModel.getLines();

        if (c == 10) {
            String secondLine = lines.get(cursorLocation.getLine() + 1);
            String firstLine = lines.get(cursorLocation.getLine());

            lines.remove(cursorLocation.getLine());
            lines.remove(cursorLocation.getLine());

            lines.add(cursorLocation.getLine(), firstLine + secondLine);

            textEditorModel.setCursorLocation(cursorLocation);
        } else {
            String newLine = lines.get(cursorLocation.getLine());

            String oldLine = newLine.substring(0, cursorLocation.getColumn()) + newLine.substring(cursorLocation.getColumn() + 1);
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), oldLine);
            textEditorModel.setCursorLocation(cursorLocation);
        }
        textEditorModel.notifyTextObservers();
    }
}
