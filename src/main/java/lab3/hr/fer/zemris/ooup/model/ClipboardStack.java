package lab3.hr.fer.zemris.ooup.model;

import java.util.Stack;

public class ClipboardStack extends Stack<String> {

    private Stack<String> texts = new Stack<>();

    public String push(String text) {
        return texts.push(text);
    }

    public String pop() {
        return texts.pop();
    }

    public String peek() {
        return texts.peek();
    }

    public boolean isEmpty() {
        return texts.size() > 0;
    }

    public void clear() {
        texts.clear();
    }
}
