import javax.swing.*;
import java.awt.*;

public class InstructionFrame extends JFrame {

    private String displayName;

    public InstructionFrame(String displayName) {

        this.displayName = displayName;

        setTitle(
                "Exam Instructions - Online Examination System"
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
        // MAIN CARD
        // =====================================

        JPanel card =
                Theme.createCardPanel();

        card.setPreferredSize(
                new Dimension(700, 620)
        );

        card.setLayout(
                new BorderLayout(
                        20,
                        15
                )
        );


        // =====================================
        // HEADER
        // =====================================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );


        // Icon

        JLabel icon =
                new JLabel("📋");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        45
                )
        );

        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        header.add(icon);


        // Title

        JLabel title =
                new JLabel(
                        "EXAM INSTRUCTIONS"
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

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(title);


        // Welcome

        JLabel welcome =
                new JLabel(
                        "Welcome, " + displayName + "!"
                );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        welcome.setForeground(
                Theme.TEXT
        );

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(welcome);


        card.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================
        // CENTER CONTENT
        // =====================================

        JPanel content =
        new JPanel();

content.setOpaque(false);

content.setLayout(
        new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        )
);


        // =====================================
        // EXAM DETAILS
        // =====================================

        JPanel details =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        details.setOpaque(false);

        details.setMaximumSize(
                new Dimension(
                        600,
                        75
                )
        );


        details.add(
                createDetailCard(
                        "10",
                        "Questions"
                )
        );

        details.add(
                createDetailCard(
                        "10 min",
                        "Duration"
                )
        );

        details.add(
                createDetailCard(
                        "1 mark",
                        "Per Question"
                )
        );


        content.add(details);

        content.add(
                Box.createVerticalStrut(20)
        );


        // =====================================
        // INSTRUCTIONS TITLE
        // =====================================

        JLabel instructionsTitle =
                new JLabel(
                        "Please read the following instructions carefully:"
                );

        instructionsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        instructionsTitle.setForeground(
                Theme.TEXT
        );

        instructionsTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(instructionsTitle);

        content.add(
                Box.createVerticalStrut(10)
        );


        // =====================================
        // INSTRUCTIONS
        // =====================================

        JPanel instructionPanel =
                new JPanel();

        instructionPanel.setBackground(
                new Color(
                        248,
                        249,
                        255
                )
        );

        instructionPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        228,
                                        245
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        instructionPanel.setLayout(
                new BoxLayout(
                        instructionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        instructionPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        String[] instructions = {

                "1. Read each question carefully.",

                "2. Each question has four options.",

                "3. Select only one answer for each question.",

                "4. Use Next to move to the next question.",

                "5. Use Previous to return to an earlier question.",

                "6. The countdown timer will remain visible during the exam.",

                "7. The exam will be automatically submitted when the timer reaches zero.",

                "8. You can manually submit the exam using the Submit button.",

                "9. Closing the exam window will ask for confirmation before quitting.",

                "10. Your final score will be displayed after submission."
        };


        for (String text : instructions) {

            JLabel label =
                    new JLabel(
                            "<html>"
                            + text
                            + "</html>"
                    );

            label.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            14
                    )
            );

            label.setForeground(
                    Theme.TEXT
            );

            label.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            instructionPanel.add(label);

            instructionPanel.add(
                    Box.createVerticalStrut(8)
            );
        }


        JScrollPane scrollPane =
                new JScrollPane(
                        instructionPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setOpaque(false);

        scrollPane.getViewport()
                .setOpaque(false);


        content.add(scrollPane);


        card.add(
                content,
                BorderLayout.CENTER
        );


        // =====================================
        // BOTTOM BUTTONS
        // =====================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );


        // Logout button

        JButton logoutButton =
                new JButton(
                        "LOGOUT"
                );

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

        logoutButton.addActionListener(
                e -> logout()
        );


        // Start button

        JButton startButton =
                Theme.createButton(
                        "START EXAM →"
                );

        startButton.setPreferredSize(
                new Dimension(
                        220,
                        48
                )
        );

        startButton.addActionListener(
                e -> startExam()
        );


        bottomPanel.add(
                logoutButton
        );

        bottomPanel.add(
                startButton
        );


        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================
        // ADD CARD
        // =====================================

        background.add(card);

        add(background);
    }


    // =========================================
    // DETAIL CARD
    // =========================================

    private JPanel createDetailCard(
            String value,
            String labelText) {

        JPanel panel =
                new JPanel();

        panel.setBackground(
                new Color(
                        245,
                        247,
                        255
                )
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        228,
                                        245
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        valueLabel.setForeground(
                Theme.PRIMARY
        );

        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        label.setForeground(
                Theme.SECONDARY_TEXT
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        panel.add(valueLabel);

        panel.add(
                Box.createVerticalStrut(3)
        );

        panel.add(label);


        return panel;
    }


    // =========================================
    // START EXAM
    // =========================================

    private void startExam() {

        new ExamFrame(
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

            new LoginFrame()
                    .setVisible(true);

            dispose();
        }
    }
}