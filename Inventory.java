import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends JFrame {
    private List<Product> productList;
    private DefaultListModel<Product> productListModel; 
    private JPanel productInfoPanel;
    private JTextField searchField;
    public Inventory() {
        setTitle("Inventory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); 

        productList = new ArrayList<>();

        retrieveProductData();

        productListModel = new DefaultListModel<>();
        for (Product product : productList) {
            productListModel.addElement(product);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 50)); 
        topPanel.setBackground(new Color(0, 0, 0)); 
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 0, 0));
        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(0, 0, 0));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0, 0, 0));
        JButton homeButton = new JButton("Home");
        JButton aboutButton = new JButton("About us");
        JButton profileButton = new JButton("Profile");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(homeButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());

        productInfoPanel = new JPanel(new GridLayout(0, 3, 10, 10)); 
        productInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(productInfoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        displayProducts(productList);

        JPanel addRemovePanel = new JPanel(new GridLayout(0, 2)); 
        addRemovePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        addRemovePanel.add(addButton);
        
        JButton All = new JButton("ALL Products");
        addRemovePanel.add(All);
        addRemovePanel.add(removeButton);
        contentPanel.add(addRemovePanel, BorderLayout.SOUTH);
        
        JButton back = new JButton("Back");
        addRemovePanel.add(back);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog("Enter product ID:");
                String name = JOptionPane.showInputDialog("Enter product name:");
                String brand = JOptionPane.showInputDialog("Enter product brand:");
                String category = JOptionPane.showInputDialog("Enter product category:");
                String priceStr = JOptionPane.showInputDialog("Enter product price:");
                String stockQuantityStr = JOptionPane.showInputDialog("Enter product stock quantity:");
                String reorderLevelStr = JOptionPane.showInputDialog("Enter product reorder level:");
                String reorderQuantityStr = JOptionPane.showInputDialog("Enter product reorder quantity:");
                String imagePath = JOptionPane.showInputDialog("Enter product image link:");

                try {
                    // Parse numerical values
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);
                    int stockQuantity = Integer.parseInt(stockQuantityStr);
                    int reorderLevel = Integer.parseInt(reorderLevelStr);
                    int reorderQuantity = Integer.parseInt(reorderQuantityStr);

                    // Insert the new product into the database
                    addProductToDatabase(id, name, brand, category, price, stockQuantity, reorderLevel, reorderQuantity, imagePath);

                    // Refresh the inventory display
                    retrieveProductData();
                    displayProducts(productList);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Inventory.this, "Invalid input for product ID, price, stock quantity, reorder level, or reorder quantity.");
                }
            }
        });



        All.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            	
            	  products all = new products();
                  all.setVisible(true);
            }
        });
        
     
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> productNames = new ArrayList<>();
                for (Product product : productList) {
                    productNames.add(product.getName());
                }
                
                String selectedProductName = (String) JOptionPane.showInputDialog(
                    Inventory.this,
                    "Select the product to remove:",
                    "Remove Product",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    productNames.toArray(),
                    productNames.get(0)
                );
                
                if (selectedProductName != null) {
                    // Ask for the number of units to remove
                    String unitsToRemoveStr = JOptionPane.showInputDialog("Enter the number of units to remove:");
                    if (unitsToRemoveStr != null && !unitsToRemoveStr.isEmpty()) {
                        try {
                            int unitsToRemove = Integer.parseInt(unitsToRemoveStr);
                            // Decrease stock quantity in the database
                            removeProduct(selectedProductName, unitsToRemove);
                            // Refresh the inventory display
                            retrieveProductData();
                            displayProducts(productList);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Inventory.this, "Invalid input for units to remove.");
                        }
                    }
                }
            }
        });

        

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                login login = new login();
                login.setVisible(true);
                dispose();
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

        
      
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                List<Product> searchResults = searchProducts(searchTerm);
                displayProducts(searchResults);
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
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void retrieveProductData() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getDBConnection();

            String query = "SELECT * FROM EAppliances";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int applianceID = resultSet.getInt("ApplianceID");
                String name = resultSet.getString("Name");
                String brand = resultSet.getString("Brand");
                String category = resultSet.getString("Category");
                double price = resultSet.getDouble("Price");
                String imagePath = resultSet.getString("ImagePath");
                productList.add(new Product(applianceID, name, brand, category, price, imagePath));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    
    

    private void addProductToDatabase(int id, String name, String brand, String category, double price,
                                       int stockQuantity, int reorderLevel, int reorderQuantity, String imagePath) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getDBConnection();

            String query = "INSERT INTO EAppliances (ApplianceID, Name, Brand, Category, Price, StockQuantity, ReorderLevel, ReorderQuantity, ImagePath) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, brand);
            preparedStatement.setString(4, category);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, stockQuantity);
            preparedStatement.setInt(7, reorderLevel);
            preparedStatement.setInt(8, reorderQuantity);
            preparedStatement.setString(9, imagePath);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    
    
    
    
    
    
    private void removeProduct(String productName, int unitsToRemove) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            
            connection = DBUtil.getDBConnection();

            String query = "UPDATE EAppliances SET StockQuantity = StockQuantity - ? WHERE Name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, unitsToRemove);
            preparedStatement.setString(2, productName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Product> searchProducts(String searchTerm) {
        List<Product> searchResults = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    private void displayProducts(List<Product> products) {
        productInfoPanel.removeAll();
        for (Product product : products) {
            JPanel productPanel = createProductPanel(product);
            productInfoPanel.add(productPanel);
        }
        productInfoPanel.revalidate();
        productInfoPanel.repaint();
    }

    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setPreferredSize(new Dimension(150, 200)); 
        productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ImageIcon icon = new ImageIcon(product.getImagePath());
        JLabel imageLabel = new JLabel(icon);

        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        imageLabel.setIcon(icon);

        productPanel.add(imageLabel, BorderLayout.CENTER); 

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        JLabel nameLabel = new JLabel(product.getName());
        JLabel categoryLabel = new JLabel("Category: " + product.getCategory());
        JLabel priceLabel = new JLabel("Price: $" + product.getPrice());

        infoPanel.add(nameLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(priceLabel);

        productPanel.add(infoPanel, BorderLayout.NORTH);

        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int applianceID = ((Product)((JPanel) ((JButton)e.getSource()).getParent()).getClientProperty("product")).getApplianceID();
                
                Sale salesPage = new Sale(applianceID);
                salesPage.setVisible(true);

            }
        });

        productPanel.add(sellButton, BorderLayout.SOUTH); 

        productPanel.putClientProperty("product", product);

        productPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayProductInformation(product);
            }
        });

        return productPanel;
    }


     

    private void displayProductInformation(Product product) {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("Name: ").append(product.getName()).append("\n");
        infoBuilder.append("Brand: ").append(product.getBrand()).append("\n");
        infoBuilder.append("Category: ").append(product.getCategory()).append("\n");
        infoBuilder.append("Price: $").append(product.getPrice()).append("\n");

        JOptionPane.showMessageDialog(this, infoBuilder.toString(), "Product Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class Product {
        private int applianceID;
        private String name;
        private String brand;
        private String category;
        private double price;
        private String imagePath;

        public Product(int applianceID, String name, String brand, String category, double price, String imagePath) {
            this.applianceID = applianceID;
            this.name = name;
            this.brand = brand;
            this.category = category;
            this.price = price;
            this.imagePath = imagePath;
        }

        public int getApplianceID() {
            return applianceID;
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

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Inventory();
            }
        });
    }
}
