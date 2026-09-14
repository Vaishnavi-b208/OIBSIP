import javax.swing.*;
import java.awt.*;

public class Theme {

    // ==============================
    // COLORS
    // ==============================

    public static final Color BACKGROUND =
            new Color(244, 247, 255);

    public static final Color PRIMARY =
            new Color(76, 63, 200);

    public static final Color PRIMARY_DARK =
            new Color(58, 48, 160);

    public static final Color WHITE =
            Color.WHITE;

    public static final Color TEXT =
            new Color(45, 45, 65);

    public static final Color SECONDARY_TEXT =
            new Color(105, 110, 135);

    public static final Color SUCCESS =
            new Color(40, 167, 69);

    public static final Color DANGER =
            new Color(220, 53, 69);


    // ==============================
    // FONTS
    // ==============================

    public static final Font TITLE_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    30
            );

    public static final Font SUBTITLE_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    17
            );

    public static final Font LABEL_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
            );

    public static final Font NORMAL_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14
            );


    // ==============================
    // MAIN BACKGROUND
    // ==============================

    public static JPanel createBackgroundPanel() {

        JPanel panel = new BackgroundPanel();

        panel.setLayout(
                new GridBagLayout()
        );

        return panel;
    }


    // ==============================
    // WHITE CARD
    // ==============================

    public static JPanel createCardPanel() {

        RoundedPanel panel =
                new RoundedPanel(
                        25,
                        Color.WHITE
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        35,
                        45,
                        35,
                        45
                )
        );

        return panel;
    }


    // ==============================
    // BUTTON
    // ==============================

    public static JButton createButton(
            String text) {

        RoundedButton button =
                new RoundedButton(
                        text,
                        PRIMARY
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // ==============================
    // LABEL
    // ==============================

    public static JLabel createLabel(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                LABEL_FONT
        );

        label.setForeground(
                TEXT
        );

        return label;
    }


    // ==============================
    // TEXT FIELD
    // ==============================

    public static JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                NORMAL_FONT
        );

        field.setPreferredSize(
                new Dimension(
                        350,
                        48
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        205,
                                        212,
                                        235
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                15,
                                5,
                                15
                        )
                )
        );

        return field;
    }


    // ==============================
    // PASSWORD FIELD
    // ==============================

    public static JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setFont(
                NORMAL_FONT
        );

        field.setPreferredSize(
                new Dimension(
                        350,
                        48
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        205,
                                        212,
                                        235
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                15,
                                5,
                                15
                        )
                )
        );

        return field;
    }


    // ==============================
    // ROUNDED PANEL
    // ==============================

    static class RoundedPanel
            extends JPanel {

        private int radius;
        private Color color;

        public RoundedPanel(
                int radius,
                Color color) {

            this.radius = radius;
            this.color = color;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }


    // ==============================
    // ROUNDED BUTTON
    // ==============================

    static class RoundedButton
            extends JButton {

        private int radius = 15;
        private Color color;

        public RoundedButton(
                String text,
                Color color) {

            super(text);

            this.color = color;

            setOpaque(false);

            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (getModel().isPressed()) {

                g2.setColor(
                        PRIMARY_DARK
                );

            } else if (getModel().isRollover()) {

                g2.setColor(
                        new Color(
                                91,
                                76,
                                220
                        )
                );

            } else {

                g2.setColor(color);
            }

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }


    // ==============================
    // MODERN BACKGROUND
    // ==============================

    static class BackgroundPanel
            extends JPanel {

        public BackgroundPanel() {

            setOpaque(true);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Main background

            g2.setColor(
                    BACKGROUND
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );


            // Large light circle - top left

            g2.setColor(
                    new Color(
                            225,
                            231,
                            255
                    )
            );

            g2.fillOval(
                    -180,
                    -230,
                    480,
                    480
            );


            // Large light circle - bottom right

            g2.setColor(
                    new Color(
                            230,
                            234,
                            255
                    )
            );

            g2.fillOval(
                    getWidth() - 280,
                    getHeight() - 260,
                    500,
                    500
            );

            g2.dispose();
        }
    }
}