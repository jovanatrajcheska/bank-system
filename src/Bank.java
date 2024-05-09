import java.util.ArrayList;
import java.util.List;

public class Bank {

    private String bankName;
    private double transactionFlatFeeAmount;
    private double transactionPercentFeeValue;
    private double transactionFeeAmount;
    private double totalTransferAmount;
    private List<Account> accounts;

    public Bank(String bankName, double transactionFlatFeeAmount, double transactionPercentFeeValue) {
        this.bankName = bankName;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
        this.transactionPercentFeeValue = transactionPercentFeeValue;
        transactionFeeAmount = 0.00;
        totalTransferAmount = 0.00;
        accounts = new ArrayList<>();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getTransactionFlatFeeAmount() {
        return transactionFlatFeeAmount;
    }

    public void setTransactionFlatFeeAmount(double transactionFlatFeeAmount) {
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
    }

    public double getTransactionPercentFeeValue() {
        return transactionPercentFeeValue;
    }

    public void setTransactionPercentFeeValue(double transactionPercentFeeValue) {
        this.transactionPercentFeeValue = transactionPercentFeeValue;
    }

    public double getTransactionFeeAmount() {
        return transactionFeeAmount;
    }

    public void setTransactionFeeAmount(double transactionFeeAmount) {
        this.transactionFeeAmount = transactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    public void setTotalTransferAmount(double totalTransferAmount) {
        this.totalTransferAmount = totalTransferAmount;
    }

    public List<Account> getAccountList() {
        return accounts;
    }

    public void setAccountList(List<Account> accountList) {
        this.accounts = accountList;
    }

    private double calculateTransactionFee(double amount) {
        return transactionFlatFeeAmount + (transactionPercentFeeValue / 100) * amount;
    }

    public double checkTotalTransactionFeeAmount(String name) throws BankException {
        if (this.bankName.equals(name))
            return transactionFeeAmount;
        else throw new BankException("There is no bank with name: " + name);
    }

    private double parseAmount(String amountStr) throws BankException {
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                throw new BankException("Amount cannot be less than 0.");
            }
            return Math.round(amount * 100.0) / 100.0;
        } catch (NumberFormatException | BankException e) {
            throw new BankException("Invalid amount format.");
        }
    }

    public void performFlatFeeTransaction(Account from, Account to, double amount) throws AccountException {
        double totalAmountWithFee = amount + transactionFlatFeeAmount;
        from.withdraw(totalAmountWithFee);
        to.deposit(amount);
        Transaction transaction = new Transaction(amount, from.getAccountId(), to.getAccountId(), "Flat fee", TransferType.WITHDRAWAL);
        from.getTransactions().add(transaction);
        transactionFeeAmount += transactionFlatFeeAmount;
        totalTransferAmount += amount;
    }

    public void performPercentFeeTransaction(Account from, Account to, double amount) throws AccountException {
        double fee = (transactionPercentFeeValue / 100) * amount;
        double totalAmountWithFee = amount + fee;
        from.withdraw(totalAmountWithFee);
        to.deposit(amount);
        Transaction transaction = new Transaction(amount, from.getAccountId(), to.getAccountId(), "Percent fee", TransferType.WITHDRAWAL);
        from.getTransactions().add(transaction);
        transactionFeeAmount += fee;
        totalTransferAmount += amount;
    }

    public void performTransaction(Account from, Account to, double amount) throws AccountException {
        double fee = calculateTransactionFee(amount);
        double totalAmountWithFee = amount + fee;
        from.withdraw(totalAmountWithFee);
        to.deposit(amount);
        transactionFeeAmount += fee;
        totalTransferAmount += amount;
    }

    public void listAccounts(String name) throws BankException {
        if (this.bankName.equals(name)) {
            for (Account account : accounts) {
                System.out.println(account);
            }
        } else {
            throw new BankException("There is no bank with name: " + name);
        }
    }

    public double checkTotalTransferAmount(String name) throws BankException {
        if (this.bankName.equals(name))
            return totalTransferAmount;
        else throw new BankException("There is no bank with name: " + name);
    }

    public Account findAccountById(String accountId) throws AccountException {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        throw new AccountException("Account with ID " + accountId + " not found.");
    }


    public void listTransactions(String id) throws AccountException {
        Account account = findAccountById(id);
        System.out.println("Transaction history for account with ID: " + id);
        if (account.getAccountId().equals(id)) {
            for (Transaction transaction : account.getTransactions())
                System.out.println(transaction);
        }
    }
}
