package lab3.hr.fer.zemris.ooup.model;

import lab3.hr.fer.zemris.ooup.observers.ClipboardObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ClipboardStack {

    private final Set<ClipboardObserver> clipboardObservers = new HashSet<>();
    private final Stack<String> texts = new Stack<>();

    public String push(String text) {
        boolean wasEmpty = isEmpty();
        String clipboard = texts.push(text);
        if (wasEmpty)
            notifyObservers();
        return clipboard;
    }

    public String pop() {
        String clipboard = texts.pop();
        if (isEmpty())
            notifyObservers();
        return clipboard;
    }

    public String peek() {
        return texts.peek();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public void clear() {
        texts.clear();
        notifyObservers();
    }

    public void addObserver(ClipboardObserver clipboardObserver) {
        clipboardObservers.add(clipboardObserver);
    }

    public void removeObserver(ClipboardObserver clipboardObserver) {
        clipboardObservers.add(clipboardObserver);
    }

    public void notifyObservers() {
        clipboardObservers.forEach(ClipboardObserver::updateClipboard);
    }
}
