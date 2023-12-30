package main.ui;

import javax.swing.*;
import java.awt.*;

public class FrameSplashscreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int SPLASH_WIDTH = 600;
    private static final int SPLASH_HEIGHT = 350;
    private static final int LOADING_LABEL_X = 220;
    private static final int LOADING_LABEL_Y = 250;

    public FrameSplashscreen() {
        super("Battleship");
        initializeUI();
    }

    private void initializeUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
        centerFrameOnScreen();

        UIJPanelBG splashPanel = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/splash.png")));

        ImageIcon loadingIMG = new ImageIcon(getClass().getResource("/res/images/loading.gif"));
        JLabel loadingLabel = new JLabel(loadingIMG);

        this.add(splashPanel);
        splashPanel.setBounds(0, 0, SPLASH_WIDTH, SPLASH_HEIGHT);
        loadingLabel.setBounds(LOADING_LABEL_X, LOADING_LABEL_Y, 160, 40);

        this.setVisible(true);

        // Simulate a loading process with Thread.sleep
        try {
            Thread.sleep(3000); // Adjust the time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.dispose(); // Close the splash screen when loading is done
    }

    private void centerFrameOnScreen() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }
}
