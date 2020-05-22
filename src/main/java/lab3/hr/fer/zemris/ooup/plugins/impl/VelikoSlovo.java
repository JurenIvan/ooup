package lab3.hr.fer.zemris.ooup.plugins.impl;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.ClipboardStack;
import lab3.hr.fer.zemris.ooup.model.UndoManager;
import lab3.hr.fer.zemris.ooup.plugins.Plugin;

public class VelikoSlovo implements Plugin {
    @Override
    public String getName() {
        return "Big letter";
    }

    @Override
    public String getDescription() {
        return "Camel cases document";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        var lines = model.getLines();
        for (int i = 0; i < lines.size(); i++) {
            String curr = lines.get(i);
            StringBuilder sb = new StringBuilder();
            for (String spllited : curr.split(" ")) {
                if (spllited.length() > 0)
                    sb.append(capitalize(spllited));
                sb.append(" ");
            }
            lines.remove(i);
            lines.add(i, sb.toString());
            sb.setLength(0);
        }
        model.notifyTextObservers();
    }


    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
