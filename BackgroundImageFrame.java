import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundImageFrame extends JFrame {
    private JPanel contentPane;

    public BackgroundImageFrame() {
        setTitle("Background Image Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create content pane with background image
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(new File("C:\\Users\\prani\\Downloads\\Untitled design (1).jpg")); // Replace with your image file
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Add your components on top of the content pane
        JLabel label = new JLabel("This is a label on top of the background image");
        label.setForeground(Color.WHITE); // Set text color to contrast with background
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BackgroundImageFrame::new);
    }
}
