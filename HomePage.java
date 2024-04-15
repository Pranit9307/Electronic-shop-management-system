import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends javax.swing.JFrame implements ActionListener {

    private JButton aboutButton;
    private JButton loginButton;

    /** Creates new form HomePage */
    public HomePage() {
        initComponents();

        setSize(800, 600);

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        navigationPanel.setBackground(new Color(0, 0, 0));
        aboutButton = new JButton("About");
        loginButton = new JButton("Login");

        aboutButton.addActionListener(this);
        loginButton.addActionListener(this);

        navigationPanel.add(aboutButton);
        navigationPanel.add(loginButton);

        getContentPane().add(navigationPanel, BorderLayout.NORTH);     }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Electronic Shop");

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("C:\\Users\\prani\\Downloads\\Untitled design (1).jpg").getImage(); 
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        getContentPane().add(backgroundPanel);

        // Add logo label
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\prani\\Downloads\\.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        backgroundPanel.add(logoLabel, BorderLayout.NORTH); 

        // Add title label
        JLabel titleLabel = new JLabel("Welcome to Electronic Shop");
        titleLabel.setForeground(new Color(248, 248, 255));
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(titleLabel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() == aboutButton) {
            String aboutMessage = "Welcome to our Electronic Home Appliance Store!\n\n"
                + "At our store, we aim to provide you with a seamless shopping experience for all your electronic home appliance needs.\n\n"
                + "Our systems are designed to integrate inventory management, sales monitoring, and customer communication to ensure efficient operations.\n\n"
                + "We understand the importance of effective inventory management for tracking and restocking items, as well as the significance of careful inventory control in sales management.\n\n"
                + "Feel free to explore our wide range of products and rely on our dedicated team to assist you with any inquiries or purchases.\n\n"
                + "Thank you for choosing us for your electronic home appliance needs!";
                
            JOptionPane.showMessageDialog(this, aboutMessage, "About Our Store", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == loginButton) {
            dispose(); 
            login loginPage = new login(); 
            loginPage.setVisible(true); 
        }
    }

    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }
}
