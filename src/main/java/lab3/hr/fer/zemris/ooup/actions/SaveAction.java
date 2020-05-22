package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;

public class SaveAction extends AbstractAction {

    private final TextEditor textEditor;

    public SaveAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        if (jfc.showSaveDialog(textEditor) != APPROVE_OPTION) {
            showMessageDialog(textEditor, "Nothing saved", "Warning", INFORMATION_MESSAGE);
        } else {
            try {
                Files.write(jfc.getSelectedFile().toPath(), textEditor.getTextEditorModel().getLines());
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(textEditor, "Nothing saved", "Error", ERROR_MESSAGE);
            }
        }
    }
}
