package lab3.hr.fer.zemris.ooup.iterator;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

@RequiredArgsConstructor
public class TextLinesIterator implements Iterator<String> {

    private final TextEditorModel textEditorModel;
    private int curr = 0;

    @Override
    public boolean hasNext() {
        return textEditorModel.getLines().size() > curr;
    }

    @Override
    public String next() {
        return textEditorModel.getLines().get(curr++);
    }
}
