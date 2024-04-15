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

public class Supplier extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Database connection
    private Connection conn;
    private PreparedStatement pstmt;

    private JPanel supplierInfoPanel;
    private JTable supplierTable;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Supplier frame = new Supplier();
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
    public Supplier() {
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

        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"SupplierID", "Name", "Contact Person", "Email", "Phone"});
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton addSupplierButton = new JButton("Add Supplier");
        JButton removeSupplierButton = new JButton("Remove Supplier");

        buttonsPanel.add(addSupplierButton);
        buttonsPanel.add(removeSupplierButton);

        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        // ActionListener for "Add Supplier" button
        addSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for supplier details
                String name = JOptionPane.showInputDialog("Enter supplier name:");
                String contactPerson = JOptionPane.showInputDialog("Enter contact person:");
                String email = JOptionPane.showInputDialog("Enter email:");
                String phone = JOptionPane.showInputDialog("Enter phone:");

                // Add the supplier
                addSupplier(name, contactPerson, email, phone);
            }
        });

        // ActionListener for "Remove Supplier" button
        removeSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to select which supplier to remove
                int selectedRow = supplierTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a supplier to remove.");
                    return;
                }
                int confirmDialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this supplier?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
                if (confirmDialog == JOptionPane.YES_OPTION) {
                    int supplierId = (int) supplierTable.getValueAt(selectedRow, 0);
                    removeSupplier(supplierId);
                }
            }
        });
        
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
        loadAllSuppliers();
    }

    // Method to add a supplier to the database and update the table
    private void addSupplier(String name, String contactPerson, String email, String phone) {
        try {
            String sql = "INSERT INTO Suppliers (SupplierID, Name, ContactPerson, Email, Phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNextSupplierId());
            pstmt.setString(2, name);
            pstmt.setString(3, contactPerson);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Supplier added successfully!");
            loadAllSuppliers(); // Refresh the table
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to remove a supplier from the database and update the table
    private void removeSupplier(int supplierId) {
        try {
            String sql = "DELETE FROM Suppliers WHERE SupplierID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, supplierId);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Supplier removed successfully!");
            loadAllSuppliers(); // Refresh the table
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to load all suppliers from the database and populate the table
    private void loadAllSuppliers() {
        DefaultTableModel tableModel = (DefaultTableModel) supplierTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Suppliers");
            while (rs.next()) {
                int supplierId = rs.getInt("SupplierID");
                String name = rs.getString("Name");
                String contactPerson = rs.getString("ContactPerson");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                Object[] row = {supplierId, name, contactPerson, email, phone};
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to get the next available SupplierID
    private int getNextSupplierId() throws SQLException {
        String sql = "SELECT MAX(SupplierID) AS maxId FROM Suppliers";
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
