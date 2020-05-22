package lab3.hr.fer.zemris.ooup.model;

import lab3.hr.fer.zemris.ooup.observers.ClipboardObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ClipboardStackAlternative extends Stack<String> {

    private Set<ClipboardObserver> clipboardObservers = new HashSet<>();

    void addObserver(ClipboardObserver clipboardObserver) {
        clipboardObservers.add(clipboardObserver);
    }

    void removeObserver(ClipboardObserver clipboardObserver) {
        clipboardObservers.add(clipboardObserver);
    }

    void notifyObservers() {
        clipboardObservers.forEach(ClipboardObserver::updateClipboard);
    }
}
