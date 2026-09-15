import java.util.HashMap;
public class Bank {
private HashMap<String, Account> accounts;

public Bank() {

    accounts = new HashMap<>();

    addAccount(
        new Account(
            "vaishnavi",
            "1234",
            "1001001",
            "Vaishnavi Biradar",
            25000.00
        )
    );

    addAccount(
        new Account(
            "rahul",
            "5678",
            "1001002",
            "Rahul Patil",
            15000.00
        )
    );

    addAccount(
        new Account(
            "priya",
            "2468",
            "1001003",
            "Priya Sharma",
            20000.00
        )
    );
}

public void addAccount(Account account) {
    accounts.put(account.getAccountNumber(), account);
}

public Account authenticate(String userId, String pin) {

    for (Account account : accounts.values()) {

        if (account.getUserId().equals(userId)
                && account.getPin().equals(pin)) {

            return account;
        }
    }

    return null;
}

public Account findAccount(String accountNumber) {
    return accounts.get(accountNumber);
}
}
