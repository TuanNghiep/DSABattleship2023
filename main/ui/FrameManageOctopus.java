import javax.swing.*;

public class FrameManageOctopus extends JFrame {

    public FrameManageOctopus() {
        setTitle("Battleship Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add components to the frame, e.g., UIManagePanel and UIMapPanel
        UIManagePanel managePanel = new UIManagePanel();
        UIMapPanel mapPanel = new UIMapPanel();

        add(managePanel);
        add(mapPanel);

        // Set layout, size, and other frame properties
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrameManageOctopus());
    }
}
