package lab3.hr.fer.zemris.ooup.lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import static java.awt.Color.RED;
import static java.awt.event.KeyEvent.VK_ENTER;

public class PrviZadatak extends JFrame {

    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrviZadatak().setVisible(true));
    }

    public PrviZadatak() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JComponent component = new Module(this);
        cp.add(component, BorderLayout.CENTER);

    }

    private static class Module extends JComponent {

        public Module(JFrame jFrame) {
            jFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == VK_ENTER) {
                        jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            int x = 100;
            int y = 100;

            int thirdX = (int) (getSize().width * 0.3);
            int fourthY = (int) (getSize().width * 0.4);

            g.setColor(RED);
            g.drawLine(x, y, x, y + 50);
            g.drawLine(x, y, x + 50, y);

            g.drawString("Zboh cega je Crna Gora odbila stolicu u ujedinjenim nacijama?\n", thirdX, fourthY);
            g.drawString("- Trazila je lezaljku!\n", thirdX, fourthY + g.getFont().getSize() * 2);

            g.setColor(RED);
            g.fillRect(100,200,100,100);
        }
    }
}
