package lab3.hr.fer.zemris.ooup;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class NotepadRunner {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TextEditorModel textEditorModel = new TextEditorModel("Primjer\nvise-redcanog\nstringa");
        SwingUtilities.invokeLater(() -> new TextEditor(textEditorModel).setVisible(true));
    }
}
