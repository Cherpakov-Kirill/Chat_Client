package nsu.oop.client.view.panels;


import nsu.oop.client.view.windows.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class WindowPanel extends JPanel {
    private ImageIcon imageIcon;

    public void setImageIcon(String fileName) {
        URL file = MainWindow.class.getResource(fileName);
        if (file == null) {
            BufferedImage defaultBackground = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = defaultBackground.createGraphics();

            graphics.setPaint(new Color(46, 101, 217));
            graphics.fillRect(0, 0, defaultBackground.getWidth(), defaultBackground.getHeight());

            this.imageIcon = new ImageIcon(defaultBackground);
        } else {
            this.imageIcon = new ImageIcon(file);
        }
        repaint();
    }

    public WindowPanel(String fileName) {
        setLayout(null);
        setImageIcon(fileName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, null);
    }
}
