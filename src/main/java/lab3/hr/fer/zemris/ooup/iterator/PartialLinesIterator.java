package lab3.hr.fer.zemris.ooup.iterator;

import lab3.hr.fer.zemris.ooup.TextEditorModel;

import java.util.Iterator;


public class PartialLinesIterator implements Iterator<String> {

    private final TextEditorModel textEditorModel;
    private int start;
    private final int end;

    public PartialLinesIterator(TextEditorModel textEditorModel, int start, int end) {
        this.textEditorModel = textEditorModel;
        this.start = start;
        this.end = end;
        if (start > end) throw new IllegalArgumentException();
    }

    public PartialLinesIterator(TextEditorModel textEditorModel) {
        this(textEditorModel, 0, textEditorModel.getLines().size());
    }

    @Override
    public boolean hasNext() {
        return start < end && textEditorModel.getLines().size() > start;
    }

    @Override
    public String next() {
        return textEditorModel.getLines().get(start++);
    }
}
