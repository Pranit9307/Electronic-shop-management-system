import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Invoice extends JFrame {
    private JTextPane invoiceTextPane;
    private Product product;

    public Invoice(int applianceId, String customerFirstName, String customerLastName, String customerEmail, String phone, String address, String paymentMethod) {
        setTitle("Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set background image
        ImageIcon backgroundImage = new ImageIcon("C:\\\\Users\\\\prani\\\\Downloads\\\\Untitled design (1).jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        getContentPane().setLayout(new BorderLayout());

        // Create a panel for the invoice text
        JPanel invoicePanel = new JPanel(new BorderLayout());
        invoicePanel.setOpaque(false); // Make the panel transparent

        // Initialize components
        invoiceTextPane = new JTextPane();
        invoiceTextPane.setContentType("text/html");
        invoiceTextPane.setEditable(false);
        invoiceTextPane.setOpaque(false);
        invoiceTextPane.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size and style
        invoiceTextPane.setForeground(Color.WHITE); // Set text color
        invoiceTextPane.setAlignmentX(Component.CENTER_ALIGNMENT); // Center text horizontally
        invoiceTextPane.setAlignmentY(Component.CENTER_ALIGNMENT); // Center text vertically

        // Search for the product
        product = searchProduct(applianceId);

        // Construct the invoice text
        constructInvoiceText(product, customerFirstName, customerLastName, customerEmail, phone, address, paymentMethod);

        JScrollPane scrollPane = new JScrollPane(invoiceTextPane);
        scrollPane.setOpaque(false); // Make the scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make the viewport transparent
        scrollPane.setBorder(null); // Remove border

        invoicePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the invoice panel to the center of the frame
        getContentPane().add(invoicePanel, BorderLayout.CENTER);

        // Add download button
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement download functionality
                downloadInvoice();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Make the button panel transparent
        
        JButton btnNewButton = new JButton("Back to Inventory");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show thank you message
                JOptionPane.showMessageDialog(Invoice.this, "Process complete, thank you!", "Message", JOptionPane.INFORMATION_MESSAGE);
                
                // Redirect to Inventory
                // Assuming Inventory is the class for the inventory window
                Inventory inventory = new Inventory();
                inventory.setVisible(true);
                
                // Close the current window
                dispose();
            }
        });
        buttonPanel.add(btnNewButton);
        buttonPanel.add(downloadButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void constructInvoiceText(Product product, String customerFirstName, String customerLastName, String customerEmail, String phone, String address,  String paymentMethod) {
        // Construct the invoice text using the passed parameters
        StringBuilder invoiceText = new StringBuilder();
        invoiceText.append("<html><body>");
        invoiceText.append("<h2 style='color:white;'>Invoice Details:</h2>");
        // Append product data
        invoiceText.append("<h3 style='color:white;'>Product Data:</h3>");
        invoiceText.append("<p style='color:white;'>Name: ").append(product.getName()).append("</p>");
        invoiceText.append("<p style='color:white;'>Brand: ").append(product.getBrand()).append("</p>");
        // Append customer data
        invoiceText.append("<h3 style='color:white;'>Customer Data:</h3>");
        invoiceText.append("<p style='color:white;'>Name: ").append(customerFirstName).append(" ").append(customerLastName).append("</p>");
        invoiceText.append("<p style='color:white;'>Email: ").append(customerEmail).append("</p>");
        invoiceText.append("<p style='color:white;'>Phone: ").append(phone).append("</p>");
        invoiceText.append("<p style='color:white;'>Address: ").append(address).append("</p>");
        // Append total amount paid
        invoiceText.append("<p style='color:white;'>Total Amount Paid: $").append(String.format("%.2f",product.getPrice())).append("</p>");
        // Append payment method
        invoiceText.append("<p style='color:white;'>Payment Method: ").append(paymentMethod).append("</p>");
        invoiceText.append("</body></html>");

        // Set the text in the invoice text pane
        invoiceTextPane.setText(invoiceText.toString());
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

    private void downloadInvoice() {
        // Prompt user to choose file location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Invoice");
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get selected file
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(invoiceTextPane.getText());
                JOptionPane.showMessageDialog(this, "Invoice downloaded successfully.", "Download Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while downloading the invoice.", "Download Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Add any additional methods or fields as needed
    
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
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Invoice(12345, "John", "Doe", "john.doe@example.com", "123456789", "123 Main St", "Credit Card");
            }
        });
    }
}
