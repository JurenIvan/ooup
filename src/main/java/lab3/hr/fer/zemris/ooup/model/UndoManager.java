package lab3.hr.fer.zemris.ooup.model;

import lab3.hr.fer.zemris.ooup.actions.EditAction;
import lab3.hr.fer.zemris.ooup.observers.StackObserver;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Data
public class UndoManager {

    private static UndoManager undoManager;

    public static UndoManager getInstance() {
        if (undoManager != null)
            return undoManager;
        return undoManager = new UndoManager();
    }

    Stack<EditAction> undoStack = new Stack<>();
    Stack<EditAction> redoStack = new Stack<>();

    Set<StackObserver> undoObservers = new HashSet<>();
    Set<StackObserver> redoObservers = new HashSet<>();

    public void undo() {
        if (undoStack.isEmpty())
            return;

        EditAction editAction = undoStack.pop();
        if (undoStack.isEmpty())
            notifyUndoObservers();

        editAction.executeUndo();
        redoStack.push(editAction);
        notifyRedoObservers();
    }

    public void redo() {
        if (redoStack.isEmpty())
            return;

        EditAction editAction = redoStack.pop();
        if (redoStack.isEmpty())
            notifyRedoObservers();

        editAction.executeDo();
        undoStack.push(editAction);
        notifyUndoObservers();
    }

    public EditAction push(EditAction editAction) {
        undoStack.push(editAction);
        redoStack.clear();

        notifyRedoObservers();
        notifyUndoObservers();

        return editAction;
    }

    public void clear() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        notifyRedoObservers();
        notifyUndoObservers();
    }

    public void addUndoObserver(StackObserver stackObserver) {
        undoObservers.add(stackObserver);
    }

    public void removeUndoObserver(StackObserver stackObserver) {
        undoObservers.add(stackObserver);
    }

    public void notifyUndoObservers() {
        boolean notEmpty = !undoStack.empty();
        undoObservers.forEach(e -> e.updateStackView(notEmpty));
    }

    public void addRedoObserver(StackObserver stackObserver) {
        redoObservers.add(stackObserver);
    }

    public void removeRedoObserver(StackObserver stackObserver) {
        redoObservers.add(stackObserver);
    }

    public void notifyRedoObservers() {
        boolean notEmpty = !redoStack.empty();
        redoObservers.forEach(e -> e.updateStackView(notEmpty));
    }

}
