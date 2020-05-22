package lab3.hr.fer.zemris.ooup.plugins.impl;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.ClipboardStack;
import lab3.hr.fer.zemris.ooup.model.UndoManager;
import lab3.hr.fer.zemris.ooup.plugins.Plugin;

import javax.swing.*;
import java.util.List;

public class Statistics implements Plugin {

    @Override
    public String getName() {
        return "Statistics";
    }

    @Override
    public String getDescription() {
        return "Prints statistics";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        List<String> lines = model.getLines();

        int numOfChars = lines.stream().mapToInt(String::length).sum();
        int numOfWords = lines.stream().mapToInt(e -> e.split("\\s++").length).sum();
        int numOfLines = model.getLines().size();

        StringBuilder sb = new StringBuilder();
        sb.append("Number of chars:").append(numOfChars).append("\n");
        sb.append("Number of words:").append(numOfWords).append("\n");
        sb.append("Number of lines:").append(numOfLines).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
