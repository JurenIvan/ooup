package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;

import java.util.List;

public class InsertStringAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final Location cursorLocation;
    private final String string;

    public InsertStringAction(TextEditorModel textEditorModel, String string) {
        this.textEditorModel = textEditorModel;
        this.cursorLocation = textEditorModel.getCursorLocation().copy();
        this.string = string;
    }

    @Override
    public void executeDo() {
        List<String> lines = textEditorModel.getLines();
        Location cursorLocation = textEditorModel.getCursorLocation();

        String oldLine = lines.get(cursorLocation.getLine());
        String[] inputSplitted = string.split("\n");

        if (inputSplitted.length == 1) {
            String newLine = oldLine.substring(0, cursorLocation.getColumn()) + string + oldLine.substring(cursorLocation.getColumn());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);

            cursorLocation.setColumn(cursorLocation.getColumn() + string.length());
        } else {
            String firstHalf = oldLine.substring(0, cursorLocation.getColumn());
            String secondHalf = oldLine.substring(cursorLocation.getColumn());
            int cursorLine = cursorLocation.getLine();

            lines.remove(cursorLine);
            lines.add(cursorLine++, firstHalf + inputSplitted[0]);

            for (int i = 1; i < inputSplitted.length - 1; i++)
                lines.add(cursorLine++, inputSplitted[i]);

            lines.add(cursorLine, inputSplitted[inputSplitted.length - 1] + secondHalf);

            textEditorModel.getCursorLocation().setColumn(inputSplitted[inputSplitted.length - 1].length());
            textEditorModel.getCursorLocation().setLine(cursorLine);
        }
        textEditorModel.notifyTextObservers();
    }

    @Override
    public void executeUndo() {
        List<String> lines = textEditorModel.getLines();

        String newLine = lines.get(cursorLocation.getLine());
        String[] inputSplitted = string.split("\n");

        if (inputSplitted.length == 1) {
            String oldLine = newLine.substring(0, cursorLocation.getColumn()) + newLine.substring(cursorLocation.getColumn() + string.length());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), oldLine);

            textEditorModel.getCursorLocation().setColumn(cursorLocation.getColumn());
        } else {
            String firstHalf = newLine.substring(0, cursorLocation.getColumn());
            String secondHalf = lines.get(cursorLocation.getLine() + inputSplitted.length - 1).substring(inputSplitted[inputSplitted.length - 1].length());
            int cursorLine = cursorLocation.getLine();

            for (int i = 0; i < inputSplitted.length; i++)
                lines.remove(cursorLine);

            lines.add(cursorLocation.getLine(), firstHalf + secondHalf);

            textEditorModel.setCursorLocation(cursorLocation);
        }
        textEditorModel.notifyTextObservers();
    }
}
