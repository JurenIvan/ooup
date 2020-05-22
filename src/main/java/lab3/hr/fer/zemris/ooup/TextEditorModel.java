package lab3.hr.fer.zemris.ooup;

import lab3.hr.fer.zemris.ooup.actions.*;
import lab3.hr.fer.zemris.ooup.iterator.PartialLinesIterator;
import lab3.hr.fer.zemris.ooup.model.ClipboardStackAlternative;
import lab3.hr.fer.zemris.ooup.model.Location;
import lab3.hr.fer.zemris.ooup.model.SelectionRange;
import lab3.hr.fer.zemris.ooup.model.UndoManager;
import lab3.hr.fer.zemris.ooup.observers.CursorObserver;
import lab3.hr.fer.zemris.ooup.observers.TextObserver;
import lombok.Data;

import java.util.*;

import static java.lang.Math.min;

@Data
public class TextEditorModel {

    private List<String> lines = new ArrayList<>();
    private SelectionRange selectionRange = null;
    private Location cursorLocation = new Location(0, 0);
    private ClipboardStackAlternative clipboardStack = new ClipboardStackAlternative();
    private UndoManager undoManager = UndoManager.getInstance();

    private Set<CursorObserver> cursorObservers = new HashSet<>();
    private Set<TextObserver> textObservers = new HashSet<>();

    public TextEditorModel(String initString) {
        lines.addAll(List.of(initString.split("\n")));
    }

    public Iterator<String> allLines() {
        return new PartialLinesIterator(this);
    }

    public Iterator<String> linesRange(int start, int end) {
        return new PartialLinesIterator(this, start, end);
    }

    public void moveCursorLeft() {
        if (cursorLocation.getColumn() == 0) {
            if (cursorLocation.getLine() != 0) {
                cursorLocation.setLine(cursorLocation.getLine() - 1);
                cursorLocation.setColumn(lines.get(cursorLocation.getLine()).length());
                notifyCursorObservers();
            }
        } else {
            cursorLocation.setColumn(cursorLocation.getColumn() - 1);
            notifyCursorObservers();
        }
    }

    public void moveCursorRight() {
        if (cursorLocation.getColumn() == lines.get(cursorLocation.getLine()).length()) {
            if (cursorLocation.getLine() != lines.size() - 1) {
                cursorLocation.setLine(cursorLocation.getLine() + 1);
                cursorLocation.setColumn(0);
                notifyCursorObservers();
            }
        } else {
            cursorLocation.setColumn(cursorLocation.getColumn() + 1);
            notifyCursorObservers();
        }
    }

    public void moveCursorUp() {
        if (cursorLocation.getLine() != 0) {
            cursorLocation.setLine(cursorLocation.getLine() - 1);
            cursorLocation.setColumn(min(cursorLocation.getColumn(), lines.get(cursorLocation.getLine()).length()));
            notifyCursorObservers();
        } else {
            if (cursorLocation.getColumn() != 0) {
                cursorLocation.setColumn(0);
                notifyCursorObservers();
            }
        }
    }

    public void moveCursorDown() {
        if (cursorLocation.getLine() != lines.size() - 1) {
            cursorLocation.setLine(min(lines.size(), cursorLocation.getLine() + 1));
            cursorLocation.setColumn(min(cursorLocation.getColumn(), lines.get(cursorLocation.getLine()).length()));
            notifyCursorObservers();
        } else {
            if (cursorLocation.getColumn() != lines.get(lines.size() - 1).length()) {
                cursorLocation.setColumn(lines.get(lines.size() - 1).length());
                notifyCursorObservers();
            }
        }
    }

    public void deleteBefore() {
        undoManager.push(new DeleteBeforeAction(this)).executeDo();
    }

    public void deleteAfter() {
        undoManager.push(new DeleteAfterAction(this)).executeDo();
    }

    public void deleteRange(SelectionRange selectionRange) {
        undoManager.push(new DeleteRangeAction(this)).executeDo();
    }

    public SelectionRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(SelectionRange range) {
        this.selectionRange = range;
    }

    public boolean addCursorObserver(CursorObserver cursorObserver) {
        return cursorObservers.add(cursorObserver);
    }

