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

public class Customer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private Connection conn;
    private PreparedStatement pstmt;

    private JPanel productInfoPanel;
    private JTable customerTable;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Customer frame = new Customer();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

 
    public Customer() {
        conn = DBUtil.getDBConnection(); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

       
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navigationPanel.setBackground(new Color(0, 0, 0));

        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About us");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        navigationPanel.add(homeButton);
        navigationPanel.add(aboutButton);
        navigationPanel.add(profileButton);
        navigationPanel.add(logoutButton);

        contentPane.add(navigationPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"CustomerID", "FirstName", "LastName", "Email", "Phone", "Address", "ProductID", "ProductName"});
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton addCustomerButton = new JButton("Add Customer");
        JButton removeCustomerButton = new JButton("Remove Customer");

        buttonsPanel.add(addCustomerButton);
        buttonsPanel.add(removeCustomerButton);

        contentPane.add(buttonsPanel, BorderLayout.SOUTH);
        
        JButton back = new JButton("back");
        buttonsPanel.add(back);

        addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = JOptionPane.showInputDialog("Enter first name:");
                String lastName = JOptionPane.showInputDialog("Enter last name:");
                String email = JOptionPane.showInputDialog("Enter email:");
                String phone = JOptionPane.showInputDialog("Enter phone:");
                String address = JOptionPane.showInputDialog("Enter address:");
                String productID = JOptionPane.showInputDialog("Enter product ID:");
                String productName = JOptionPane.showInputDialog("Enter product name:");

                addCustomer(firstName, lastName, email, phone, address, productID, productName);
            }
        });
        
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
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
        
        
        removeCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a customer to remove.");
                    return;
                }
                int confirmDialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this customer?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
                if (confirmDialog == JOptionPane.YES_OPTION) {
                    int customerId = (int) customerTable.getValueAt(selectedRow, 0);
                    removeCustomer(customerId);
                }
            }
        });

        loadAllCustomers();
    }

    private void addCustomer(String firstName, String lastName, String email, String phone, String address, String productID, String productName) {
        try {
            String sql = "INSERT INTO E_Customer (CustomerID, FirstName, LastName, Email, Phone, Address, ProductID, ProductName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNextCustomerId());
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, address);
            pstmt.setInt(7, Integer.parseInt(productID));
            pstmt.setString(8, productName);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Customer added successfully!");
            loadAllCustomers(); 
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeCustomer(int customerId) {
        try {
            String sql = "DELETE FROM E_Customer WHERE CustomerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Customer removed successfully!");
            loadAllCustomers(); // Refresh the table
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadAllCustomers() {
        DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM E_Customer");
            while (rs.next()) {
                int customerId = rs.getInt("CustomerID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                Object[] row = {customerId, firstName, lastName, email, phone, address, productID, productName};
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getNextCustomerId() throws SQLException {
        String sql = "SELECT MAX(CustomerID) AS maxId FROM E_Customer";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int maxId = 0;
        if (rs.next()) {
            maxId = rs.getInt("maxId");
        }
        rs.close();
        stmt.close();
        return maxId + 1;
    }
}
