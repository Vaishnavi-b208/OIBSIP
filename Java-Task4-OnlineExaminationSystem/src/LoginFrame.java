import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle(
                "Online Examination System"
        );

        setSize(
                1100,
                750
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(true);


        // =================================
        // BACKGROUND
        // =================================

        JPanel background =
                Theme.createBackgroundPanel();

        background.setLayout(
                new GridBagLayout()
        );


        // =================================
        // LOGIN CARD
        // =================================

        JPanel card =
                Theme.createCardPanel();

        card.setPreferredSize(
                new Dimension(
                        600,
                        620
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        // =================================
        // GRADUATION CAP ICON
        // =================================

        JLabel icon =
                new JLabel("🎓");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        55
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(icon);


        // =================================
        // TITLE
        // =================================

        JLabel title =
                new JLabel(
                        "<html>"
                        + "<div style='text-align:center;'>"
                        + "<b>ONLINE EXAMINATION</b><br>"
                        + "<b>SYSTEM</b>"
                        + "</div>"
                        + "</html>"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                Theme.PRIMARY
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(title);


        // =================================
        // SUBTITLE
        // =================================

        JLabel subtitle =
                new JLabel(
                        "Student Login"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        19
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


        // =================================
        // USERNAME LABEL
        // =================================

        card.add(
                Box.createVerticalStrut(32)
        );

        JLabel usernameLabel =
                Theme.createLabel(
                        "Username"
                );

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(usernameLabel);


        // =================================
        // USERNAME FIELD
        // =================================

        card.add(
                Box.createVerticalStrut(8)
        );

        JPanel usernamePanel =
                new JPanel(
                        new BorderLayout()
                );

        usernamePanel.setOpaque(
                false
        );

        usernamePanel.setMaximumSize(
                new Dimension(
                        430,
                        50
                )
        );

        JLabel userIcon =
                new JLabel("👤");

        userIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        21
                )
        );

        userIcon.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        12,
                        0,
                        8
                )
        );

        usernameField =
                Theme.createTextField();

        usernamePanel.add(
                userIcon,
                BorderLayout.WEST
        );

        usernamePanel.add(
                usernameField,
                BorderLayout.CENTER
        );

        card.add(usernamePanel);


        // =================================
        // PASSWORD LABEL
        // =================================

        card.add(
                Box.createVerticalStrut(22)
        );

        JLabel passwordLabel =
                Theme.createLabel(
                        "Password"
                );

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(passwordLabel);


        // =================================
        // PASSWORD FIELD
        // =================================

        card.add(
                Box.createVerticalStrut(8)
        );

        JPanel passwordPanel =
                new JPanel(
                        new BorderLayout()
                );

        passwordPanel.setOpaque(
                false
        );

        passwordPanel.setMaximumSize(
                new Dimension(
                        430,
                        50
                )
        );

        JLabel lockIcon =
                new JLabel("🔒");

        lockIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        20
                )
        );

        lockIcon.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        12,
                        0,
                        8
                )
        );

        passwordField =
                Theme.createPasswordField();

        passwordPanel.add(
                lockIcon,
                BorderLayout.WEST
        );

        passwordPanel.add(
                passwordField,
                BorderLayout.CENTER
        );

        card.add(passwordPanel);


        // =================================
        // LOGIN BUTTON
        // =================================

        card.add(
                Box.createVerticalStrut(30)
        );

        JButton loginButton =
                Theme.createButton(
                        "LOGIN"
                );

        loginButton.setMaximumSize(
                new Dimension(
                        430,
                        52
                )
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(
                e -> login()
        );

        card.add(loginButton);


        // =================================
        // DEMO LOGIN
        // =================================

        card.add(
                Box.createVerticalStrut(22)
        );

        JLabel demoLabel =
                new JLabel(
                        "Demo Login: student / 1234"
                );

        demoLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        demoLabel.setForeground(
                Theme.SECONDARY_TEXT
        );

        demoLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(demoLabel);


        // =================================
        // ADD CARD
        // =================================

        background.add(card);

        add(background);


        // =================================
        // ENTER KEY LOGIN
        // =================================

        getRootPane().setDefaultButton(
                loginButton
        );
    }


    // =====================================
    // LOGIN
    // =====================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );


        // Empty fields

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Demo login

        if (username.equals("student")
                && password.equals("1234")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new ProfileFrame(
                    username
            ).setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}