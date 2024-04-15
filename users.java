import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class users extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Database connection
    private Connection conn;
    private PreparedStatement pstmt;

    private JPanel userInfoPanel;
    private JTable userTable;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    users frame = new users();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public users() {
        // Connect to database
        conn = DBUtil.getDBConnection(); // Assuming you have a DBUtil class for database connection

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set window size
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Navigation bar
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navigationPanel.setBackground(new Color(0, 0, 0));

        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About us");
        JButton profileButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");

        navigationPanel.add(homeButton);
        navigationPanel.add(aboutButton);
        navigationPanel.add(profileButton);
        navigationPanel.add(logoutButton);

        contentPane.add(navigationPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Id", "Password", "Name"});
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(tablePanel, BorderLayout.CENTER);

        loadAllUsers();
        
        
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to DashboardPage
                dispose(); // Close the current JFrame
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            }
        });
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display the about us information
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

    // Method to load all users from the database and populate the table
    private void loadAllUsers() {
        DefaultTableModel tableModel = (DefaultTableModel) userTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String userId = rs.getString("Id");
                String password = rs.getString("Password");
                String name = rs.getString("Name");
                Object[] row = {userId, password, name};
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}