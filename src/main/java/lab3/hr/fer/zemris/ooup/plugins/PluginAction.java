package lab3.hr.fer.zemris.ooup.plugins;

import lab3.hr.fer.zemris.ooup.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PluginAction extends AbstractAction {

    private String pluginName;
    private TextEditorModel textEditorModel;

    public PluginAction(String name, TextEditorModel textEditorModel) {
        super(name);
        this.pluginName = name;
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            PluginFactory.newInstance(pluginName).execute(textEditorModel, textEditorModel.getUndoManager(), textEditorModel.getClipboardStack());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
