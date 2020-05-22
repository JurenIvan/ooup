package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.SelectionRange;

import java.util.ArrayList;
import java.util.List;

public class DeleteRangeAction implements EditAction {

    private final TextEditorModel textEditorModel;
    private final SelectionRange selectionRange;
    private final List<String> deletedLines = new ArrayList<>();

    public DeleteRangeAction(TextEditorModel textEditorModel, SelectionRange selectionRange) {
        this.textEditorModel = textEditorModel;
        this.selectionRange = selectionRange.copy();
        this.selectionRange.swapStartAndEndIfNecessary();
        textEditorModel.linesRange(this.selectionRange.getStart().getLine(), this.selectionRange.getEnd().getLine() + 1).forEachRemaining(deletedLines::add);
    }

    @Override
    public void executeDo() {
        List<String> lines = textEditorModel.getLines();

        SelectionRange selectionRange = this.selectionRange.copy();
        selectionRange.swapStartAndEndIfNecessary();

        int sl = selectionRange.getStart().getLine();
        int sc = selectionRange.getStart().getColumn();
        int el = selectionRange.getEnd().getLine();
        int ec = selectionRange.getEnd().getColumn();
        int newEl = el;

        for (int i = sl + 1; i < el; i++) {
            lines.remove(sl + 1);
            newEl--;
        }

        if (sl + 1 == newEl) {
            String line = lines.get(sl).substring(0, sc) + lines.get(newEl).substring(ec);
            lines.remove(sl);
            lines.remove(sl);
            lines.add(sl, line);
        } else {      //sl je isti kao i el
            String line = lines.get(sl).substring(0, sc) + lines.get(sl).substring(ec);
            lines.remove(sl);
            lines.add(sl, line);
        }

        textEditorModel.getCursorLocation().setColumn(sc);
        textEditorModel.getCursorLocation().setLine(sl);
        textEditorModel.setSelectionRange(null);
        textEditorModel.notifyTextObservers();
    }

    @Override
    public void executeUndo() {
        List<String> lines = textEditorModel.getLines();

        if (selectionRange.getEnd().getLine() == selectionRange.getStart().getLine()) {
            lines.remove(selectionRange.getEnd().getLine());
            lines.add(selectionRange.getEnd().getLine(), deletedLines.get(0));
        } else {
            lines.remove(selectionRange.getStart().getLine());
            for (int i = 0; i < deletedLines.size(); i++) {
                lines.add(selectionRange.getStart().getLine(), deletedLines.get(deletedLines.size()-1 - i));
            }
        }

        textEditorModel.getCursorLocation().setColumn(selectionRange.getEnd().getColumn());
        textEditorModel.getCursorLocation().setLine(selectionRange.getEnd().getLine());
        textEditorModel.notifyTextObservers();
    }
}
