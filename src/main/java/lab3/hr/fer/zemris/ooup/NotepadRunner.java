package lab3.hr.fer.zemris.ooup;

import javax.swing.*;

public class NotepadRunner {

    public static void main(String[] args) {
        TextEditorModel textEditorModel = new TextEditorModel("Primjer\nvise-redcanog\nstringa");
        SwingUtilities.invokeLater(() -> new TextEditor(textEditorModel).setVisible(true));
    }
}
