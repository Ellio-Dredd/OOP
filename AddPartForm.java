package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPartForm {

    private JPanel Main;
    private JTextField partIDtxt;
    private JTextField partNametxt;
    private JTextField destxt;
    private JTextField supptxt;
    private JTextField stocktxt;
    private JButton addpartbtn;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Part");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new AddPartForm().Main);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public AddPartForm() {
        addpartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement add part functionality
                String partId = partIDtxt.getText(); // Get part ID from text field
                String name = partNametxt.getText(); // Get name from text field
                String description = destxt.getText(); // Get description from text field
                String supplierId = supptxt.getText(); // Get supplier ID from text field
                int stockQuantity = Integer.parseInt(stocktxt.getText()); // Get stock quantity from text field

                // Example of inserting into the database
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO parts (part_id, name, description, supplier_id, stock_quantity) VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, partId);
                    statement.setString(2, name);
                    statement.setString(3, description);
                    statement.setString(4, supplierId);
                    statement.setInt(5, stockQuantity);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Part added successfully!");
                        clearFields();
                    }
                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding part: " + ex.getMessage());
                }
            }
        });
    }

    // Method to clear text fields
    private void clearFields() {
        partIDtxt.setText("");
        partNametxt.setText("");
        destxt.setText("");
        supptxt.setText("");
        stocktxt.setText("");
    }
}
