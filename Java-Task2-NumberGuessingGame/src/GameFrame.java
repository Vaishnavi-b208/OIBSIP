import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(10, 18, 35);

    private static final Color CARD =
            new Color(20, 30, 52);

    private static final Color CARD_LIGHT =
            new Color(28, 40, 66);

    private static final Color BLUE =
            new Color(70, 140, 255);

    private static final Color CYAN =
            new Color(70, 210, 220);

    private static final Color GREEN =
            new Color(60, 200, 130);

    private static final Color RED =
            new Color(240, 80, 90);

    private static final Color GOLD =
            new Color(255, 190, 70);

    private static final Color WHITE =
            new Color(245, 248, 255);

    private static final Color TEXT =
            new Color(190, 200, 220);

    // =========================================================
    // FILE
    // =========================================================

    private static final String SCORE_FILE =
            "scores.dat";

    // =========================================================
    // GAME VARIABLES
    // =========================================================

    private final Random random =
            new Random();

    private int secretNumber;

    private int minNumber;

    private int maxNumber;

    private int maxAttempts;

    private int attempts;

    private int score = 0;

    private int bestScore = 0;

    private int round = 1;

    private int roundsPlayed = 0;

    private int roundsWon = 0;

    private int roundsLost = 0;

    private int totalAttempts = 0;

    private int winStreak = 0;

    private int bestStreak = 0;

    private int lastRoundScore = 0;

    private String selectedDifficulty =
            "Medium";

    // =========================================================
    // ACHIEVEMENTS
    // =========================================================

    private final Set<String> achievements =
            new HashSet<>();

    // =========================================================
    // LEADERBOARD
    // =========================================================

    private final List<ScoreEntry> leaderboard =
            new ArrayList<>();

    // =========================================================
    // GUI
    // =========================================================

    private CardLayout cardLayout;

    private JPanel mainPanel;

    private JTextField guessField;

    private JLabel statusLabel;

    private JLabel attemptsLabel;

    private JLabel scoreLabel;

    private JLabel roundLabel;

    private JLabel rangeLabel;

    private JLabel streakLabel;

    private JLabel bestScoreLabel;

    private JLabel timerLabel;

    private JLabel historyLabel;

    private JLabel statisticsLabel;

    // Welcome screen difficulty
    private JComboBox<String> welcomeDifficultyBox;

    // Game screen difficulty
    private JComboBox<String> difficultyBox;

    private JProgressBar progressBar;

    private Timer timer;

    private int seconds = 0;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public GameFrame() {

        setTitle(
                "Number Guessing Game"
        );

        setSize(
                1050,
                700
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // Allow maximize and resizing
        setResizable(true);

        // Open maximized
        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        cardLayout =
                new CardLayout();

        mainPanel =
                new JPanel(cardLayout);

        mainPanel.setBackground(
                BACKGROUND
        );

        createWelcomePanel();

        createGamePanel();

        createResultPanel();

        // Load previously saved scores
        loadLeaderboard();

        updateBestScoreFromLeaderboard();

        add(mainPanel);

        cardLayout.show(
                mainPanel,
                "WELCOME"
        );

        setVisible(true);
    }

    // =========================================================
    // WELCOME SCREEN
    // =========================================================

    private void createWelcomePanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND
        );

        JPanel card =
                createCardPanel(
                        760,
                        560
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "NUMBER GUESSING GAME"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        34
                )
        );

        title.setForeground(
                BLUE
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Test your guessing skills!"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        18
                )
        );

        subtitle.setForeground(
                TEXT
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel information =
        new JLabel(
                "<html>"
                        + "<div style='text-align:center;'>"
                        + "Guess the secret number within the given attempts.<br>"
                        + "Use the hints to find the correct number!"
                        + "</div>"
                        + "</html>"
        );

information.setFont(
        new Font(
                "SansSerif",
                Font.PLAIN,
                16
        )
);

information.setForeground(
        TEXT
);

information.setHorizontalAlignment(
        SwingConstants.CENTER
);

information.setAlignmentX(
        Component.CENTER_ALIGNMENT
);

information.setMaximumSize(
        new Dimension(
                550,
                60
        )
);

        information.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        16
                )
        );

        information.setForeground(
                TEXT
        );

        information.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel difficultyTitle =
                new JLabel(
                        "SELECT DIFFICULTY"
                );

        difficultyTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        difficultyTitle.setForeground(
                WHITE
        );

        difficultyTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // IMPORTANT:
        // Separate combo box for welcome screen
        welcomeDifficultyBox =
                new JComboBox<>(
                        new String[]{
                                "Easy",
                                "Medium",
                                "Hard"
                        }
                );

        welcomeDifficultyBox.setSelectedItem(
                "Medium"
        );

        welcomeDifficultyBox.setMaximumSize(
                new Dimension(
                        330,
                        45
                )
        );

        welcomeDifficultyBox.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        JButton startButton =
                createButton(
                        "START GAME",
                        BLUE
                );

        startButton.setMaximumSize(
                new Dimension(
                        330,
                        52
                )
        );

        startButton.addActionListener(
                e -> {

                    selectedDifficulty =
                            welcomeDifficultyBox
                                    .getSelectedItem()
                                    .toString();

                    if (difficultyBox != null) {

                        difficultyBox.setSelectedItem(
                                selectedDifficulty
                        );
                    }

                    startGame();

                    cardLayout.show(
                            mainPanel,
                            "GAME"
                    );
                }
        );

        // Best score display
        JLabel bestScoreWelcome =
                new JLabel(
                        "Best Score: "
                                + bestScore
                );

        bestScoreWelcome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        bestScoreWelcome.setForeground(
                GOLD
        );

        bestScoreWelcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(12)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(information);

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(difficultyTitle);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(
                welcomeDifficultyBox
        );

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(
                bestScoreWelcome
        );

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(startButton);

        card.add(
                Box.createVerticalGlue()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(
                card,
                gbc
        );

        mainPanel.add(
                panel,
                "WELCOME"
        );
    }

    // =========================================================
    // GAME SCREEN
    // =========================================================

    private void createGamePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        panel.setBackground(
                BACKGROUND
        );

        panel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // =====================================================
        // TOP
        // =====================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setOpaque(false);

        JLabel title =
                new JLabel(
                        "NUMBER GUESSING GAME"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                BLUE
        );

        timerLabel =
                new JLabel(
                        "TIME: 00:00"
                );

        timerLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        timerLabel.setForeground(
                CYAN
        );

        topPanel.add(
                title,
                BorderLayout.WEST
        );

        topPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel center =
                new JPanel(
                        new GridBagLayout()
                );

        center.setOpaque(false);

        JPanel card =
                createCardPanel(
                        600,
                        450
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel instruction =
                new JLabel(
                        "Guess the secret number"
                );

        instruction.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        instruction.setForeground(
                WHITE
        );

        instruction.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rangeLabel =
                new JLabel(
                        "Range: 1 - 100"
                );

        rangeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        rangeLabel.setForeground(
                CYAN
        );

        rangeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =====================================================
        // GUESS FIELD
        // =====================================================

        guessField =
                new JTextField();

        guessField.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        guessField.setHorizontalAlignment(
                JTextField.CENTER
        );

        guessField.setMaximumSize(
                new Dimension(
                        300,
                        50
                )
        );

        guessField.setBackground(
                new Color(
                        12,
                        20,
                        38
                )
        );

        guessField.setForeground(
                WHITE
        );

        guessField.setCaretColor(
                WHITE
        );

        guessField.setBorder(
                BorderFactory.createLineBorder(
                        BLUE,
                        2
                )
        );

        // Press ENTER to guess
        guessField.addActionListener(
                e -> checkGuess()
        );

        // =====================================================
        // STATUS
        // =====================================================

        statusLabel =
                new JLabel(
                        "MAKE YOUR GUESS!"
                );

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        statusLabel.setForeground(
                CYAN
        );

        statusLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =====================================================
        // PROGRESS BAR
        // =====================================================

        progressBar =
                new JProgressBar();

        progressBar.setMaximumSize(
                new Dimension(
                        400,
                        18
                )
        );

        progressBar.setStringPainted(
                true
        );

        progressBar.setForeground(
                BLUE
        );

        progressBar.setBackground(
                new Color(
                        45,
                        55,
                        75
                )
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton guessButton =
                createButton(
                        "GUESS",
                        BLUE
                );

        guessButton.setMaximumSize(
                new Dimension(
                        180,
                        45
                )
        );

        guessButton.addActionListener(
                e -> checkGuess()
        );

        JButton hintButton =
                createButton(
                        "HINT",
                        GOLD
                );

        hintButton.setMaximumSize(
                new Dimension(
                        180,
                        45
                )
        );

        hintButton.addActionListener(
                e -> showHint()
        );

        JButton newGameButton =
                createButton(
                        "NEW GAME",
                        RED
                );

        newGameButton.setMaximumSize(
                new Dimension(
                        180,
                        45
                )
        );

        newGameButton.addActionListener(
                e -> {

                    stopTimer();

                    selectedDifficulty =
                            difficultyBox
                                    .getSelectedItem()
                                    .toString();

                    startGame();

                    cardLayout.show(
                            mainPanel,
                            "GAME"
                    );
                }
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                5
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                guessButton
        );

        buttonPanel.add(
                hintButton
        );

        buttonPanel.add(
                newGameButton
        );

        // =====================================================
        // CARD CONTENT
        // =====================================================

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(
                instruction
        );

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(
                rangeLabel
        );

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(
                guessField
        );

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(
                statusLabel
        );

        card.add(
                Box.createVerticalStrut(15)
        );

        card.add(
                progressBar
        );

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(
                buttonPanel
        );

        card.add(
                Box.createVerticalGlue()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        center.add(
                card,
                gbc
        );

        panel.add(
                center,
                BorderLayout.CENTER
        );

        // =====================================================
        // RIGHT INFORMATION PANEL
        // =====================================================

        JPanel rightPanel =
                createCardPanel(
                        280,
                        450
                );

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel infoTitle =
                new JLabel(
                        "GAME INFO"
                );

        infoTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        infoTitle.setForeground(
                WHITE
        );

        infoTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        attemptsLabel =
                createInfoLabel(
                        "Attempts: 0"
                );

        scoreLabel =
                createInfoLabel(
                        "Score: 0"
                );

        roundLabel =
                createInfoLabel(
                        "Round: 1"
                );

        streakLabel =
                createInfoLabel(
                        "Win Streak: 0"
                );

        bestScoreLabel =
                createInfoLabel(
                        "Best Score: 0"
                );

        JLabel difficultyInfoLabel =
                createInfoLabel(
                        "Difficulty: Medium"
                );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                infoTitle
        );

        rightPanel.add(
                Box.createVerticalStrut(25)
        );

        rightPanel.add(
                attemptsLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                scoreLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                roundLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                streakLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                bestScoreLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        rightPanel.add(
                difficultyInfoLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // HISTORY
        // =====================================================

        JLabel historyTitle =
                createInfoLabel(
                        "ROUND HISTORY"
                );

        historyTitle.setForeground(
                GOLD
        );

        historyLabel =
                new JLabel(
                        "<html>"
                                + "No rounds completed yet."
                                + "</html>"
                );

        historyLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        historyLabel.setForeground(
                TEXT
        );

        historyLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                historyTitle
        );

        rightPanel.add(
                Box.createVerticalStrut(10)
        );

        rightPanel.add(
                historyLabel
        );

        // =====================================================
        // CHANGE DIFFICULTY
        // =====================================================

        rightPanel.add(
                Box.createVerticalStrut(20)
        );

        JLabel changeDifficulty =
                createInfoLabel(
                        "CHANGE DIFFICULTY"
                );

        changeDifficulty.setForeground(
                CYAN
        );

        rightPanel.add(
                changeDifficulty
        );

        rightPanel.add(
                Box.createVerticalStrut(8)
        );

        difficultyBox =
                new JComboBox<>(
                        new String[]{
                                "Easy",
                                "Medium",
                                "Hard"
                        }
                );

        difficultyBox.setSelectedItem(
                selectedDifficulty
        );

        difficultyBox.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        difficultyBox.setMaximumSize(
                new Dimension(
                        200,
                        38
                )
        );

        difficultyBox.addActionListener(
                e -> {

                    if (
                            difficultyBox
                                    .getSelectedItem()
                                    != null
                    ) {

                        selectedDifficulty =
                                difficultyBox
                                        .getSelectedItem()
                                        .toString();

                        applyDifficulty();

                        startNewRound();
                    }
                }
        );

        rightPanel.add(
                difficultyBox
        );

        panel.add(
                rightPanel,
                BorderLayout.EAST
        );

        mainPanel.add(
                panel,
                "GAME"
        );
    }

    // =========================================================
    // RESULT SCREEN
    // =========================================================

    private void createResultPanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBackground(
                BACKGROUND
        );

        JPanel card =
                createCardPanel(
                        650,
                        600
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "ROUND COMPLETE"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                GREEN
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        statisticsLabel =
                new JLabel();

        statisticsLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        16
                )
        );

        statisticsLabel.setForeground(
                TEXT
        );

        statisticsLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =====================================================
        // PLAY AGAIN
        // =====================================================

        JButton playAgain =
                createButton(
                        "PLAY AGAIN",
                        BLUE
                );

        playAgain.setMaximumSize(
                new Dimension(
                        280,
                        50
                )
        );

        playAgain.addActionListener(
                e -> {

                    round++;

                    startNewRound();

                    cardLayout.show(
                            mainPanel,
                            "GAME"
                    );
                }
        );

        // =====================================================
        // ACHIEVEMENTS
        // =====================================================

        JButton achievementsButton =
                createButton(
                        "ACHIEVEMENTS",
                        GOLD
                );

        achievementsButton.setMaximumSize(
                new Dimension(
                        280,
                        50
                )
        );

        achievementsButton.addActionListener(
                e -> showAchievements()
        );

        // =====================================================
        // HIGH SCORES
        // =====================================================

        JButton leaderboardButton =
                createButton(
                        "HIGH SCORES",
                        CYAN
                );

        leaderboardButton.setMaximumSize(
                new Dimension(
                        280,
                        50
                )
        );

        leaderboardButton.addActionListener(
                e -> showLeaderboard()
        );

        // =====================================================
        // MAIN MENU
        // =====================================================

        JButton mainMenu =
                createButton(
                        "MAIN MENU",
                        CARD_LIGHT
                );

        mainMenu.setMaximumSize(
                new Dimension(
                        280,
                        50
                )
        );

        mainMenu.addActionListener(
                e -> {

                    stopTimer();

                    cardLayout.show(
                            mainPanel,
                            "WELCOME"
                    );
                }
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(
                title
        );

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(
                statisticsLabel
        );

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(
                playAgain
        );

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(
                achievementsButton
        );

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(
                leaderboardButton
        );

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(
                mainMenu
        );

        card.add(
                Box.createVerticalGlue()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(
                card,
                gbc
        );

        mainPanel.add(
                panel,
                "RESULT"
        );
    }

    // =========================================================
    // START GAME
    // =========================================================

    private void startGame() {

        stopTimer();

        score = 0;

        round = 1;

        roundsPlayed = 0;

        roundsWon = 0;

        roundsLost = 0;

        totalAttempts = 0;

        winStreak = 0;

        seconds = 0;

        lastRoundScore = 0;

        historyLabel.setText(
                "<html>"
                        + "No rounds completed yet."
                        + "</html>"
        );

        applyDifficulty();

        startNewRound();
    }

    // =========================================================
    // APPLY DIFFICULTY
    // =========================================================

    private void applyDifficulty() {

        if (
                selectedDifficulty.equals(
                        "Easy"
                )
        ) {

            minNumber = 1;

            maxNumber = 50;

            maxAttempts = 10;

        } else if (
                selectedDifficulty.equals(
                        "Hard"
                )
        ) {

            minNumber = 1;

            maxNumber = 200;

            maxAttempts = 5;

        } else {

            minNumber = 1;

            maxNumber = 100;

            maxAttempts = 7;
        }

        if (
                difficultyBox != null
        ) {

            difficultyBox.setSelectedItem(
                    selectedDifficulty
            );
        }

        updateRangeLabel();
    }

    // =========================================================
    // START NEW ROUND
    // =========================================================

    private void startNewRound() {

        attempts = 0;

        secretNumber =
                random.nextInt(
                        maxNumber
                                - minNumber
                                + 1
                )
                        + minNumber;

        seconds = 0;

        if (
                guessField != null
        ) {

            guessField.setText("");

            guessField.setEnabled(
                    true
            );

            guessField.requestFocus();
        }

        if (
                statusLabel != null
        ) {

            statusLabel.setText(
                    "MAKE YOUR GUESS!"
            );

            statusLabel.setForeground(
                    CYAN
            );
        }

        updateRangeLabel();

        updateLabels();

        updateProgress();

        startTimer();
    }

    // =========================================================
    // CHECK GUESS
    // =========================================================

    private void checkGuess() {

        if (
                guessField == null
                        ||
                        !guessField.isEnabled()
        ) {
            return;
        }

        String input =
                guessField
                        .getText()
                        .trim();

        if (
                input.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int guess;

        try {

            guess =
                    Integer.parseInt(
                            input
                    );

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter numbers only.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            guessField.selectAll();

            return;
        }

        if (
                guess < minNumber
                        ||
                        guess > maxNumber
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a number between "
                            + minNumber
                            + " and "
                            + maxNumber
                            + ".",
                    "Outside Range",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        attempts++;

        updateProgress();

        updateLabels();

        // =====================================================
        // CORRECT
        // =====================================================

        if (
                guess == secretNumber
        ) {

            handleCorrectGuess();

        }

        // =====================================================
        // TOO LOW
        // =====================================================

        else if (
                guess < secretNumber
        ) {

            statusLabel.setText(
                    "TOO LOW"
            );

            statusLabel.setForeground(
                    GOLD
            );

            if (
                    attempts >= maxAttempts
            ) {

                handleGameOver();

            } else {

                guessField.selectAll();
            }
        }

        // =====================================================
        // TOO HIGH
        // =====================================================

        else {

            statusLabel.setText(
                    "TOO HIGH"
            );

            statusLabel.setForeground(
                    RED
            );

            if (
                    attempts >= maxAttempts
            ) {

                handleGameOver();

            } else {

                guessField.selectAll();
            }
        }
    }

    // =========================================================
    // CORRECT GUESS
    // =========================================================

    private void handleCorrectGuess() {

        stopTimer();

        guessField.setEnabled(
                false
        );

        roundsPlayed++;

        roundsWon++;

        totalAttempts += attempts;

        winStreak++;

        if (
                winStreak > bestStreak
        ) {

            bestStreak =
                    winStreak;
        }

        int points =
                calculateScore();

        lastRoundScore =
                points;

        score += points;

        if (
                score > bestScore
        ) {

            bestScore =
                    score;
        }

        addLeaderboardEntry();

        checkAchievements();

        updateLabels();

        updateHistory(
                "Round "
                        + round
                        + " - WON in "
                        + attempts
                        + " attempts"
        );

        showResult(
                true
        );
    }

    // =========================================================
    // GAME OVER
    // =========================================================

    private void handleGameOver() {

        stopTimer();

        guessField.setEnabled(
                false
        );

        roundsPlayed++;

        roundsLost++;

        totalAttempts += attempts;

        winStreak = 0;

        updateLabels();

        updateHistory(
                "Round "
                        + round
                        + " - LOST"
        );

        showResult(
                false
        );
    }

    // =========================================================
    // SCORE
    // =========================================================

    private int calculateScore() {

        int baseScore;

        if (
                selectedDifficulty.equals(
                        "Easy"
                )
        ) {

            baseScore = 100;

        } else if (
                selectedDifficulty.equals(
                        "Hard"
                )
        ) {

            baseScore = 300;

        } else {

            baseScore = 200;
        }

        int attemptBonus =
                (
                        maxAttempts
                                - attempts
                                + 1
                )
                        * 20;

        int timeBonus =
                Math.max(
                        0,
                        100 - seconds
                );

        return baseScore
                + attemptBonus
                + timeBonus;
    }

    // =========================================================
    // HINT
    // =========================================================

    private void showHint() {

        if (
                guessField == null
                        ||
                        !guessField.isEnabled()
        ) {
            return;
        }

        String hint;

        if (
                secretNumber % 2 == 0
        ) {

            hint =
                    "HINT: The number is EVEN.";

        } else {

            hint =
                    "HINT: The number is ODD.";
        }

        JOptionPane.showMessageDialog(
                this,
                hint,
                "Hint",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // RANGE
    // =========================================================

    private void updateRangeLabel() {

        if (
                rangeLabel != null
        ) {

            rangeLabel.setText(
                    "Range: "
                            + minNumber
                            + " - "
                            + maxNumber
            );
        }
    }

    // =========================================================
    // LABELS
    // =========================================================

    private void updateLabels() {

        if (
                attemptsLabel != null
        ) {

            attemptsLabel.setText(
                    "Attempts: "
                            + attempts
                            + " / "
                            + maxAttempts
            );
        }

        if (
                scoreLabel != null
        ) {

            scoreLabel.setText(
                    "Score: "
                            + score
            );
        }

        if (
                roundLabel != null
        ) {

            roundLabel.setText(
                    "Round: "
                            + round
            );
        }

        if (
                streakLabel != null
        ) {

            streakLabel.setText(
                    "Win Streak: "
                            + winStreak
            );
        }

        if (
                bestScoreLabel != null
        ) {

            bestScoreLabel.setText(
                    "Best Score: "
                            + bestScore
            );
        }
    }

    // =========================================================
    // PROGRESS
    // =========================================================

    private void updateProgress() {

        if (
                progressBar == null
        ) {
            return;
        }

        progressBar.setMaximum(
                maxAttempts
        );

        progressBar.setValue(
                attempts
        );

        progressBar.setString(
                attempts
                        + " / "
                        + maxAttempts
        );
    }

    // =========================================================
    // TIMER
    // =========================================================

    private void startTimer() {

        stopTimer();

        seconds = 0;

        if (
                timerLabel != null
        ) {

            timerLabel.setText(
                    "TIME: 00:00"
            );
        }

        timer =
                new Timer(
                        1000,
                        e -> {

                            seconds++;

                            int minutes =
                                    seconds / 60;

                            int remainingSeconds =
                                    seconds % 60;

                            if (
                                    timerLabel
                                            != null
                            ) {

                                timerLabel.setText(
                                        String.format(
                                                "TIME: %02d:%02d",
                                                minutes,
                                                remainingSeconds
                                        )
                                );
                            }
                        }
                );

        timer.start();
    }

    // =========================================================
    // STOP TIMER
    // =========================================================

    private void stopTimer() {

        if (
                timer != null
        ) {

            timer.stop();

            timer = null;
        }
    }

    // =========================================================
    // HISTORY
    // =========================================================

    private void updateHistory(
            String newEntry
    ) {

        String oldHistory =
                historyLabel.getText();

        oldHistory =
                oldHistory.replace(
                        "<html>",
                        ""
                );

        oldHistory =
                oldHistory.replace(
                        "</html>",
                        ""
                );

        if (
                oldHistory.equals(
                        "No rounds completed yet."
                )
        ) {

            oldHistory = "";
        }

        String newHistory =
                "<html>"
                        + newEntry
                        + "<br>"
                        + oldHistory
                        + "</html>";

        historyLabel.setText(
                newHistory
        );
    }

    // =========================================================
    // RESULT
    // =========================================================

    private void showResult(
            boolean won
    ) {

        String resultMessage;

        if (won) {

            resultMessage =
                    "<font color='#3CC883'>"
                            + "CONGRATULATIONS!"
                            + "</font><br>"
                            + "<font size='4'>"
                            + "You guessed the number!"
                            + "</font>";

        } else {

            resultMessage =
                    "<font color='#F0505A'>"
                            + "BETTER LUCK NEXT TIME!"
                            + "</font><br>"
                            + "<font size='4'>"
                            + "The secret number was "
                            + secretNumber
                            + "</font>";
        }

        statisticsLabel.setText(
                "<html>"
                        + "<div style='text-align:center;'>"

                        + resultMessage

                        + "<br><br>"

                        + "Difficulty: "
                        + selectedDifficulty

                        + "<br>"

                        + "Attempts: "
                        + attempts

                        + "<br>"

                        + "Round Score: "
                        + lastRoundScore

                        + "<br>"

                        + "Total Score: "
                        + score

                        + "<br>"

                        + "Win Streak: "
                        + winStreak

                        + "<br>"

                        + "Best Score: "
                        + bestScore

                        + "<br>"

                        + "Time: "
                        + String.format(
                                "%02d:%02d",
                                seconds / 60,
                                seconds % 60
                        )

                        + "</div>"
                        + "</html>"
        );

        cardLayout.show(
                mainPanel,
                "RESULT"
        );
    }

    // =========================================================
    // ACHIEVEMENTS
    // =========================================================

    private void checkAchievements() {

        if (
                roundsWon >= 1
        ) {

            achievements.add(
                    "First Victory"
            );
        }

        if (
                attempts == 1
                        &&
                        roundsWon >= 1
        ) {

            achievements.add(
                    "Lucky Guess"
            );
        }

        if (
                winStreak >= 3
        ) {

            achievements.add(
                    "3 Win Streak"
            );
        }

        if (
                selectedDifficulty.equals(
                        "Hard"
                )
                        &&
                        attempts == 1
        ) {

            achievements.add(
                    "Hard Mode Perfect"
            );
        }

        if (
                score >= 500
        ) {

            achievements.add(
                    "Champion"
            );
        }

        if (
                attempts <= 2
                        &&
                        seconds <= 15
                        &&
                        roundsWon >= 1
        ) {

            achievements.add(
                    "Speed Master"
            );
        }
    }

    // =========================================================
    // SHOW ACHIEVEMENTS
    // =========================================================

    private void showAchievements() {

        checkAchievements();

        StringBuilder message =
                new StringBuilder();

        message.append(
                "<html>"
                        + "<div style='text-align:center;'>"
                        + "<h2>ACHIEVEMENTS</h2>"
        );

        String[] allAchievements = {

                "First Victory",

                "Lucky Guess",

                "3 Win Streak",

                "Hard Mode Perfect",

                "Champion",

                "Speed Master"
        };

        for (
                String achievement :
                allAchievements
        ) {

            if (
                    achievements.contains(
                            achievement
                    )
            ) {

                message.append(
                        "<p>"
                                + "[UNLOCKED] "
                                + achievement
                                + "</p>"
                );

            } else {

                message.append(
                        "<p>"
                                + "[LOCKED] "
                                + achievement
                                + "</p>"
                );
            }
        }

        message.append(
                "<br>"
                        + "Unlocked: "
                        + achievements.size()
                        + " / "
                        + allAchievements.length
                        + "</div>"
                        + "</html>"
        );

        JOptionPane.showMessageDialog(
                this,
                message.toString(),
                "Achievements",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // ADD LEADERBOARD ENTRY
    // =========================================================

    private void addLeaderboardEntry() {

        leaderboard.add(
                new ScoreEntry(
                        selectedDifficulty,
                        lastRoundScore,
                        attempts,
                        round
                )
        );

        leaderboard.sort(
                Comparator.comparingInt(
                        (ScoreEntry entry) ->
                                entry.score
                ).reversed()
        );

        // Keep only top 10
        if (
                leaderboard.size() > 10
        ) {

            leaderboard.remove(
                    leaderboard.size() - 1
            );
        }

        saveLeaderboard();
    }

    // =========================================================
    // SHOW LEADERBOARD
    // =========================================================

    private void showLeaderboard() {

        if (
                leaderboard.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No scores yet.\n"
                            + "Win a round to appear "
                            + "on the leaderboard!",
                    "High Scores",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String[] columns = {

                "Rank",

                "Difficulty",

                "Score",

                "Attempts",

                "Round"
        };

        Object[][] data =
                new Object[
                        leaderboard.size()
                ][5];

        for (
                int i = 0;
                i < leaderboard.size();
                i++
        ) {

            ScoreEntry entry =
                    leaderboard.get(i);

            data[i][0] =
                    i + 1;

            data[i][1] =
                    entry.difficulty;

            data[i][2] =
                    entry.score;

            data[i][3] =
                    entry.attempts;

            data[i][4] =
                    entry.round;
        }

        JTable table =
                new JTable(
                        data,
                        columns
                );

        table.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        table.setRowHeight(
                32
        );

        table.setBackground(
                CARD
        );

        table.setForeground(
                WHITE
        );

        table.setGridColor(
                new Color(
                        55,
                        70,
                        100
                )
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                14
                        )
                );

        table.getTableHeader()
                .setBackground(
                        BLUE
                );

        table.getTableHeader()
                .setForeground(
                        WHITE
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        table
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        600,
                        350
                )
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                BACKGROUND
        );

        JLabel title =
                new JLabel(
                        "HIGH SCORES"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        title.setForeground(
                GOLD
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Leaderboard",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // =========================================================
    // SAVE LEADERBOARD
    // =========================================================

    private void saveLeaderboard() {

        try (
                FileOutputStream fileOut =
                        new FileOutputStream(
                                SCORE_FILE
                        );

                ObjectOutputStream objectOut =
                        new ObjectOutputStream(
                                fileOut
                        )
        ) {

            objectOut.writeObject(
                    leaderboard
            );

        } catch (
                IOException e
        ) {

            System.out.println(
                    "Could not save scores: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // LOAD LEADERBOARD
    // =========================================================

    @SuppressWarnings("unchecked")
    private void loadLeaderboard() {

        try (
                FileInputStream fileIn =
                        new FileInputStream(
                                SCORE_FILE
                        );

                ObjectInputStream objectIn =
                        new ObjectInputStream(
                                fileIn
                        )
        ) {

            List<ScoreEntry> savedScores =
                    (List<ScoreEntry>)
                            objectIn.readObject();

            leaderboard.clear();

            leaderboard.addAll(
                    savedScores
            );

            leaderboard.sort(
                    Comparator.comparingInt(
                            (ScoreEntry entry) ->
                                    entry.score
                    ).reversed()
            );

            if (
                    leaderboard.size() > 10
            ) {

                leaderboard.subList(
                        10,
                        leaderboard.size()
                ).clear();
            }

        } catch (
                IOException
                |
                ClassNotFoundException e
        ) {

            // First run:
            // scores.dat does not exist yet.
        }
    }

    // =========================================================
    // UPDATE BEST SCORE FROM SAVED SCORES
    // =========================================================

    private void updateBestScoreFromLeaderboard() {

        bestScore = 0;

        for (
                ScoreEntry entry :
                leaderboard
        ) {

            if (
                    entry.score > bestScore
            ) {

                bestScore =
                        entry.score;
            }
        }
    }

    // =========================================================
    // INFO LABEL
    // =========================================================

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        label.setForeground(
                TEXT
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // CARD
    // =========================================================

    private JPanel createCardPanel(
            int width,
            int height
    ) {

        JPanel panel =
                new JPanel();

        panel.setPreferredSize(
                new Dimension(
                        width,
                        height
                )
        );

        panel.setBackground(
                CARD
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(
                                        45,
                                        60,
                                        90
                                ),
                                1
                        ),

                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        return panel;
    }

    // =========================================================
    // SCORE ENTRY CLASS
    // =========================================================

    private static class ScoreEntry
            implements Serializable {

        private static final long serialVersionUID =
                1L;

        String difficulty;

        int score;

        int attempts;

        int round;

        ScoreEntry(
                String difficulty,
                int score,
                int attempts,
                int round
        ) {

            this.difficulty =
                    difficulty;

            this.score =
                    score;

            this.attempts =
                    attempts;

            this.round =
                    round;
        }
    }
}