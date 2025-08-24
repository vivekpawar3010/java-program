package studywork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JavaStocks extends HistoryPage{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StockPortfolioTracker portfolioTracker;

    private JFrame frame;
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField dateField;
    private JTextArea outputArea;

    public JavaStocks() {
        portfolioTracker = new StockPortfolioTracker();
        initialize();
    }
    private void initialize() {
        frame = new JFrame("Stock Portfolio Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setBounds(10, 10, 80, 25);
        frame.add(symbolLabel);
        symbolField = new JTextField();
        symbolField.setBounds(100, 10, 150, 25);
        frame.add(symbolField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 40, 80, 25);
        frame.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(100, 40, 150, 25);
        frame.add(nameField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 70, 80, 25);
        frame.add(quantityLabel);
        quantityField = new JTextField();
        quantityField.setBounds(100, 70, 150, 25);
        frame.add(quantityField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 100, 80, 25);
        frame.add(priceLabel);
        priceField = new JTextField();
        priceField.setBounds(100, 100, 150, 25);
        frame.add(priceField);

        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
        dateLabel.setBounds(10, 130, 120, 25);
        frame.add(dateLabel);
        dateField = new JTextField();
        dateField.setBounds(130, 130, 120, 25);
        frame.add(dateField);

        JButton addButton = new JButton("Add Stock");
        addButton.setBounds(10, 160, 100, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStockButtonClicked();
            }
        });
        frame.add(addButton);

        JButton refreshButton = new JButton("Refresh Stocks");
        refreshButton.setBounds(120, 160, 120, 25);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStocksButtonClicked();
            }
        });
        frame.add(refreshButton);

        JButton historyButton = new JButton("History");
        historyButton.setBounds(250, 160, 100, 25);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHistoryPage();
            }
        });
        frame.add(historyButton);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(10, 190, 560, 160);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private void openHistoryPage() {
        SwingUtilities.invokeLater(HistoryPage::new);
    }

    private void addStockButtonClicked() {
        String symbol = symbolField.getText();
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        BigDecimal price = new BigDecimal(priceField.getText());
        Date date = Date.valueOf(dateField.getText());

        Stock stock = new Stock(symbol, name, quantity, price, date);
        portfolioTracker.addStock(stock);

        refreshOutput();
    }

//    @SuppressWarnings("unused")
//	private void refreshStocksButtonClicked() {
//        refreshOutput();
//    }

    private void refreshOutput() {
        outputArea.setText("");
        List<Stock> allStocks = portfolioTracker.getAllStocks();
        for (Stock stock : allStocks) {
            outputArea.append(stock.getName() + " (" + stock.getSymbol() + "): $" + stock.getPurchasePrice() + "\n");
        }
    }
    private void refreshStocksButtonClicked() {
        clearInputFields();
        clearOutputArea();
        refreshOutput();
    }

    private void clearInputFields() {
        symbolField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        dateField.setText("");
    }

    private void clearOutputArea() {
        outputArea.setText("");
        
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JavaStocks();
            }
        });
    }

    public static class StockPortfolioTracker {
        private Connection connection;

        public StockPortfolioTracker() {
            try {
                connection = DatabaseConnector.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void addStock(Stock stock) {
            String query = "INSERT INTO stocks (symbol, name, quantity, purchase_price, purchase_date) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, stock.getSymbol());
                stmt.setString(2, stock.getName());
                stmt.setInt(3, stock.getQuantity());
                stmt.setBigDecimal(4, stock.getPurchasePrice());
                stmt.setDate(5, stock.getPurchaseDate());
                stmt.executeUpdate();
                System.out.println("Stock added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Stock> getAllStocks() {
            List<Stock> stocks = new ArrayList<>();
            String query = "SELECT * FROM stocks";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setId(rs.getInt("id"));
                    stock.setSymbol(rs.getString("symbol"));
                    stock.setName(rs.getString("name"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setPurchasePrice(rs.getBigDecimal("purchase_price"));
                    stock.setPurchaseDate(rs.getDate("purchase_date"));
                    stocks.add(stock);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return stocks;
        }
    }

    public static class DatabaseConnector {
        private static final String URL = "jdbc:sqlserver://VIVEK\\\\SQLEXPRESS:1433;databaseName=Stock;encrypt=true;trustServerCertificate=true";
        private static final String USERNAME = "sa";
        private static final String PASSWORD = "King@3010";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }

        public static void closeConnection(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Stock {
        private int id;
        private String symbol;
        private String name;
        private int quantity;
        private BigDecimal purchasePrice;
        private Date purchaseDate;

        public Stock() {
        }

        public Stock(String symbol, String name, int quantity, BigDecimal purchasePrice, Date purchaseDate) {
            this.symbol = symbol;
            this.name = name;
            this.quantity = quantity;
            this.purchasePrice = purchasePrice;
            this.purchaseDate = purchaseDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public Date getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(Date purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        @Override
        public String toString() {
            return "Stock{" +
                    "id=" + id +
                    ", symbol='" + symbol + '\'' +
                    ", name='" + name + '\'' +
                    ", quantity=" + quantity +
                    ", purchasePrice=" + purchasePrice +
                    ", purchaseDate=" + purchaseDate +
                    '}';
        }
    }
}
