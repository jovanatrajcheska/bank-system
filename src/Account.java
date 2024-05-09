import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountId;
    private String username;
    private double accountBalance;
    private List<Transaction> transactions;

    public Account(String accountId, String username, double accountBalance) {
        this.accountId = accountId;
        this.username = username;
        this.accountBalance = accountBalance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Account findAccount(String accountId) throws AccountException {
        if (this.accountId.equals(accountId)) {
            return this;
        } else throw new AccountException("Account with this ID not found");
    }

    @Override
    public String toString() {
        return "Information about created account:" + '\'' +
                "Account ID: '" + accountId + '\'' +
                "Username: '" + username + '\'' +
                "Balance: $" + accountBalance + '\'';
    }

    public void deposit(double amount) throws AccountException {

        if (amount <= 0) {
            throw new AccountException("Amount to deposit must be greater than 0");
        }

        accountBalance += amount;
        transactions.add(new Transaction(amount, accountId, accountId, "Deposit", TransferType.DEPOSIT));
    }

    public void withdraw(double amount) throws AccountException {

        if (amount <= 0) {
            throw new AccountException("Amount to withdraw must be greater than 0");
        }

        accountBalance -= amount;
        transactions.add(new Transaction(amount, accountId, accountId, "Withdraw", TransferType.WITHDRAWAL));
    }


    public void listTransactions() {
        System.out.println("Transaction history for account with ID: " + accountId);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }


}
