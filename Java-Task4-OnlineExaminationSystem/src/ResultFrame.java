import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ResultFrame extends JFrame {

    private String displayName;
    private int score;
    private int totalQuestions;
    private int minutes;
    private int seconds;

    private int[] selectedAnswers;
    private ArrayList<Question> questions;

    public ResultFrame(
            String displayName,
            int score,
            int totalQuestions,
            int minutes,
            int seconds,
            int[] selectedAnswers,
            ArrayList<Question> questions) {

        this.displayName = displayName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.minutes = minutes;
        this.seconds = seconds;
        this.selectedAnswers = selectedAnswers;
        this.questions = questions;

        setTitle("Online Examination System - Result");
        setSize(1100, 750);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                DO_NOTHING_ON_CLOSE
        );

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e) {

                        confirmLogout();
                    }
                }
        );

        createResultInterface();
    }

    // =========================================================
    // CREATE RESULT INTERFACE
    // =========================================================

    private void createResultInterface() {

        JPanel background =
                Theme.createBackgroundPanel();

        background.setLayout(
                new GridBagLayout()
        );

        // =====================================================
        // MAIN CARD
        // =====================================================

        JPanel card =
                Theme.createCardPanel();

        card.setPreferredSize(
                new Dimension(
                        650,
                        570
                )
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        35,
                        55,
                        35,
                        55
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                Theme.createLabel(
                        "EXAM RESULT"
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

        JLabel student =
                Theme.createLabel(
                        "Student: " + displayName
                );

        student.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        student.setForeground(
                Theme.SECONDARY_TEXT
        );

        student.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(8)
        );

        header.add(student);

        header.add(
                Box.createVerticalStrut(25)
        );

        card.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CALCULATE RESULTS
        // =====================================================

        int correct = score;

        int incorrect = 0;
        int unanswered = 0;

        for (int i = 0; i < totalQuestions; i++) {

            if (
                    selectedAnswers[i] == -1
            ) {

                unanswered++;

            } else if (
                    selectedAnswers[i]
                            !=
                    questions
                            .get(i)
                            .getCorrectAnswer()
            ) {

                incorrect++;
            }
        }

        // Percentage
        double percentage =
                ((double) score
                        / totalQuestions)
                        * 100;

        // Grade
        String grade;

        if (percentage >= 90) {

            grade = "A+";

        } else if (percentage >= 80) {

            grade = "A";

        } else if (percentage >= 70) {

            grade = "B";

        } else if (percentage >= 60) {

            grade = "C";

        } else if (percentage >= 50) {

            grade = "D";

        } else {

            grade = "F";
        }

        // =====================================================
        // RESULT DETAILS
        // =====================================================

        JPanel resultPanel =
                new JPanel();

        resultPanel.setOpaque(false);

        resultPanel.setLayout(
                new GridLayout(
                        7,
                        1,
                        5,
                        5
                )
        );

        // Score
        addResultRow(
                resultPanel,
                "Score:",
                score + " / "
                        + totalQuestions
        );

        // Percentage
        addResultRow(
                resultPanel,
                "Percentage:",
                String.format(
                        "%.0f%%",
                        percentage
                )
        );

        // Grade
        addResultRow(
                resultPanel,
                "Grade:",
                grade
        );

        // Correct
        addResultRow(
                resultPanel,
                "Correct:",
                String.valueOf(correct)
        );

        // Incorrect
        addResultRow(
                resultPanel,
                "Incorrect:",
                String.valueOf(incorrect)
        );

        // Unanswered
        addResultRow(
                resultPanel,
                "Unanswered:",
                String.valueOf(unanswered)
        );

        // Time
        addResultRow(
                resultPanel,
                "Time:",
                String.format(
                        "%02d:%02d",
                        minutes,
                        seconds
                )
        );

        card.add(
                resultPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // PERFORMANCE MESSAGE
        // =====================================================

        String performanceMessage;

        if (percentage >= 80) {

            performanceMessage =
                    "EXCELLENT PERFORMANCE!";

        } else if (percentage >= 50) {

            performanceMessage =
                    "GOOD PERFORMANCE!";

        } else {

            performanceMessage =
                    "KEEP PRACTICING!";
        }

        JLabel performanceLabel =
                Theme.createLabel(
                        performanceMessage
                );

        performanceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        performanceLabel.setForeground(
                Theme.SUCCESS
        );

        performanceLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        performanceLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        0,
                        15,
                        0
                )
        );

        // =====================================================
        // BOTTOM PANEL
        // =====================================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        performanceLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        bottomPanel.add(
                performanceLabel
        );

        JButton logoutButton =
                Theme.createButton(
                        "LOGOUT"
                );

        logoutButton.setPreferredSize(
                new Dimension(
                        180,
                        48
                )
        );

        logoutButton.setMaximumSize(
                new Dimension(
                        180,
                        48
                )
        );

        logoutButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoutButton.addActionListener(
                e -> confirmLogout()
        );

        bottomPanel.add(
                logoutButton
        );

        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // ADD CARD
        // =====================================================

        background.add(card);

        setContentPane(background);
    }

    // =========================================================
    // ADD RESULT ROW
    // =========================================================

    private void addResultRow(
            JPanel panel,
            String labelText,
            String valueText) {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        row.setOpaque(false);

        row.setBorder(
                BorderFactory.createEmptyBorder(
                        3,
                        15,
                        3,
                        15
                )
        );

        JLabel label =
                Theme.createLabel(
                        labelText
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        label.setForeground(
                Theme.TEXT
        );

        JLabel value =
                Theme.createLabel(
                        valueText
                );

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        value.setForeground(
                Theme.PRIMARY
        );

        value.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        row.add(
                label,
                BorderLayout.WEST
        );

        row.add(
                value,
                BorderLayout.EAST
        );

        panel.add(row);
    }

    // =========================================================
    // LOGOUT CONFIRMATION
    // =========================================================

    private void confirmLogout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
                choice ==
                        JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginFrame()
                    .setVisible(true);
        }
    }
}