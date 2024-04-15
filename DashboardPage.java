import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DashboardPage extends JFrame {
    public DashboardPage() {
        setTitle("Electronic Shop Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); 
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 50)); 
        topPanel.setBackground(new Color(0, 0, 0)); 

        JLabel titleLabel = new JLabel("Electronic Shop Management System");
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0, 0, 0));

        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About us");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        // Customize buttons
        customizeButton(homeButton);
        customizeButton(aboutButton);
        customizeButton(profileButton);
        customizeButton(logoutButton);

        buttonPanel.add(homeButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Main section
        JPanel mainPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Layout for 3 boxes in one row
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around buttons

        // Image paths for each button
        String inventoryImagePath = "C:\\Users\\prani\\Downloads\\inventory\\inventory.png";
        String customerImagePath = "C:\\\\Users\\\\prani\\\\Downloads\\\\inventory\\\\customer_icon.png";
        String currentStockImagePath = "C:\\\\Users\\\\prani\\\\Downloads\\\\inventory\\\\stock_icon.png";

        // Add buttons with images
        mainPanel.add(createButtonWithImageAndButton("Inventory", inventoryImagePath));
        mainPanel.add(createButtonWithImageAndButton("Customer", customerImagePath));
        mainPanel.add(createButtonWithImageAndButton("Current Stock", currentStockImagePath));

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Second panel for the second row of buttons
        JPanel secondRowPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Layout for second row
        secondRowPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around buttons

        // Image paths for second row of buttons
        String suppliersImagePath = "C:\\\\Users\\\\prani\\\\Downloads\\\\inventory\\\\supplier_icon.png";
        String salesImagePath = "C:\\\\Users\\\\prani\\\\Downloads\\\\inventory\\\\sales_icon.png";
        String usersImagePath = "C:\\\\Users\\\\prani\\\\Downloads\\\\inventory\\\\users_icon.png";

        // Add buttons for the second row
        secondRowPanel.add(createButtonWithImageAndButton("Suppliers", suppliersImagePath));
        secondRowPanel.add(createButtonWithImageAndButton("Sales", salesImagePath));
        secondRowPanel.add(createButtonWithImageAndButton("Users", usersImagePath));

        getContentPane().add(secondRowPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Adding action listener to logoutButton
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to Login.java
                dispose(); // Close the current JFrame
                login login = new login();
                login.setVisible(true);
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
            }
        });
        
        
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String aboutUsMessage = "Welcome to our Electronic Home Appliance Store!\n\n"
                    + "At our store, we aim to provide you with a seamless shopping experience for all your electronic home appliance needs.\n\n"
                    + "Our systems are designed to integrate inventory management, sales monitoring, and customer communication to ensure efficient operations.\n\n"
                    + "We understand the importance of effective inventory management for tracking and restocking items, as well as the significance of careful inventory control in sales management.\n\n"
                    + "Feel free to explore our wide range of products and rely on our dedicated team to assist you with any inquiries or purchases.\n\n"
                    + "Thank you for choosing us for your electronic home appliance needs!";
                JOptionPane.showMessageDialog(null, aboutUsMessage, "About Us", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(0, 102, 204)); 
        button.setForeground(Color.WHITE); 
        button.setFont(new Font("Arial", Font.BOLD, 12)); 
        button.setFocusPainted(false); 
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(100, 30)); 
    }

    private JPanel createButtonWithImageAndButton(String buttonText, String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 200)); 
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(200, 50)); 

        customizeButton(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           
                if (buttonText.equals("Inventory")) {
                    
                    Inventory inventoryPage = new Inventory();
                    inventoryPage.setVisible(true);
                    dispose(); 
                } else if (buttonText.equals("Customer")) {
                   
                    Customer customerPage = new Customer();
                    customerPage.setVisible(true);
                    dispose();
                } else if (buttonText.equals("Current Stock")) {
                                       products currentStockPage = new products();
                    currentStockPage.setVisible(true);
                    dispose(); 
                }
                else if (buttonText.equals("Sales")) {
                  
                     SalesPage sales= new SalesPage();
                     sales.setVisible(true);
                    dispose(); 
                }else if (buttonText.equals("Suppliers")) {
                  
                    Supplier currentStockPage = new Supplier();
                    currentStockPage.setVisible(true);
                    dispose(); 
                }else if (buttonText.equals("Users")) {
                    
                    users currentStockPage = new users();
                    currentStockPage.setVisible(true);
                    dispose();                 }
            }
        });

        panel.add(button, BorderLayout.SOUTH);

        ImageIcon imageIcon = createResizedImageIcon(imagePath, 100, 100); 
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(100, 100)); 

        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }


    private ImageIcon createResizedImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardPage());
    }
}
