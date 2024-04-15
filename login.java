import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class login extends javax.swing.JFrame implements ActionListener {

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JLabel l1;
    private JButton registerButton;
    private JPasswordField p1;
    private JTextField t1;
    private Connection connection;
    private JButton homeButton;
    private JButton aboutButton;
    private JButton contactButton;

    public login() {
        connectToDatabase(); 
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(x, y);

        setSize(800, 600);

        setLocationRelativeTo(null);

        JPanel navigationPanel = new JPanel(new FlowLayout());
        homeButton = new JButton("Home");
        aboutButton = new JButton("About");
        contactButton = new JButton("Contact");
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        homeButton.addActionListener(this);
        aboutButton.addActionListener(this);
        contactButton.addActionListener(this);
        navigationPanel.add(registerButton);
        navigationPanel.add(homeButton);
        navigationPanel.add(aboutButton);
        navigationPanel.add(contactButton);

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(navigationPanel, BorderLayout.NORTH);
        getContentPane().add(jPanel1, BorderLayout.CENTER);

        homeButton.addActionListener(this);
        aboutButton.addActionListener(this);
        contactButton.addActionListener(this);

        navigationPanel.add(homeButton);
        navigationPanel.add(aboutButton);
        navigationPanel.add(contactButton);
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel() {
            // Override paintComponent to set background image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon imageIcon = new ImageIcon("C:\\\\Users\\\\prani\\\\Downloads\\\\Untitled design (1).jpg"); // Adjust the file name as per your image
                // Draw the image on the panel
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jPanel1.setForeground(new Color(255, 255, 255));
        // Remove background color
        jPanel1.setOpaque(false); 
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setForeground(new Color(248, 248, 255));
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setForeground(new Color(230, 230, 250));
        t1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        l1 = new javax.swing.JLabel();
        l1.setForeground(new Color(255, 245, 238));
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        p1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setText("         Electronic shop Management System");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel2.setText("Id");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setText("Password");

        jButton1.setBackground(new Color(255, 182, 193));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        l1.setFont(new java.awt.Font("Tahoma", 1, 14));
        l1.setText("Result");

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton2.setForeground(new java.awt.Color(0, 51, 255));
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton3.setForeground(new java.awt.Color(204, 0, 0));
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 620, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(170, 170, 170)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(p1)
                                    .addComponent(t1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32)))
                        .addGap(152))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addComponent(l1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(l1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(62))
        );

        pack();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        t1.setText(null);
        p1.setText(null);
        l1.setText("Result");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
            String username = "system"; 
            String password = "pranit123"; 
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database!");
            System.exit(1);
        }
    }

    private boolean authenticate(String id, String password) {
        PreparedStatement statement = null; // Declare statement variable here
        try {
            String query = "SELECT Id,Password FROM users WHERE Id = ? AND Password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to authenticate user. Please try again later.");
            return false;
        } finally {
            try {
                // Close resources
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

//------------------------------------------------------------------------------------------
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {       
    	connectToDatabase(); 
        String id = t1.getText();
        String password = new String(p1.getPassword()); 
        if (authenticate(id, password)) {
            l1.setText("Successful");
            //dispose(); 
            DashboardPage dashboard = new DashboardPage();
            dashboard.setVisible(true);
        } else {
            l1.setText("Error");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            HomePage homepage = new HomePage();
            homepage.setVisible(true); 
        } else if (e.getSource() == aboutButton) {
            String aboutMessage = "Welcome to our Electronic Home Appliance Store!\n\n"
                    + "At our store, we aim to provide you with a seamless shopping experience for all your electronic home appliance needs.\n\n"
                    + "Our systems are designed to integrate inventory management, sales monitoring, and customer communication to ensure efficient operations.\n\n"
                    + "We understand the importance of effective inventory management for tracking and restocking items, as well as the significance of careful inventory control in sales management.\n\n"
                    + "Feel free to explore our wide range of products and rely on our dedicated team to assist you with any inquiries or purchases.\n\n"
                    + "Thank you for choosing us for your electronic home appliance needs!";
                    
                JOptionPane.showMessageDialog(this, aboutMessage, "About Our Store", JOptionPane.INFORMATION_MESSAGE);        
        } else if (e.getSource() == contactButton) {
            displayHardcodedContacts();
        } else if (e.getSource() == registerButton) { 
            RegistrationForm registrationForm = new RegistrationForm(); 
            registrationForm.setVisible(true); 
        }
    }

    private void displayHardcodedContacts() {
        String contact1 = "Name:Pranit Sarode, Phone Number: 9307145326, PRN: 122B1B253";
        String contact2 = "Name: Arnav Sonawane, Phone Number: 0987654321, PRN:122B1B265";
        String contact3 = "Name: Pranav Sakpal, Phone Number: 9876543210, PRN:1221B248";
        
        String contacts = "Contacts:\n" + contact1 + "\n" + contact2 + "\n" + contact3;
        
        JOptionPane.showMessageDialog(this, contacts);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }
}
