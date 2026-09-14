import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;
    private JTextArea bookingDetails;

    public CancellationFrame() {

        setTitle("Online Reservation System - Cancel Ticket");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Enter PNR:"));

        pnrField = new JTextField(15);
        topPanel.add(pnrField);

        JButton fetchButton = new JButton("Fetch");
        topPanel.add(fetchButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // Booking details area
        bookingDetails = new JTextArea();
        bookingDetails.setEditable(false);
        bookingDetails.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(
                new JScrollPane(bookingDetails),
                BorderLayout.CENTER
        );

        // Cancel button
        JButton cancelButton =
                new JButton("Confirm Cancellation");

        panel.add(cancelButton, BorderLayout.SOUTH);

        add(panel);

        // Fetch booking
        fetchButton.addActionListener(e -> fetchBooking());

        // Cancel booking
        cancelButton.addActionListener(e -> cancelBooking());
    }

    private void fetchBooking() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR number."
            );

            return;
        }

        String sql =
                "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, pnr);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                bookingDetails.setText(
                        "PNR: " +
                        rs.getString("pnr") + "\n\n" +

                        "Passenger Name: " +
                        rs.getString("passenger_name") + "\n\n" +

                        "Train Number: " +
                        rs.getInt("train_number") + "\n\n" +

                        "Train Name: " +
                        rs.getString("train_name") + "\n\n" +

                        "Class: " +
                        rs.getString("class_type") + "\n\n" +

                        "Journey Date: " +
                        rs.getDate("journey_date") + "\n\n" +

                        "Source: " +
                        rs.getString("source_station") + "\n\n" +

                        "Destination: " +
                        rs.getString("destination_station")
                );

            } else {

                bookingDetails.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "No booking found for this PNR."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage()
            );
        }
    }

    private void cancelBooking() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR number."
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this booking?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM reservations WHERE pnr = ?";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, pnr);

            int rows = pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking cancelled successfully!"
                );

                pnrField.setText("");
                bookingDetails.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No booking found for this PNR."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cancellation failed: " +
                    e.getMessage()
            );
        }
    }
}