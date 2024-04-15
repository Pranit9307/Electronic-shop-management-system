import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sale extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int applianceId;
    private JPanel formPanel;
    private JComboBox<String> paymentMethodComboBox;
    private JTextField customerIdField;

    public Sale(int applianceId) {
        this.applianceId = applianceId;

        setTitle("Sale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 50));
        topPanel.setBackground(new Color(0, 0, 0));
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 0, 0));
        JLabel titleLabel = new JLabel("SALES");
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(0, 0, 0));

        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0, 0, 0));
        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About us");
        JButton profileButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(homeButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        Product product = searchProduct(applianceId);
        if (product != null) {
            JPanel productDetailsPanel = new JPanel(new BorderLayout());
            productDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            contentPane.add(productDetailsPanel, BorderLayout.NORTH);

            JLabel productNameLabel = new JLabel("Product: " + product.getName());
            productDetailsPanel.add(productNameLabel, BorderLayout.NORTH);

            JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
            detailsPanel.add(new JLabel("Brand: " + product.getBrand()));
            detailsPanel.add(new JLabel("Category: " + product.getCategory()));
            detailsPanel.add(new JLabel("Price: $" + product.getPrice()));
            detailsPanel.add(new JLabel("Stock: " + product.getStock()));
            productDetailsPanel.add(detailsPanel, BorderLayout.CENTER);

            ImageIcon imageIcon = new ImageIcon(product.getImagePath());
            JLabel imageLabel = new JLabel(imageIcon);
            productDetailsPanel.add(imageLabel, BorderLayout.EAST);

            JButton sellButton = new JButton("Sell");
            contentPane.add(sellButton, BorderLayout.SOUTH);

            JScrollPane scrollPane = new JScrollPane();
            formPanel = new JPanel(new GridLayout(0, 2));
            scrollPane.setViewportView(formPanel);
            contentPane.add(scrollPane, BorderLayout.CENTER);

            formPanel.add(new JLabel("Customer ID:"));
            customerIdField = new JTextField();
            formPanel.add(customerIdField);
            
            formPanel.add(new JLabel("Customer First Name:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Customer Last Name:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Customer Email:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Phone:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Address:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Sale ID:"));
            formPanel.add(new JTextField());
            formPanel.add(new JLabel("Payment Method:"));
            paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Credit Card", "Debit Card"});
            formPanel.add(paymentMethodComboBox);
            formPanel.add(new JLabel("Stock (No of Units):"));
            formPanel.add(new JTextField());
        } else {
            JLabel notFoundLabel = new JLabel("Product not found.");
            contentPane.add(notFoundLabel, BorderLayout.CENTER);
        }

        getContentPane().add(contentPane, BorderLayout.CENTER);

        // Add sell button
        JButton sellButton = new JButton("Sell");
        contentPane.add(sellButton, BorderLayout.SOUTH);

        // ActionListener for sellButton
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellProduct();
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

        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current Sale frame
                // Open the inventory page here
                // Replace "InventoryPage" with the class name of your inventory page
                Inventory inventoryPage = new Inventory();
                inventoryPage.setVisible(true);
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
        
      

        setVisible(true);
    }

    private Product searchProduct(int applianceId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;

        try {
            connection = DBUtil.getDBConnection();

            String query = "SELECT * FROM EAppliances WHERE ApplianceID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, applianceId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                String brand = resultSet.getString("Brand");
                String category = resultSet.getString("Category");
                double price = resultSet.getDouble("Price");
                int stock = resultSet.getInt("StockQuantity");
                String imagePath = resultSet.getString("ImagePath");
                product = new Product(applianceId, name, brand, category, price, stock, imagePath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return product;
    }

    private void sellProduct() {
        // Retrieve data from form fields
        String customerID = customerIdField.getText();
        String customerFirstName = ((JTextField) formPanel.getComponent(3)).getText();
        String customerLastName = ((JTextField) formPanel.getComponent(5)).getText();
        String customerEmail = ((JTextField) formPanel.getComponent(7)).getText();
        String phone = ((JTextField) formPanel.getComponent(9)).getText();
        String address = ((JTextField) formPanel.getComponent(11)).getText();
        String saleId = ((JTextField) formPanel.getComponent(13)).getText();
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        int stockSold = Integer.parseInt(((JTextField) formPanel.getComponent(17)).getText());

        // Get product details
        Product product = searchProduct(applianceId);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBUtil.getDBConnection();

            if (customerID.isEmpty()) { // Check if customer ID is empty
                JOptionPane.showMessageDialog(this, "Customer ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert data into E_Customer table
            if (insertCustomer(connection, Long.parseLong(customerID), customerFirstName, customerLastName, customerEmail, phone, address)) {
                // Insert sale data only if insertion into E_Customer was successful
                insertSale(connection, Integer.parseInt(saleId), Long.parseLong(customerID), paymentMethod, product.getPrice());
                // Update stock quantity
                updateStockQuantity(connection, stockSold);
                
                
                
                //----------------------------------------------------------------
                int paymentConfirmation = JOptionPane.showConfirmDialog(this, "Has the payment been completed?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);
                if (paymentConfirmation == JOptionPane.YES_OPTION) {
                    // Payment completed, redirect to the Invoice page
                    dispose(); // Close the current Sale frame
                 // Instantiate the Invoice class from the Sale page
                    Invoice invoicePage = new Invoice(applianceId, customerFirstName, customerLastName, customerEmail, phone, address, paymentMethod);
                    invoicePage.setVisible(true);
                } else {
                    // Payment not completed, stay on the current page
                }
                //------------------------------------------------------------------
                JOptionPane.showMessageDialog(this, "Sale completed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                
                
                
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add customer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while processing the sale.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to insert data into E_Customer table
    private boolean insertCustomer(Connection connection, long customerId, String firstName, String lastName, String email, String phone, String address) throws SQLException {
        String query = "INSERT INTO E_Customer (CustomerID, FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setLong(1, customerId);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, email);
        pstmt.setString(5, phone);
        pstmt.setString(6, address);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    private void insertSale(Connection connection, int saleId, long customerId, String paymentMethod, double productPrice) throws SQLException {
        String query = "INSERT INTO Sales (SaleID, CustomerID, SaleDate, TotalAmount, PaymentMethod) VALUES (?, ?, CURRENT_DATE, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, saleId);
        pstmt.setLong(2, customerId);
        pstmt.setDouble(3, productPrice); // Set product price
        pstmt.setString(4, paymentMethod);
        pstmt.executeUpdate();
    }

    private void updateStockQuantity(Connection connection, int stockSold) throws SQLException {
        String query = "UPDATE EAppliances SET StockQuantity = StockQuantity - ? WHERE ApplianceID = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, stockSold);
        pstmt.setInt(2, applianceId);
        pstmt.executeUpdate();
    }

    private static class Product {
        private int applianceID;
        private String name;
        private String brand;
        private String category;
        private double price;
        private int stock;
        private String imagePath;

        public Product(int applianceID, String name, String brand, String category, double price, int stock, String imagePath) {
            this.applianceID = applianceID;
            this.name = name;
            this.brand = brand;
            this.category = category;
            this.price = price;
            this.stock = stock;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sale sale = new Sale(1);
        });
    }
}