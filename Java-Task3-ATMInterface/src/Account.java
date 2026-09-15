import java.util.ArrayList;

public class Account {
private String userId;
private String pin;
private String accountNumber;
private String accountHolder;
private double balance;

private ArrayList<Transaction> transactions;

public Account(String userId, String pin, String accountNumber,
               String accountHolder, double balance) {

    this.userId = userId;
    this.pin = pin;
    this.accountNumber = accountNumber;
    this.accountHolder = accountHolder;
    this.balance = balance;

    transactions = new ArrayList<>();
}

public String getUserId() {
    return userId;
}

public String getPin() {
    return pin;
}

public String getAccountNumber() {
    return accountNumber;
}

public String getAccountHolder() {
    return accountHolder;
}

public double getBalance() {
    return balance;
}

public ArrayList<Transaction> getTransactions() {
    return transactions;
}

public void deposit(double amount) {
    balance += amount;

    transactions.add(
        new Transaction("Deposit", amount, balance)
    );
}

public boolean withdraw(double amount) {

    if (amount > balance) {
        return false;
    }

    balance -= amount;

    transactions.add(
        new Transaction("Withdrawal", amount, balance)
    );

    return true;
}

public boolean transfer(double amount, Account receiver) {

    if (amount > balance) {
        return false;
    }

    balance -= amount;
    receiver.balance += amount;

    transactions.add(
        new Transaction(
            "Transfer to " + receiver.getAccountNumber(),
            amount,
            balance
        )
    );

    receiver.transactions.add(
        new Transaction(
            "Received from " + accountNumber,
            amount,
            receiver.balance
        )
    );

    return true;
}
}
