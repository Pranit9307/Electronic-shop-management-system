import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JButton registerButton;

    public RegistrationForm() {
        initComponents();
    }

    private void initComponents() {
        // Create JPanel with background image
        JPanel jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                Image image = new ImageIcon("C:\\\\Users\\\\prani\\\\Downloads\\\\Untitled design (1).jpg").getImage();
                // Draw the image at the specified position
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set window size
        setSize(800, 600);

        jPanel1.setForeground(new Color(255, 255, 255));
        jPanel1.setBackground(new Color(0, 0, 51));
        JLabel jLabel1 = new JLabel();
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 24));
        jLabel1.setText("Registration Form");

        JLabel jLabel2 = new JLabel();
        jLabel2.setForeground(new Color(248, 248, 255));
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel2.setText("ID");

        JLabel jLabel3 = new JLabel();
        jLabel3.setForeground(new Color(230, 230, 250));
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel3.setText("Password");

        JLabel jLabel4 = new JLabel(); 
        jLabel4.setForeground(new Color(230, 230, 250));
        jLabel4.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel4.setText("Name");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        nameField = new JTextField(); 

        registerButton = new JButton();
        registerButton.setBackground(new Color(255, 182, 193));
        registerButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
        registerButton.setText("Register");
        registerButton.addActionListener(this);

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)) 
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(usernameField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE) // Added nameField
                            .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE) // Added nameField
                    .addComponent(jLabel4)) // Added jLabel4
                .addGap(18, 18, 18)
                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String id = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText(); 

            try {
                Connection connection = DBUtil.getDBConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (Id, Password, Name) VALUES (?, ?, ?)");
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name); 
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                login login = new login();
                login.setVisible(true);                
                this.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering user!");
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationForm().setVisible(true);
            }
        });
    }
}
