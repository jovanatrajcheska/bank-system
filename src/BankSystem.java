import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static Bank bank;
    private static Account account;
    private static Account from;
    private static Account to;
    private static double amount;

    public static void instructions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In the next few lines you will see what choices you can make\n");
        stringBuilder.append("Note: You must have created a bank and an account to perform and list transactions, check the status of accounts etc.\n");
        stringBuilder.append("1: Create New Account\n");
        stringBuilder.append("2: Perform Transaction - withdraw\n");
        stringBuilder.append("3: Perform Transaction - deposit\n");
        stringBuilder.append("4: Check balance\n");
        stringBuilder.append("5: Check Total Transfer Amount\n");
        stringBuilder.append("6: Check Total Transaction Fee Amount\n");
        stringBuilder.append("7: List all accounts in specific bank\n");
        stringBuilder.append("8: Transfer money from one to another account - flat fee\n");
        stringBuilder.append("9: Transfer money from one to another account - percent fee\n");
        stringBuilder.append("10: List transaction for specific account ID\n");
        stringBuilder.append("0: Close\n");

        System.out.println(stringBuilder.toString());
    }

    private static Bank createBank() {
        System.out.print("Enter bank name: ");
        String bankName = scanner.nextLine();
        System.out.print("Enter transaction flat fee amount: ");
        double flatFee = scanner.nextDouble();
        System.out.print("Enter transaction percent fee value: ");
        double percentFee = scanner.nextDouble();
        bank = new Bank(bankName, flatFee, percentFee);
        return bank;
    }

    private static Account createAccount() {
        if (bank == null) {
            System.out.println("Please create a bank first.");
            return null;
        }
        System.out.print("Enter account ID: ");
        String accountId = scanner.next();
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        account = new Account(accountId, username, initialBalance);
        bank.getAccountList().add(account);
        account.toString();
        return account;
    }

    public static void withdrawTransaction() throws AccountException {
        System.out.println("Insert amount");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    public static void depositTransaction() throws AccountException {
        System.out.println("Insert amount");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    public static void balanceChecking() {
        System.out.println("Your balance is: $" + String.format("%.2f", account.getAccountBalance()));

    }

    public static void totalTransferAmountCheck() throws BankException {
        System.out.println("Enter the name of the bank you want to check");
        String name = scanner.nextLine();
        bank.checkTotalTransferAmount(name);
    }

    public static void transactionFeeAmountCheck() throws BankException {
        System.out.println("Enter the name of the bank you want to check");
        String name = scanner.nextLine();
        bank.checkTotalTransactionFeeAmount(name);
    }

    public static void listAccountInSpecificBank() throws BankException {
        System.out.println("Enter the name of the bank");
        String name = scanner.nextLine();
        if (name.equals(bank.getBankName()))
            bank.listAccounts(bank.getBankName());
        else throw new BankException("No bank with that name");
    }

    public static void transferAccounts() throws AccountException {
        if (bank.getAccountList().size() < 2)
            throw new IllegalArgumentException("There are not enough accounts to transfer money from one to another.");

        System.out.println("Enter the sender ID");
        String sender = scanner.nextLine();

        System.out.println("Enter the recipient ID");
        String recipient = scanner.nextLine();

        if (Objects.equals(sender, recipient))
            throw new IllegalArgumentException("You have entered same IDs");

        List<String> ids = new ArrayList<>();

        for (Account acc : bank.getAccountList()) {
            ids.add(acc.getAccountId());
        }

        if (!ids.contains(sender)) System.out.println("There is no account with this ID to send from.");
        if (!ids.contains(recipient)) System.out.println("There is no account with this ID to send to.");

        from = bank.findAccountById(sender);
        to = bank.findAccountById(recipient);

        System.out.println("Enter the amount you want to transfer");
        amount = scanner.nextDouble();
    }

    public static void afterTransferStatus() {
        System.out.println("Sender balance is: $" + String.format("%.2f", from.getAccountBalance()));
        System.out.println("Recipient balance is: $" + String.format("%.2f", to.getAccountBalance()));
        System.out.println("Transfer successful.");
    }

    public static void transferMoneyBetweenAccountsFlatFee() throws AccountException {
        transferAccounts();
        bank.performFlatFeeTransaction(from, to, amount);
        afterTransferStatus();
    }

    public static void transferMoneyBetweenAccountsPercentFee() throws AccountException {
        transferAccounts();
        bank.performPercentFeeTransaction(from, to, amount);
        afterTransferStatus();

    }

    public static void transactionListByAccountID() throws AccountException {
        System.out.println("Enter the account ID for which you need a list of made transactions.");
        String accountId = scanner.nextLine();
        bank.listTransactions(accountId);
    }

    public static void main(String[] args) throws AccountException, BankException {
        System.out.println("Firstly you need to create a bank and one account.");

        Bank bank = createBank();
        Account account = createAccount();

        instructions();
        System.out.println("In the next line you will be asked to write a number based on your choice. For more information read the instructions below");

        while (true) {

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1 -> {
                    Account a = createAccount();
                }
                case 2 -> withdrawTransaction();
                case 3 -> depositTransaction();
                case 4 -> balanceChecking();
                case 5 -> totalTransferAmountCheck();
                case 6 -> transactionFeeAmountCheck();
                case 7 -> listAccountInSpecificBank();
                case 8 -> transferMoneyBetweenAccountsFlatFee();
                case 9 -> transferMoneyBetweenAccountsPercentFee();
                case 10 -> transactionListByAccountID();
                case 0 -> {
                    System.out.println("Close");
                    return;
                }
                default -> System.out.println("Invalid input. Please read the instructions carefully.");
            }
        }
    }
}
