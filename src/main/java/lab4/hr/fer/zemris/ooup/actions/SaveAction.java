package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.join;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;

public class SaveAction extends AbstractAction {

    private final GUI gui;
    private final DocumentModel model;

    public SaveAction(DocumentModel model, GUI gui) {
        super("Save .svg");
        this.model = model;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        List<String> rows = new LinkedList<>();
        model.list().forEach(e -> e.save(rows));

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        if (jfc.showSaveDialog(gui) != APPROVE_OPTION) {
            showMessageDialog(gui, "Nothing saved", "Warning", INFORMATION_MESSAGE);
        } else {
            try {
                Files.writeString(jfc.getSelectedFile().toPath(), join("\n", rows));
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(gui, "Nothing saved", "Error", ERROR_MESSAGE);
            }
        }
    }

}
