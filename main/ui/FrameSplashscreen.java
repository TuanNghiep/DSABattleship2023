import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSplashscreen extends JFrame {

    public FrameSplashscreen() {
        setTitle("Battleship Splashscreen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add components to the frame, e.g., JLabel for the splash screen
        JLabel splashLabel = new JLabel("Battleship Game Splash Screen");
        splashLabel.setFont(new Font("Arial", Font.BOLD, 20));
        splashLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(splashLabel);

        // Set layout, size, and other frame properties
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame

        // Use Timer to close the splash screen after a delay (e.g., 3 seconds)
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the splash screen
                new FrameManageOctopus(); // Open the main frame
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrameSplashscreen());
    }
}
