package main.ui;

import javax.swing.*;
import java.awt.*;

public class FrameSplashscreen extends JFrame{
    private static final long serialVersionUID = 1L;

    public FrameSplashscreen() {
        super("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setSize(600, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        JPanel container = new JPanel(null);
        UIJPanelBG splashPanel = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/splash.png")));
        ImageIcon loadingIMG = new ImageIcon(getClass().getResource("/res/images/loading.gif"));
        JLabel loadingLabel = new JLabel(loadingIMG);
        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 600, 350);
        container.add(loadingLabel, 0);
        loadingLabel.setBounds(220, 250, 160, 40);
        this.add(container);
        this.setVisible(true);
    }
}
