//package studywork;
//import javax.swing.*;
//
//import studywork.JavaStocks.DatabaseConnector;
//
//import java.awt.*;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class HistoryPage extends JFrame {
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private JTextArea historyTextArea;
//
//    public HistoryPage() {
//        setTitle("Stock Purchase History");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel panel = new JPanel(new BorderLayout());
//
//        historyTextArea = new JTextArea();
//        JScrollPane scrollPane = new JScrollPane(historyTextArea);
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        add(panel);
//        setVisible(true);
//
//        fetchAndDisplayHistory();
//    }
//
//    private void fetchAndDisplayHistory() {
//        try (Connection connection = DatabaseConnector.getConnection()) {
//            String query = "SELECT * FROM stocks";
//            try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                ResultSet rs = stmt.executeQuery();
//                StringBuilder historyBuilder = new StringBuilder();
//                while (rs.next()) {
//                    historyBuilder.append("Symbol: ").append(rs.getString("symbol")).append("\n");
//                    historyBuilder.append("Name: ").append(rs.getString("name")).append("\n");
//                    historyBuilder.append("Quantity: ").append(rs.getInt("quantity")).append("\n");
//                    historyBuilder.append("Price: $").append(rs.getBigDecimal("purchase_price")).append("\n");
//                    historyBuilder.append("Date: ").append(rs.getDate("purchase_date")).append("\n");
//                    historyBuilder.append("-----------------------------\n");
//                }
//                historyTextArea.setText(historyBuilder.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to fetch history from the database.");
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new HistoryPage());
//    }
//}
