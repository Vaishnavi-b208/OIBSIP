import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner scanner;
    private Account currentAccount;

    // ANSI colors
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String WHITE = "\u001B[37m";

    private static final int SCREEN_WIDTH = 100;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    // ==============================
    // START ATM
    // ==============================
    public void start() {

        clearScreen();

        showWelcome();

        if (!login()) {
            goodbye();
            return;
        }

        showAccountSummary();

        pause();

        mainMenu();
    }

    // ==============================
    // WELCOME SCREEN
    // ==============================
    private void showWelcome() {

        center(BLUE + BOLD +
                "========================================================" +
                RESET);

        center(CYAN + BOLD +
                "                 SMART BANK ATM" +
                RESET);

        center(WHITE +
                "              SECURE | SIMPLE | FAST" +
                RESET);

        center(BLUE + BOLD +
                "========================================================" +
                RESET);

        System.out.println();

        center(YELLOW + BOLD +
                "Welcome to Smart Bank" +
                RESET);

        center(WHITE +
                "Your trusted banking partner" +
                RESET);

        System.out.println();

        center(CYAN +
                "Please insert your credentials to continue." +
                RESET);

        System.out.println();
    }

    // ==============================
    // LOGIN
    // ==============================
    private boolean login() {

        center(CYAN + BOLD +
                "---------------- ATM LOGIN ----------------" +
                RESET);

        System.out.println();

        for (int attempt = 1; attempt <= 3; attempt++) {

            System.out.print(
                    YELLOW + "Enter User ID: " + RESET
            );

            String userId = scanner.nextLine();

            System.out.print(
                    YELLOW + "Enter PIN: " + RESET
            );

            String pin = scanner.nextLine();

            System.out.println();

            showProcessing();

            Account account = bank.authenticate(userId, pin);

            if (account != null) {

                currentAccount = account;

                center(GREEN + BOLD +
                        "╔════════════════════════════════════════════════════╗" +
                        RESET);

                center(GREEN + BOLD +
                        "║              LOGIN SUCCESSFUL                     ║" +
                        RESET);

                center(GREEN + BOLD +
                        "╚════════════════════════════════════════════════════╝" +
                        RESET);

                return true;
            }

            center(RED + BOLD +
                    "Invalid User ID or PIN." +
                    RESET);

            center(YELLOW +
                    "Attempts remaining: " + (3 - attempt) +
                    RESET);

            System.out.println();
        }

        center(RED + BOLD +
                "========================================================" +
                RESET);

        center(RED + BOLD +
                "           ACCESS DENIED - 3 ATTEMPTS USED" +
                RESET);

        center(RED + BOLD +
                "========================================================" +
                RESET);

        return false;
    }

    // ==============================
    // ACCOUNT SUMMARY
    // ==============================
    private void showAccountSummary() {

        System.out.println();

        center(CYAN + BOLD +
                "╔════════════════════════════════════════════════════╗" +
                RESET);

        center(CYAN + BOLD +
                "║                 ACCOUNT SUMMARY                    ║" +
                RESET);

        center(CYAN + BOLD +
                "╚════════════════════════════════════════════════════╝" +
                RESET);

        System.out.println();

        center(WHITE +
                "Account Holder : " +
                GREEN + currentAccount.getAccountHolder() +
                RESET);

        center(WHITE +
                "Account Number : " +
                GREEN + currentAccount.getAccountNumber() +
                RESET);

        System.out.println();

        showBalance();

        System.out.println();

        center(YELLOW +
                "Welcome to Smart Bank, " +
                currentAccount.getAccountHolder() +
                "!" +
                RESET);
    }

    // ==============================
    // MAIN MENU
    // ==============================
    private void mainMenu() {

        while (true) {

            clearScreen();

            center(BLUE + BOLD +
                    "========================================================" +
                    RESET);

            center(CYAN + BOLD +
                    "                  SMART BANK ATM" +
                    RESET);

            center(WHITE +
                    "              Welcome, " +
                    currentAccount.getAccountHolder() +
                    RESET);

            center(BLUE + BOLD +
                    "========================================================" +
                    RESET);

            System.out.println();

            center(MAGENTA + BOLD +
                    "                 MAIN MENU" +
                    RESET);

            System.out.println();

            center(CYAN + "[1] " + WHITE + "Transaction History" + RESET);
            center(CYAN + "[2] " + WHITE + "Withdraw Money" + RESET);
            center(CYAN + "[3] " + WHITE + "Deposit Money" + RESET);
            center(CYAN + "[4] " + WHITE + "Transfer Money" + RESET);
            center(CYAN + "[5] " + WHITE + "Quit" + RESET);

            System.out.println();

            center(YELLOW +
                    "Account Balance: Rs. " +
                    String.format("%.2f", currentAccount.getBalance()) +
                    RESET);

            System.out.println();

            System.out.print(
                    YELLOW + "Enter your choice: " + RESET
            );

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    transactionHistory();
                    break;

                case "2":
                    withdraw();
                    break;

                case "3":
                    deposit();
                    break;

                case "4":
                    transfer();
                    break;

                case "5":
                    goodbye();
                    return;

                default:
                    center(RED + BOLD +
                            "Invalid choice. Please select 1 to 5." +
                            RESET);

                    pause();
            }
        }
    }

    // ==============================
    // BALANCE DISPLAY
    // ==============================
    private void showBalance() {

        center(GREEN + BOLD +
                "╔════════════════════════════════════════════════════╗" +
                RESET);

        center(GREEN + BOLD +
                "║                  CURRENT BALANCE                   ║" +
                RESET);

        center(GREEN + BOLD +
                "║                                                    ║" +
                RESET);

        String balanceText =
                "Rs. " +
                String.format("%.2f", currentAccount.getBalance());

        center(GREEN + BOLD +
                "║              " + balanceText +
                "                     ║" +
                RESET);

        center(GREEN + BOLD +
                "║                                                    ║" +
                RESET);

        center(GREEN + BOLD +
                "╚════════════════════════════════════════════════════╝" +
                RESET);
    }

    // ==============================
    // WITHDRAW
    // ==============================
    private void withdraw() {

        clearScreen();

        center(CYAN + BOLD +
                "================ WITHDRAW MONEY ================" +
                RESET);

        System.out.println();

        System.out.print(
                YELLOW + "Enter withdrawal amount: Rs. " + RESET
        );

        double amount = readAmount();

        if (amount <= 0) {

            center(RED + BOLD +
                    "Amount must be greater than zero." +
                    RESET);

            pause();
            return;
        }

        if (amount > currentAccount.getBalance()) {

            center(RED + BOLD +
                    "INSUFFICIENT FUNDS" +
                    RESET);

            center(RED +
                    "Available Balance: Rs. " +
                    String.format("%.2f",
                            currentAccount.getBalance()) +
                    RESET);

            pause();
            return;
        }

        showProcessing();

        currentAccount.withdraw(amount);

        showReceipt(
                "WITHDRAWAL",
                amount,
                currentAccount.getBalance()
        );

        pause();
    }

    // ==============================
    // DEPOSIT
    // ==============================
    private void deposit() {

        clearScreen();

        center(CYAN + BOLD +
                "================ DEPOSIT MONEY ================" +
                RESET);

        System.out.println();

        System.out.print(
                YELLOW + "Enter deposit amount: Rs. " + RESET
        );

        double amount = readAmount();

        if (amount <= 0) {

            center(RED + BOLD +
                    "Amount must be greater than zero." +
                    RESET);

            pause();
            return;
        }

        showProcessing();

        currentAccount.deposit(amount);

        showReceipt(
                "DEPOSIT",
                amount,
                currentAccount.getBalance()
        );

        pause();
    }

    // ==============================
    // TRANSFER
    // ==============================
    private void transfer() {

        clearScreen();

        center(CYAN + BOLD +
                "================ TRANSFER MONEY ================" +
                RESET);

        System.out.println();

        System.out.print(
                YELLOW + "Enter recipient account number: " + RESET
        );

        String recipientNumber = scanner.nextLine();

        Account receiver =
                bank.findAccount(recipientNumber);

        if (receiver == null) {

            center(RED + BOLD +
                    "Recipient account not found." +
                    RESET);

            pause();
            return;
        }

        if (receiver == currentAccount) {

            center(RED + BOLD +
                    "You cannot transfer money to your own account." +
                    RESET);

            pause();
            return;
        }

        System.out.print(
                YELLOW + "Enter transfer amount: Rs. " + RESET
        );

        double amount = readAmount();

        if (amount <= 0) {

            center(RED + BOLD +
                    "Amount must be greater than zero." +
                    RESET);

            pause();
            return;
        }

        if (amount > currentAccount.getBalance()) {

            center(RED + BOLD +
                    "INSUFFICIENT FUNDS" +
                    RESET);

            pause();
            return;
        }

        // Transfer confirmation
        System.out.println();

        center(YELLOW + BOLD +
                "--------------- TRANSFER CONFIRMATION ---------------" +
                RESET);

        System.out.println();

        center(WHITE +
                "From Account : " +
                currentAccount.getAccountNumber() +
                RESET);

        center(WHITE +
                "To Account   : " +
                receiver.getAccountNumber() +
                RESET);

        center(WHITE +
                "Recipient    : " +
                receiver.getAccountHolder() +
                RESET);

        center(WHITE +
                "Amount       : Rs. " +
                String.format("%.2f", amount) +
                RESET);

        System.out.println();

        System.out.print(
                YELLOW +
                "Confirm transfer? (Y/N): " +
                RESET
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {

            center(RED +
                    "Transfer cancelled." +
                    RESET);

            pause();
            return;
        }

        showProcessing();

        currentAccount.transfer(amount, receiver);

        showReceipt(
                "TRANSFER TO " +
                receiver.getAccountNumber(),
                amount,
                currentAccount.getBalance()
        );

        pause();
    }

    // ==============================
    // TRANSACTION HISTORY
    // ==============================
    private void transactionHistory() {

        clearScreen();

        center(CYAN + BOLD +
                "========================================================" +
                RESET);

        center(CYAN + BOLD +
                "                TRANSACTION HISTORY" +
                RESET);

        center(CYAN + BOLD +
                "========================================================" +
                RESET);

        System.out.println();

        center(WHITE +
                "Account Holder : " +
                currentAccount.getAccountHolder() +
                RESET);

        center(WHITE +
                "Account Number : " +
                currentAccount.getAccountNumber() +
                RESET);

        center(YELLOW +
                "Total Transactions: " +
                currentAccount.getTransactions().size() +
                RESET);

        System.out.println();

        if (currentAccount.getTransactions().isEmpty()) {

            center(YELLOW +
                    "No transactions available." +
                    RESET);

        } else {

            center(MAGENTA +
                    "--------------------------------------------------------" +
                    RESET);

            for (Transaction transaction :
                    currentAccount.getTransactions()) {

                center(WHITE +
                        transaction.toString() +
                        RESET);
            }

            center(MAGENTA +
                    "--------------------------------------------------------" +
                    RESET);
        }

        System.out.println();

        center(GREEN +
                "Current Balance: Rs. " +
                String.format("%.2f",
                        currentAccount.getBalance()) +
                RESET);

        pause();
    }

    // ==============================
    // RECEIPT
    // ==============================
    private void showReceipt(
            String transactionType,
            double amount,
            double balance) {

        System.out.println();

        center(GREEN + BOLD +
                "╔════════════════════════════════════════════════════╗" +
                RESET);

        center(GREEN + BOLD +
                "║                 TRANSACTION RECEIPT               ║" +
                RESET);

        center(GREEN + BOLD +
                "╠════════════════════════════════════════════════════╣" +
                RESET);

        center(WHITE +
                "Transaction Type : " +
                transactionType +
                RESET);

        center(WHITE +
                "Account Number   : " +
                currentAccount.getAccountNumber() +
                RESET);

        center(WHITE +
                "Amount           : Rs. " +
                String.format("%.2f", amount) +
                RESET);

        center(WHITE +
                "Balance          : Rs. " +
                String.format("%.2f", balance) +
                RESET);

        center(GREEN + BOLD +
                "╠════════════════════════════════════════════════════╣" +
                RESET);

        center(GREEN + BOLD +
                "║            TRANSACTION SUCCESSFUL                 ║" +
                RESET);

        center(GREEN + BOLD +
                "╚════════════════════════════════════════════════════╝" +
                RESET);
    }

    // ==============================
    // PROCESSING EFFECT
    // ==============================
    private void showProcessing() {

        center(YELLOW +
                "Processing..." +
                RESET);

        try {
            Thread.sleep(400);

            center(YELLOW +
                    "Please wait..." +
                    RESET);

            Thread.sleep(400);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==============================
    // READ AMOUNT
    // ==============================
    private double readAmount() {

        while (true) {

            String input = scanner.nextLine();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                center(RED +
                        "Please enter a valid numeric amount." +
                        RESET);

                System.out.print(
                        YELLOW +
                        "Enter amount again: Rs. " +
                        RESET
                );
            }
        }
    }

    // ==============================
    // PAUSE
    // ==============================
    private void pause() {

        System.out.println();

        center(YELLOW +
                "Press Enter to continue..." +
                RESET);

        scanner.nextLine();

        clearScreen();
    }

    // ==============================
    // GOODBYE
    // ==============================
    private void goodbye() {

        clearScreen();

        center(BLUE + BOLD +
                "========================================================" +
                RESET);

        center(CYAN + BOLD +
                "              THANK YOU FOR BANKING WITH US" +
                RESET);

        center(WHITE +
                "                  SMART BANK ATM" +
                RESET);

        center(GREEN +
                "                 Have a great day!" +
                RESET);

        center(BLUE + BOLD +
                "========================================================" +
                RESET);
    }

    // ==============================
    // CENTER TEXT
    // ==============================
    private void center(String text) {

        String cleanText =
                text.replaceAll("\u001B\\[[;\\d]*m", "");

        int visibleLength = cleanText.length();

        int spaces =
                (SCREEN_WIDTH - visibleLength) / 2;

        if (spaces < 0) {
            spaces = 0;
        }

        System.out.println(
                " ".repeat(spaces) + text
        );
    }

    // ==============================
    // CLEAR SCREEN
    // ==============================
    private void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}