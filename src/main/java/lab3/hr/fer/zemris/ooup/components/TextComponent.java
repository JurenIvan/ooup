package lab3.hr.fer.zemris.ooup.components;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;
import lab3.hr.fer.zemris.ooup.model.SelectionRange;
import lab3.hr.fer.zemris.ooup.observers.CursorObserver;
import lab3.hr.fer.zemris.ooup.observers.TextObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

import static java.awt.Color.*;

public class TextComponent extends JComponent implements TextObserver, CursorObserver {

    private static final int margins = 15;
    private static final int SPACE_BETWEEN_LINES = 4;
    private final TextEditorModel textEditorModel;

    public TextComponent(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawText(g);
        drawCarret(g);
    }

    private void drawText(Graphics g) {
        int y = margins;
        Iterator<String> iterator = textEditorModel.allLines();

        SelectionRange selectionRange = textEditorModel.getSelectionRange();
        if (selectionRange != null) {
            selectionRange = selectionRange.copy();
            selectionRange.swapStartAndEndIfNecessary();
            for (int lineIndex = selectionRange.getStart().getLine(); lineIndex <= selectionRange.getEnd().getLine(); lineIndex++) {
                String line = textEditorModel.getLines().get(lineIndex);
                int start = 0;
                int end = line.length();

                if (selectionRange.getStart().getLine() == lineIndex) {
                    start = selectionRange.getStart().getColumn();
                }
                if (selectionRange.getEnd().getLine() == lineIndex) {
                    end = selectionRange.getEnd().getColumn();
                }

                int fontSize = g.getFont().getSize();

                int x0 = g.getFontMetrics().stringWidth(line.substring(0, start)) + margins;
                int x1 = g.getFontMetrics().stringWidth(line.substring(0, end)) + margins;
                int y1 = lineIndex * (fontSize + SPACE_BETWEEN_LINES) + margins +SPACE_BETWEEN_LINES;
                int y0 = (lineIndex - 1) * (fontSize + SPACE_BETWEEN_LINES) + margins+SPACE_BETWEEN_LINES;

                g.setColor(GRAY);
                g.fillRect(x0, y0, x1 - x0, y1 - y0);
                g.setColor(BLACK);
            }
        }
        while (iterator.hasNext()) {
            g.drawString(iterator.next(), margins, y);
            y += g.getFont().getSize() + SPACE_BETWEEN_LINES;
        }
    }

    private void drawCarret(Graphics g) {
        Location position = textEditorModel.getCursorLocation();
        String line = textEditorModel.getLines().get(position.getLine()).substring(0, position.getColumn());

        int fontSize = g.getFont().getSize();
        int xStart = g.getFontMetrics().stringWidth(line) + margins;
        g.drawLine(xStart,
                position.getLine() * (fontSize + SPACE_BETWEEN_LINES) + margins,
                xStart,
                (position.getLine() - 1) * (fontSize + SPACE_BETWEEN_LINES) + margins + SPACE_BETWEEN_LINES);

    }

    private void registerCursorObserver() {
        textEditorModel.addCursorObserver(loc -> this.repaint());
    }

    @Override
    public void updateText() {
        repaint();
    }

    @Override
    public void updateCursorLocation(Location loc) {
        repaint();
    }
}