    public boolean removeCursorObserver(CursorObserver cursorObserver) {
        return cursorObservers.remove(cursorObserver);
    }

    public void notifyCursorObservers() {
        cursorObservers.forEach(e -> e.updateCursorLocation(cursorLocation));
    }

    public boolean addTextObserver(TextObserver textObserver) {
        return textObservers.add(textObserver);
    }

    public boolean removeTextObserver(TextObserver textObserver) {
        return textObservers.remove(textObserver);
    }

    public void notifyTextObservers() {
        textObservers.forEach(TextObserver::updateText);
    }

    public void moveSelectionUp() {
        initSelection();

        if (cursorLocation.getLine() == 0)
            selectionRange.getEnd().setColumn(0);
        else {
            selectionRange.getEnd().setLine(selectionRange.getEnd().getLine() - 1);
            selectionRange.getEnd().setColumn(min(selectionRange.getEnd().getColumn(), lines.get(selectionRange.getEnd().getLine()).length()));
        }

        moveCursorUp();
    }

    public void moveSelectionDown() {
        initSelection();

        if (cursorLocation.getLine() == lines.size() - 1)
            selectionRange.getEnd().setColumn(lines.get(lines.size() - 1).length());
        else {
            selectionRange.getEnd().setLine(selectionRange.getEnd().getLine() + 1);
            selectionRange.getEnd().setColumn(min(selectionRange.getEnd().getColumn(), lines.get(selectionRange.getEnd().getLine()).length()));
        }

        moveCursorDown();
    }

    public void moveSelectionLeft() {
        initSelection();

        if (cursorLocation.getColumn() == 0) {
            if (cursorLocation.getLine() != 0) {
                selectionRange.getEnd().setColumn(lines.get(cursorLocation.getLine() - 1).length());
                selectionRange.getEnd().setLine(cursorLocation.getLine() - 1);
            }
        } else {
            selectionRange.getEnd().setColumn(selectionRange.getEnd().getColumn() - 1);
        }

        moveCursorLeft();
    }

    public void moveSelectionRight() {
        initSelection();

        if (cursorLocation.getColumn() == lines.get(cursorLocation.getLine()).length()) {
            if (cursorLocation.getLine() != lines.size() - 1) {
                selectionRange.getEnd().setLine(selectionRange.getEnd().getLine() + 1);
                selectionRange.getEnd().setColumn(0);
            }
        } else {
            selectionRange.getEnd().setColumn(selectionRange.getEnd().getColumn() + 1);
        }

        moveCursorRight();
    }

    private void initSelection() {
        if (selectionRange == null)
            selectionRange = new SelectionRange(cursorLocation.copy(), cursorLocation.copy());
    }

    void insert(char c) {
        if (selectionRange != null)
            deleteRange(selectionRange);

        undoManager.push(new InsertCharAction(this, c)).executeDo();
    }

    void insert(String string) {
        if (selectionRange != null)
            deleteRange(selectionRange);

        undoManager.push(new InsertStringAction(this, string)).executeDo();
    }

    public void copy() {
        clipboardStack.push(extractSelection());
    }

    public void cut() {
        clipboardStack.push(extractSelection());
        deleteRange(selectionRange);
    }

    public void paste() {
        if (!clipboardStack.isEmpty())
            insert(clipboardStack.peek());
    }

    public void pasteAndPop() {
        if (!clipboardStack.isEmpty())
            insert(clipboardStack.pop());
    }

    private String extractSelection() {
        if (selectionRange == null) return null;
        SelectionRange selectionRange = this.selectionRange.copy();
        selectionRange.swapStartAndEndIfNecessary();
        StringBuilder sb = new StringBuilder();

        for (int i = selectionRange.getStart().getLine(); i <= selectionRange.getEnd().getLine(); i++) {
            int x0 = 0;
            int x1 = lines.get(i).length();

            if (i == selectionRange.getStart().getLine())
                x0 = selectionRange.getStart().getColumn();
            if (i == selectionRange.getEnd().getLine())
                x1 = selectionRange.getEnd().getColumn();

            sb.append(lines.get(i), x0, x1);
            sb.append('\n');
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
