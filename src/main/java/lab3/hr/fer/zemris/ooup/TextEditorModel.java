package lab3.hr.fer.zemris.ooup;

import lab3.hr.fer.zemris.ooup.actions.InsertStringAction;
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
        if (cursorLocation.getColumn() == 0) {
            if (cursorLocation.getLine() != 0) {
                String newLine = lines.get(cursorLocation.getLine() - 1) + lines.get(cursorLocation.getLine());

                lines.remove(cursorLocation.getLine() - 1);
                lines.remove(cursorLocation.getLine() - 1);
                lines.add(cursorLocation.getLine() - 1, newLine);

                moveCursorLeft();   //todo check
                notifyTextObservers();
            }
        } else {
            String oldLine = lines.get(cursorLocation.getLine());
            String newLine = oldLine.substring(0, cursorLocation.getColumn() - 1) + oldLine.substring(cursorLocation.getColumn());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);

            moveCursorLeft();
            notifyTextObservers();
        }
    }

    public void deleteAfter() {
        if (cursorLocation.getColumn() == lines.get(cursorLocation.getLine()).length()) {
            if (cursorLocation.getLine() != lines.size() - 1) {
                String newLine = lines.get(cursorLocation.getLine()) + lines.get(cursorLocation.getLine() + 1);

                lines.remove(cursorLocation.getLine());
                lines.remove(cursorLocation.getLine());
                lines.add(cursorLocation.getLine(), newLine);

                notifyTextObservers();
            }
        } else {
            String oldLine = lines.get(cursorLocation.getLine());
            String newLine = oldLine.substring(0, cursorLocation.getColumn()) + oldLine.substring(cursorLocation.getColumn() + 1);
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);

            notifyTextObservers();
        }
    }

    public void deleteRange(SelectionRange selectionRange) {
        selectionRange = selectionRange.copy();
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

        cursorLocation.setColumn(sc);
        cursorLocation.setLine(sl);
        this.selectionRange = null;
        notifyTextObservers();
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

        String oldLine = lines.get(cursorLocation.getLine());
        if (c == 10) {
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), oldLine.substring(0, cursorLocation.getColumn()));
            lines.add(cursorLocation.getLine() + 1, oldLine.substring(cursorLocation.getColumn()));

            cursorLocation.setLine(cursorLocation.getLine() + 1);
            cursorLocation.setColumn(0);

        } else {
            String newLine = oldLine.substring(0, cursorLocation.getColumn()) + c + oldLine.substring(cursorLocation.getColumn());
            lines.remove(cursorLocation.getLine());
            lines.add(cursorLocation.getLine(), newLine);
            cursorLocation.setColumn(cursorLocation.getColumn() + 1);
        }
        notifyTextObservers();
    }

    void insert(String string) {
        if (selectionRange != null)
            deleteRange(selectionRange);

        UndoManager.getInstance().push(new InsertStringAction(this, string)).executeDo();
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
