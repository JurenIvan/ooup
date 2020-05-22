package lab3.hr.fer.zemris.ooup.actions;

import lab3.hr.fer.zemris.ooup.TextEditor;
import lab3.hr.fer.zemris.ooup.model.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class OpenAction extends AbstractAction {

    private final TextEditor textEditor;

    public OpenAction(String name, TextEditor textEditor) {
        super(name);
        this.textEditor = textEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");
        if (jfc.showOpenDialog(textEditor) != JFileChooser.APPROVE_OPTION)
            return;

        Path openedFilePath = jfc.getSelectedFile().toPath();
        if (!Files.isReadable(openedFilePath)) {
            showMessageDialog(textEditor, "no Read Persmision", "Error", ERROR_MESSAGE);
            return;
        }

        try {
            textEditor.getTextEditorModel().setLines(Files.readAllLines(openedFilePath));
        } catch (IOException ioException) {
            showMessageDialog(textEditor, "Not possible to open", "Error", ERROR_MESSAGE);
            ioException.printStackTrace();
        }

        textEditor.getTextEditorModel().setCursorLocation(new Location(0, 0));
        textEditor.getTextEditorModel().notifyTextObservers();
    }
}
