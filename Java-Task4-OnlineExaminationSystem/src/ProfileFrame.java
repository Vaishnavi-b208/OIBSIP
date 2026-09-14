import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {

    private JTextField displayNameField;
    private JPasswordField passwordField;

    private String username;

    public ProfileFrame(String username) {

        this.username = username;

        setTitle(
                "Profile Update - Online Examination System"
        );

        setSize(1100, 750);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(true);


        // =====================================
        // BACKGROUND
        // =====================================

        JPanel background =
                Theme.createBackgroundPanel();

        background.setLayout(
                new GridBagLayout()
        );


        // =====================================
        // CARD
        // =====================================

        JPanel card =
                Theme.createCardPanel();

        card.setPreferredSize(
                new Dimension(600, 610)
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================
        // ICON
        // =====================================

        JLabel icon =
                new JLabel("👤");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        48
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(icon);


        // =====================================
        // TITLE
        // =====================================

        JLabel title =
                new JLabel(
                        "PROFILE UPDATE"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                Theme.PRIMARY
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(title);


        // =====================================
        // SUBTITLE
        // =====================================

        JLabel subtitle =
                new JLabel(
                        "Update your details before starting the exam"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        subtitle.setForeground(
                Theme.SECONDARY_TEXT
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(subtitle);


        // =====================================
        // USERNAME
        // =====================================

        card.add(
                Box.createVerticalStrut(28)
        );

        JLabel usernameLabel =
                Theme.createLabel("Username");

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(usernameLabel);

        card.add(
                Box.createVerticalStrut(8)
        );


        JTextField usernameField =
                Theme.createTextField();

        usernameField.setText(username);

        usernameField.setEditable(false);

        usernameField.setMaximumSize(
                new Dimension(430, 48)
        );

        usernameField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(usernameField);


        // =====================================
        // DISPLAY NAME
        // =====================================

        card.add(
                Box.createVerticalStrut(20)
        );

        JLabel nameLabel =
                Theme.createLabel("Display Name");

        nameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(nameLabel);

        card.add(
                Box.createVerticalStrut(8)
        );


        displayNameField =
                Theme.createTextField();

        displayNameField.setText(username);

        displayNameField.setMaximumSize(
                new Dimension(430, 48)
        );

        displayNameField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(displayNameField);


        // =====================================
        // PASSWORD
        // =====================================

        card.add(
                Box.createVerticalStrut(20)
        );

        JLabel passwordLabel =
                Theme.createLabel("New Password");

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(8)
        );


        passwordField =
                Theme.createPasswordField();

        passwordField.setMaximumSize(
                new Dimension(430, 48)
        );

        passwordField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(passwordField);


        // =====================================
        // SAVE BUTTON
        // =====================================

        card.add(
                Box.createVerticalStrut(28)
        );

        JButton startButton =
                Theme.createButton(
                        "SAVE & START EXAM"
                );

        startButton.setMaximumSize(
                new Dimension(430, 50)
        );

        startButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        startButton.addActionListener(
                e -> saveProfile()
        );

        card.add(startButton);


        // =====================================
        // LOGOUT
        // =====================================

        card.add(
                Box.createVerticalStrut(12)
        );

        JButton logoutButton =
                new JButton("LOGOUT");

        logoutButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        logoutButton.setForeground(
                Theme.DANGER
        );

        logoutButton.setBackground(
                Color.WHITE
        );

        logoutButton.setFocusPainted(false);

        logoutButton.setBorderPainted(false);

        logoutButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        logoutButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        card.add(logoutButton);


        // =====================================
        // ADD CARD
        // =====================================

        background.add(card);

        add(background);
    }


    // =========================================
    // SAVE PROFILE
    // =========================================

    private void saveProfile() {

        String displayName =
                displayNameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );


        // Check display name

        if (displayName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your display name.",
                    "Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Check password

        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a new password.",
                    "Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Password length

        if (password.length() < 4) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 4 characters.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Profile updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        // Open instructions

        new InstructionFrame(
                displayName
        ).setVisible(true);

        dispose();
    }


    // =========================================
    // LOGOUT
    // =========================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            new LoginFrame().setVisible(true);

            dispose();
        }
    }
}