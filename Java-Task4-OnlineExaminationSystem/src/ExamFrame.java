import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ExamFrame extends JFrame {

    private String displayName;

    private ArrayList<Question> questions;
    private int currentQuestion = 0;

    private int[] selectedAnswers;

    // Stores whether each question is marked for review
    private boolean[] markedForReview;

    private JLabel questionNumberLabel;
    private JLabel questionLabel;
    private JLabel timerLabel;
    private JLabel progressLabel;

    private JLabel answeredLabel;
    private JLabel unansweredLabel;
    private JLabel reviewLabel;

    private JProgressBar progressBar;

    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;

    private JButton reviewButton;

    private Timer timer;

    // 10 minutes
    private int remainingSeconds = 600;

    private long examStartTime;

    public ExamFrame(String displayName) {

        this.displayName = displayName;

        questions = QuestionBank.getQuestions();

        selectedAnswers = new int[questions.size()];

        markedForReview = new boolean[questions.size()];

        // -1 means unanswered
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
            markedForReview[i] = false;
        }

        examStartTime = System.currentTimeMillis();

        setTitle("Online Examination System - Exam");
        setSize(1100, 750);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                DO_NOTHING_ON_CLOSE
        );

        // ==========================================
        // WINDOW CLOSE CONFIRMATION
        // ==========================================

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e) {

                        int choice =
                                JOptionPane.showConfirmDialog(
                                        ExamFrame.this,
                                        "Are you sure you want to quit the exam?",
                                        "Confirm Exit",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE
                                );

                        if (choice ==
                                JOptionPane.YES_OPTION) {

                            if (timer != null) {
                                timer.stop();
                            }

                            dispose();

                            new LoginFrame()
                                    .setVisible(true);
                        }
                    }
                }
        );

        createExamInterface();

        startTimer();

        loadQuestion();
    }

    // =========================================================
    // CREATE EXAM INTERFACE
    // =========================================================

    private void createExamInterface() {

        JPanel background =
                Theme.createBackgroundPanel();

        background.setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        background.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        30,
                        40
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                Theme.createCardPanel();

        header.setLayout(
                new BorderLayout()
        );

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                Theme.createLabel(
                        "ONLINE EXAMINATION"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                Theme.PRIMARY
        );

        JLabel student =
                Theme.createLabel(
                        "Student: " + displayName
                );

        student.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        student.setForeground(
                Theme.SECONDARY_TEXT
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(student);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =====================================================
        // TIMER
        // =====================================================

        JPanel timerPanel =
                new JPanel();

        timerPanel.setOpaque(false);

        timerPanel.setLayout(
                new BoxLayout(
                        timerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel timeText =
                Theme.createLabel(
                        "TIME REMAINING"
                );

        timeText.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        timeText.setForeground(
                Theme.SECONDARY_TEXT
        );

        timeText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        timerLabel =
                Theme.createLabel(
                        "10:00"
                );

        timerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        timerLabel.setForeground(
                Theme.PRIMARY
        );

        timerLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        timerPanel.add(timeText);

        timerPanel.add(
                Box.createVerticalStrut(3)
        );

        timerPanel.add(timerLabel);

        header.add(
                timerPanel,
                BorderLayout.EAST
        );

        background.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN CARD
        // =====================================================

        JPanel card =
                Theme.createCardPanel();

        card.setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        // =====================================================
        // QUESTION HEADER
        // =====================================================

        JPanel questionHeader =
                new JPanel(
                        new BorderLayout()
                );

        questionHeader.setOpaque(false);

        questionNumberLabel =
                Theme.createLabel(
                        "QUESTION 1"
                );

        questionNumberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        questionNumberLabel.setForeground(
                Theme.PRIMARY
        );

        progressLabel =
                Theme.createLabel(
                        "1 / " + questions.size()
                );

        progressLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        progressLabel.setForeground(
                Theme.SECONDARY_TEXT
        );

        questionHeader.add(
                questionNumberLabel,
                BorderLayout.WEST
        );

        questionHeader.add(
                progressLabel,
                BorderLayout.EAST
        );

        // =====================================================
        // PROGRESS BAR
        // =====================================================

        progressBar =
                new JProgressBar(
                        0,
                        questions.size()
                );

        progressBar.setValue(1);

        progressBar.setPreferredSize(
                new Dimension(
                        100,
                        8
                )
        );

        // =====================================================
        // TOP PANEL
        // =====================================================

        JPanel topPanel =
                new JPanel();

        topPanel.setOpaque(false);

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        topPanel.add(questionHeader);

        topPanel.add(
                Box.createVerticalStrut(15)
        );

        topPanel.add(progressBar);

        topPanel.add(
                Box.createVerticalStrut(12)
        );

        // =====================================================
        // STATUS PANEL
        // =====================================================

        JPanel statusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                20,
                                0
                        )
                );

        statusPanel.setOpaque(false);

        answeredLabel =
                Theme.createLabel(
                        "✓ Answered: 0"
                );

        answeredLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        answeredLabel.setForeground(
                Theme.SUCCESS
        );

        unansweredLabel =
                Theme.createLabel(
                        "○ Unanswered: "
                                + questions.size()
                );

        unansweredLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        unansweredLabel.setForeground(
                Theme.SECONDARY_TEXT
        );

        reviewLabel =
                Theme.createLabel(
                        "⭐ Review: 0"
                );

        reviewLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        reviewLabel.setForeground(
                Theme.PRIMARY
        );

        statusPanel.add(
                answeredLabel
        );

        statusPanel.add(
                unansweredLabel
        );

        statusPanel.add(
                reviewLabel
        );

        topPanel.add(statusPanel);

        card.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // QUESTION PANEL
        // =====================================================

        JPanel questionPanel =
                new JPanel();

        questionPanel.setOpaque(false);

        questionPanel.setLayout(
                new BoxLayout(
                        questionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        questionLabel =
                Theme.createLabel(
                        ""
                );

        questionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        questionLabel.setForeground(
                Theme.TEXT
        );

        questionLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        questionPanel.add(
                questionLabel
        );

        questionPanel.add(
                Box.createVerticalStrut(30)
        );

        // =====================================================
        // OPTIONS
        // =====================================================

        optionButtons =
                new JRadioButton[4];

        optionGroup =
                new ButtonGroup();

        for (int i = 0; i < 4; i++) {

            optionButtons[i] =
                    new JRadioButton();

            optionButtons[i].setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            16
                    )
            );

            optionButtons[i].setForeground(
                    Theme.TEXT
            );

            optionButtons[i].setOpaque(false);

            optionButtons[i].setFocusPainted(
                    false
            );

            optionButtons[i].setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            // When answer is selected
            optionButtons[i].addActionListener(
                    e -> {

                        saveAnswer();

                        updateAnswerStatus();
                    }
            );

            optionGroup.add(
                    optionButtons[i]
            );

            questionPanel.add(
                    optionButtons[i]
            );

            questionPanel.add(
                    Box.createVerticalStrut(18)
            );
        }

        card.add(
                questionPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BUTTONS
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        // Previous
        JButton previousButton =
                Theme.createButton(
                        "← Previous"
                );

        // Mark for Review
        reviewButton =
                Theme.createButton(
                        "⭐ Mark for Review"
                );

        // Next
        JButton nextButton =
                Theme.createButton(
                        "Next →"
                );

        // Submit
        JButton submitButton =
                Theme.createButton(
                        "SUBMIT EXAM"
                );

        previousButton.setPreferredSize(
                new Dimension(
                        130,
                        48
                )
        );

        reviewButton.setPreferredSize(
                new Dimension(
                        180,
                        48
                )
        );

        nextButton.setPreferredSize(
                new Dimension(
                        130,
                        48
                )
        );

        submitButton.setPreferredSize(
                new Dimension(
                        160,
                        48
                )
        );

        // -----------------------------------------------------
        // LEFT BUTTONS
        // -----------------------------------------------------

        JPanel leftButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        leftButtons.setOpaque(false);

        leftButtons.add(
                previousButton
        );

        leftButtons.add(
                reviewButton
        );

        // -----------------------------------------------------
        // RIGHT BUTTONS
        // -----------------------------------------------------

        JPanel rightButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        rightButtons.setOpaque(false);

        rightButtons.add(
                nextButton
        );

        rightButtons.add(
                submitButton
        );

        bottomPanel.add(
                leftButtons,
                BorderLayout.WEST
        );

        bottomPanel.add(
                rightButtons,
                BorderLayout.EAST
        );

        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        background.add(
                card,
                BorderLayout.CENTER
        );

        setContentPane(background);

        // =====================================================
        // PREVIOUS ACTION
        // =====================================================

        previousButton.addActionListener(
                e -> {

                    saveAnswer();

                    if (currentQuestion > 0) {

                        currentQuestion--;

                        loadQuestion();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "This is the first question.",
                                "Information",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        // =====================================================
        // REVIEW ACTION
        // =====================================================

        reviewButton.addActionListener(
                e -> {

                    markedForReview[
                            currentQuestion
                    ] =
                            !markedForReview[
                                    currentQuestion
                            ];

                    updateReviewButton();

                    updateAnswerStatus();
                }
        );

        // =====================================================
        // NEXT ACTION
        // =====================================================

        nextButton.addActionListener(
                e -> {

                    saveAnswer();

                    if (
                            currentQuestion
                                    <
                            questions.size() - 1
                    ) {

                        currentQuestion++;

                        loadQuestion();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "This is the last question.",
                                "Information",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        // =====================================================
        // SUBMIT ACTION
        // =====================================================

        submitButton.addActionListener(
                e -> {

                    saveAnswer();

                    manualSubmit();
                }
        );
    }

    // =========================================================
    // LOAD QUESTION
    // =========================================================

    private void loadQuestion() {

        Question q =
                questions.get(
                        currentQuestion
                );

        questionNumberLabel.setText(
                "QUESTION "
                        + (currentQuestion + 1)
        );

        progressLabel.setText(
                (currentQuestion + 1)
                        + " / "
                        + questions.size()
        );

        progressBar.setValue(
                currentQuestion + 1
        );

        questionLabel.setText(
                "<html><div style='width:850px;'>"
                        + q.getQuestion()
                        + "</div></html>"
        );

        String[] options =
                q.getOptions();

        optionGroup.clearSelection();

        for (int i = 0; i < 4; i++) {

            optionButtons[i].setText(
                    (char) ('A' + i)
                            + ". "
                            + options[i]
            );
        }

        // Restore selected answer
        if (
                selectedAnswers[currentQuestion]
                        != -1
        ) {

            optionButtons[
                    selectedAnswers[currentQuestion]
            ].setSelected(true);
        }

        updateReviewButton();

        updateAnswerStatus();
    }

    // =========================================================
    // SAVE ANSWER
    // =========================================================

    private void saveAnswer() {

        for (
                int i = 0;
                i < optionButtons.length;
                i++
        ) {

            if (
                    optionButtons[i].isSelected()
            ) {

                selectedAnswers[
                        currentQuestion
                ] = i;

                return;
            }
        }

        selectedAnswers[
                currentQuestion
        ] = -1;
    }

    // =========================================================
    // UPDATE ANSWER STATUS
    // =========================================================

    private void updateAnswerStatus() {

        int answered = 0;

        int reviewCount = 0;

        for (int answer :
                selectedAnswers) {

            if (answer != -1) {

                answered++;
            }
        }

        for (boolean marked :
                markedForReview) {

            if (marked) {

                reviewCount++;
            }
        }

        int unanswered =
                questions.size()
                        - answered;

        answeredLabel.setText(
                "✓ Answered: "
                        + answered
        );

        unansweredLabel.setText(
                "○ Unanswered: "
                        + unanswered
        );

        reviewLabel.setText(
                "⭐ Review: "
                        + reviewCount
        );
    }

    // =========================================================
    // UPDATE REVIEW BUTTON
    // =========================================================

    private void updateReviewButton() {

        if (
                markedForReview[
                        currentQuestion
                ]
        ) {

            reviewButton.setText(
                    "⭐ Remove Review"
            );

        } else {

            reviewButton.setText(
                    "⭐ Mark for Review"
            );
        }
    }

    // =========================================================
    // TIMER
    // =========================================================

    private void startTimer() {

        timer =
                new Timer(
                        1000,
                        e -> {

                            remainingSeconds--;

                            updateTimer();

                            if (
                                    remainingSeconds <= 0
                            ) {

                                timer.stop();

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Time is over. Your exam has been submitted automatically.",
                                        "Time Over",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                submitExam();
                            }
                        }
                );

        timer.start();
    }

    // =========================================================
    // UPDATE TIMER
    // =========================================================

    private void updateTimer() {

        int minutes =
                remainingSeconds / 60;

        int seconds =
                remainingSeconds % 60;

        timerLabel.setText(
                String.format(
                        "%02d:%02d",
                        minutes,
                        seconds
                )
        );

        if (
                remainingSeconds <= 60
        ) {

            timerLabel.setForeground(
                    Theme.DANGER
            );

        } else {

            timerLabel.setForeground(
                    Theme.PRIMARY
            );
        }
    }

    // =========================================================
    // MANUAL SUBMIT
    // =========================================================

    private void manualSubmit() {

        int unanswered = 0;

        int reviewCount = 0;

        for (int answer :
                selectedAnswers) {

            if (answer == -1) {

                unanswered++;
            }
        }

        for (boolean marked :
                markedForReview) {

            if (marked) {

                reviewCount++;
            }
        }

        String message =
                "Exam Summary\n\n"
                        + "✓ Answered: "
                        + (questions.size()
                        - unanswered)
                        + "\n"
                        + "○ Unanswered: "
                        + unanswered
                        + "\n"
                        + "⭐ Marked for Review: "
                        + reviewCount
                        + "\n\n"
                        + "Are you sure you want to submit?";

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Confirm Submission",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
                choice ==
                        JOptionPane.YES_OPTION
        ) {

            submitExam();
        }
    }

    // =========================================================
    // SUBMIT EXAM
    // =========================================================

    private void submitExam() {

        if (timer != null) {

            timer.stop();
        }

        saveAnswer();

        int score = 0;

        for (
                int i = 0;
                i < questions.size();
                i++
        ) {

            if (
                    selectedAnswers[i]
                            ==
                    questions
                            .get(i)
                            .getCorrectAnswer()
            ) {

                score++;
            }
        }

        // Calculate time taken
        long elapsed =
                System.currentTimeMillis()
                        - examStartTime;

        long elapsedSeconds =
                elapsed / 1000;

        int minutes =
                (int) elapsedSeconds / 60;

        int seconds =
                (int) elapsedSeconds % 60;

        // Open ResultFrame
        dispose();

        new ResultFrame(
                displayName,
                score,
                questions.size(),
                minutes,
                seconds,
                selectedAnswers,
                questions
        ).setVisible(true);
    }
}