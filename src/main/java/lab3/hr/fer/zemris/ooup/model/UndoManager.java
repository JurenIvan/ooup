package lab3.hr.fer.zemris.ooup.model;

import lab3.hr.fer.zemris.ooup.observers.EditAction;
import lombok.Data;

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

    public void undo() {
        if (undoStack.isEmpty())
            return;

        EditAction editAction = undoStack.pop();
        editAction.executeUndo();
        redoStack.push(editAction);
    }

    public void redo() {
        if (redoStack.isEmpty())
            return;

        EditAction editAction = redoStack.pop();
        editAction.executeUndo();
        undoStack.push(editAction);
    }

    public EditAction push(EditAction editAction) {
        undoStack.push(editAction);
        redoStack.clear();
        return editAction;
    }
}
