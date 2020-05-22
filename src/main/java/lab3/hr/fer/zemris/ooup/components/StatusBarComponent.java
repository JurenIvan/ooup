package lab3.hr.fer.zemris.ooup.components;

import lab3.hr.fer.zemris.ooup.TextEditorModel;
import lab3.hr.fer.zemris.ooup.model.Location;
import lab3.hr.fer.zemris.ooup.observers.ClockObserver;
import lab3.hr.fer.zemris.ooup.observers.CursorObserver;
import lab3.hr.fer.zemris.ooup.observers.SelectionObserver;
import lab3.hr.fer.zemris.ooup.observers.TextObserver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.awt.Color.gray;
import static java.awt.FlowLayout.*;
import static javax.swing.SwingConstants.RIGHT;

public class StatusBarComponent extends JPanel implements CursorObserver, TextObserver, ClockObserver, SelectionObserver {

    private final TextEditorModel textEditorModel;

    private final JLabel lengthLabel = new JLabel("length: ");
    private final JLabel lnLabel = new JLabel("Ln: 0");
    private final JLabel colLabel = new JLabel("Col: 0");
    private final JLabel selLabel = new JLabel("Sel: 0");
    private JLabel clock = new JLabel("asdasdasdasd");

    public StatusBarComponent(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;

        setLayout(new GridLayout(1, 3));
        setBorder(new TitledBorder(new LineBorder(gray, 2)));

        JPanel infoLeft = new JPanel(new FlowLayout(LEFT));
        JPanel infoRight = new JPanel(new FlowLayout(CENTER));
        JPanel clockPanel = new JPanel(new FlowLayout(CENTER));

        add(infoLeft);
        add(infoRight);
        add(clockPanel);

        infoLeft.setBorder(BorderFactory.createLineBorder(gray, 2));
        infoRight.setBorder(BorderFactory.createLineBorder(gray, 2));
        clockPanel.setBorder(BorderFactory.createLineBorder(gray, 2));

        clock.setHorizontalAlignment(RIGHT);

        infoLeft.add(lengthLabel);
        infoRight.add(lnLabel);
        infoRight.add(colLabel);
        infoRight.add(selLabel);
        clockPanel.add(clock);
    }

    @Override
    public void updateCursorLocation(Location loc) {
        int carretX = loc.getLine();
        int carretY = loc.getColumn();

        lnLabel.setText("Ln: " + carretX);
        colLabel.setText("Col: " + carretY);
    }

    @Override
    public void updateText() {
        int linesCount = textEditorModel.getLines().size();
        lengthLabel.setText("length:" + linesCount);
    }

    @Override
    public void updateTime(LocalDateTime now) {
        if (now != null)
            clock.setText(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now));
    }

    @Override
    public void updateSelectionStatus(boolean hasSelection) {
        int len = 0;
        if (hasSelection)
            len = textEditorModel.extractSelection().length();

        selLabel.setText("Sel: " + len);
    }
}
