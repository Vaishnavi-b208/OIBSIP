import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
private String type;
private double amount;
private double balanceAfter;
private LocalDateTime dateTime;

public Transaction(String type, double amount, double balanceAfter) {
    this.type = type;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
    this.dateTime = LocalDateTime.now();
}

public String getType() {
    return type;
}

public double getAmount() {
    return amount;
}

public double getBalanceAfter() {
    return balanceAfter;
}

public LocalDateTime getDateTime() {
    return dateTime;
}

@Override
public String toString() {

    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    return String.format(
        "%-25s Rs.%-10.2f Balance: Rs.%-10.2f %s",
        type,
        amount,
        balanceAfter,
        dateTime.format(formatter)
    );
}
}
