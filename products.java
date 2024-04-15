import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class products extends JFrame {
    private List<Product> productList;
    private JTable productTable;
    private JTextField searchField;

    public products() {
        setTitle("All Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());

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
        JButton profileButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(homeButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Product table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(tablePanel, BorderLayout.CENTER);

        retrieveProductData();

        
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                searchProducts(searchTerm);
            }
        });

        setVisible(true);
    }

    private void retrieveProductData() {
        productList = new ArrayList<>();
        try {
            Connection connection = DBUtil.getDBConnection();
            String query = "SELECT * FROM EAppliances";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int applianceID = resultSet.getInt("ApplianceID");
                String name = resultSet.getString("Name");
                String brand = resultSet.getString("Brand");
                String category = resultSet.getString("Category");
                double price = resultSet.getDouble("Price");
                int stockQuantity = resultSet.getInt("StockQuantity");
                int reorderLevel = resultSet.getInt("ReorderLevel");
                int reorderQuantity = resultSet.getInt("ReorderQuantity");
                String imagePath = resultSet.getString("ImagePath");
                productList.add(new Product(applianceID, name, brand, category, price, stockQuantity, reorderLevel, reorderQuantity, imagePath));
            }
            displayProducts(productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProducts(List<Product> products) {
        ProductTableModel model = new ProductTableModel(products);
        productTable.setModel((TableModel) model);
    }

    private void searchProducts(String searchTerm) {
        List<Product> searchResults = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(product);
            }
        }
        displayProducts(searchResults);
    }

    private static class Product {
        private int applianceID;
        private String name;
        private String brand;
        private String category;
        private double price;
        private int stockQuantity;
        private int reorderLevel;
        private int reorderQuantity;
        private String imagePath;

        public Product(int applianceID, String name, String brand, String category, double price, int stockQuantity, int reorderLevel, int reorderQuantity, String imagePath) {
            this.applianceID = applianceID;
            this.name = name;
            this.brand = brand;
            this.category = category;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.reorderLevel = reorderLevel;
            this.reorderQuantity = reorderQuantity;
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

        public int getStockQuantity() {
            return stockQuantity;
        }

        public int getReorderLevel() {
            return reorderLevel;
        }

        public int getReorderQuantity() {
            return reorderQuantity;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    private static class ProductTableModel extends AbstractTableModel {
        private List<Product> products;
        private String[] columnNames = {"ID", "Name", "Brand", "Category", "Price", "Stock Quantity", "Reorder Level", "Reorder Quantity"};

        public ProductTableModel(List<Product> products) {
            this.products = products;
        }
        
        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Product product = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return product.getApplianceID();
                case 1:
                    return product.getName();
                case 2:
                    return product.getBrand();
                case 3:
                    return product.getCategory();
                case 4:
                    return product.getPrice();
                case 5:
                    return product.getStockQuantity();
                case 6:
                    return product.getReorderLevel();
                case 7:
                    return product.getReorderQuantity();
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new products();
            }
        });
    }
}
