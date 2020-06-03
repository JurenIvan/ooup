package lab4.hr.fer.zemris.ooup.actions;

import lab4.hr.fer.zemris.ooup.DocumentModel;
import lab4.hr.fer.zemris.ooup.GUI;
import lab4.hr.fer.zemris.ooup.renderer.SVGRendererImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;

public class SvgExportAction extends AbstractAction {

    private final GUI gui;
    private DocumentModel model;

    public SvgExportAction(DocumentModel model, GUI gui) {
        super("SVG Export");
        this.model = model;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        if (jfc.showSaveDialog(gui) != APPROVE_OPTION) {
            showMessageDialog(gui, "Nothing saved", "Warning", INFORMATION_MESSAGE);
        } else {
            try {
                SVGRendererImpl svgRenderer = new SVGRendererImpl(jfc.getSelectedFile().toPath());
                model.list().forEach(go -> go.render(svgRenderer));
                svgRenderer.close();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(gui, "Nothing saved", "Error", ERROR_MESSAGE);
            }
        }
    }
}
