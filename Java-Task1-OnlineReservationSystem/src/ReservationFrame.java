import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class ReservationFrame extends JFrame {

    private JTextField passengerNameField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JComboBox<String> classTypeBox;
    private JTextField journeyDateField;
    private JTextField sourceField;
    private JTextField destinationField;

    public ReservationFrame() {

        setTitle("Online Reservation System - Book Ticket");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        panel.add(new JLabel("Passenger Name:"));
        passengerNameField = new JTextField();
        panel.add(passengerNameField);

        panel.add(new JLabel("Train Number:"));
        trainNumberField = new JTextField();
        panel.add(trainNumberField);

        panel.add(new JLabel("Train Name:"));
        trainNameField = new JTextField();
        trainNameField.setEditable(false);
        panel.add(trainNameField);

        panel.add(new JLabel("Class Type:"));

        String[] classes = {
                "Sleeper",
                "AC",
                "First Class"
        };

        classTypeBox = new JComboBox<>(classes);
        panel.add(classTypeBox);

        panel.add(new JLabel("Journey Date (YYYY-MM-DD):"));
        journeyDateField = new JTextField();
        panel.add(journeyDateField);

        panel.add(new JLabel("Source Station:"));
        sourceField = new JTextField();
        sourceField.setEditable(false);
        panel.add(sourceField);

        panel.add(new JLabel("Destination Station:"));
        destinationField = new JTextField();
        destinationField.setEditable(false);
        panel.add(destinationField);

        JButton bookButton = new JButton("Book Ticket");
        JButton clearButton = new JButton("Clear");
        JButton cancelButton = new JButton("Cancel Ticket");

        panel.add(bookButton);
        panel.add(clearButton);

        // Add cancel button
        panel.add(cancelButton);
        panel.add(new JLabel(""));

        add(panel);

        clearButton.addActionListener(e -> clearForm());

        bookButton.addActionListener(e -> bookTicket());

        trainNumberField.addActionListener(e -> findTrain());

        cancelButton.addActionListener(e -> {
            CancellationFrame cancellationFrame =
                    new CancellationFrame();

            cancellationFrame.setVisible(true);
        });
    }

    private void findTrain() {

        String trainNumberText =
                trainNumberField.getText().trim();

        if (trainNumberText.isEmpty()) {
            return;
        }

        try {

            int trainNumber =
                    Integer.parseInt(trainNumberText);

            String sql =
                    "SELECT train_name, source, destination " +
                    "FROM trains WHERE train_number = ?";

            try (Connection con =
                         DBConnection.getConnection();
                 PreparedStatement pst =
                         con.prepareStatement(sql)) {

                pst.setInt(1, trainNumber);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    trainNameField.setText(
                            rs.getString("train_name")
                    );

                    sourceField.setText(
                            rs.getString("source")
                    );

                    destinationField.setText(
                            rs.getString("destination")
                    );

                } else {

                    trainNameField.setText("");
                    sourceField.setText("");
                    destinationField.setText("");

                    JOptionPane.showMessageDialog(
                            this,
                            "Train number not found."
                    );
                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train number must be numeric."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage()
            );
        }
    }

    private void bookTicket() {

        String passengerName =
                passengerNameField.getText().trim();

        String trainNumberText =
                trainNumberField.getText().trim();

        String trainName =
                trainNameField.getText().trim();

        String classType =
                (String) classTypeBox.getSelectedItem();

        String journeyDate =
                journeyDateField.getText().trim();

        String source =
                sourceField.getText().trim();

        String destination =
                destinationField.getText().trim();

        if (passengerName.isEmpty()
                || trainNumberText.isEmpty()
                || trainName.isEmpty()
                || journeyDate.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }

        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(trainNumberText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train number must be numeric."
            );

            return;
        }

        try {

            LocalDate.parse(journeyDate);

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date. Use YYYY-MM-DD format."
            );

            return;
        }

        String pnr =
                "PNR" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String sql =
                "INSERT INTO reservations " +
                "(pnr, passenger_name, train_number, " +
                "train_name, class_type, journey_date, " +
                "source_station, destination_station) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, pnr);
            pst.setString(2, passengerName);
            pst.setInt(3, trainNumber);
            pst.setString(4, trainName);
            pst.setString(5, classType);
            pst.setDate(
                    6,
                    Date.valueOf(journeyDate)
            );
            pst.setString(7, source);
            pst.setString(8, destination);

            int rows = pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking successful!\n\n" +
                        "PNR: " + pnr + "\n" +
                        "Passenger: " + passengerName + "\n" +
                        "Train: " + trainName + "\n" +
                        "Class: " + classType + "\n" +
                        "Journey Date: " + journeyDate + "\n" +
                        "From: " + source + "\n" +
                        "To: " + destination,
                        "Booking Confirmation",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearForm();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed:\n" +
                    ex.getMessage()
            );
        }
    }

    private void clearForm() {

        passengerNameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");

        classTypeBox.setSelectedIndex(0);
    }
}