import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Welcome to the Banking System ===");

        // Register customer
        System.out.print("Enter First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        Customer customer = new Customer(firstName, lastName, address);

        int choice;
        do {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. View Accounts");
            System.out.println("4. Apply Monthly Interest");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Open account
                    System.out.println("\nChoose Account Type:");
                    System.out.println("1. Savings Account");
                    System.out.println("2. Investment Account");
                    System.out.println("3. Cheque Account");
                    System.out.print("Enter choice: ");
                    int accType = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Account Number: ");
                    String accNum = sc.nextLine();
                    System.out.print("Enter Branch: ");
                    String branch = sc.nextLine();

                    try {
                        Account acc = null;
                        if (accType == 1) {
                            acc = new SavingsAccount(accNum, customer, branch);
                        } else if (accType == 2) {
                            System.out.print("Enter Initial Deposit (min 500): ");
                            double initDeposit = sc.nextDouble();
                            sc.nextLine();
                            acc = new InvestmentAccount(accNum, customer, branch, initDeposit);
                        } else if (accType == 3) {
                            System.out.print("Enter Employer Name: ");
                            String emp = sc.nextLine();
                            System.out.print("Enter Employer Address: ");
                            String empAddr = sc.nextLine();
                            acc = new ChequeAccount(accNum, customer, branch, emp, empAddr);
                        }

                        if (acc != null) {
                            customer.addAccount(acc);
                            System.out.println("Account opened successfully!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // Deposit
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("No accounts available. Open one first.");
                        break;
                    }
                    System.out.println("\nYour Accounts:");
                    for (int i = 0; i < customer.getAccounts().size(); i++) {
                        System.out.println((i + 1) + ". " + customer.getAccounts().get(i));
                    }
                    System.out.print("Choose account number to deposit into: ");
                    int accIndex = sc.nextInt() - 1;
                    System.out.print("Enter deposit amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    if (accIndex >= 0 && accIndex < customer.getAccounts().size()) {
                        customer.getAccounts().get(accIndex).deposit(amount);
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Invalid account choice.");
                    }
                    break;

                case 3: // View accounts
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("No accounts available.");
                    } else {
                        System.out.println("\n--- Your Accounts ---");
                        for (Account acc : customer.getAccounts()) {
                            System.out.println(acc);
                        }
                    }
                    break;

                case 4: // Apply interest
                    if (customer.getAccounts().isEmpty()) {
                        System.out.println("No accounts available.");
                    } else {
                        for (Account acc : customer.getAccounts()) {
                            acc.applyInterest();
                        }
                        System.out.println("Interest applied to all eligible accounts.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting Banking System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}

// ------------------- Customer Class -------------------
class Customer {
    private String firstName;
    private String lastName;
    private String address;
    private List<Account> accounts;

    public Customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + address + ")";
    }
}

// ------------------- Abstract Account -------------------
abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer customer;

    public Account(String accountNumber, Customer customer, String branch) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.branch = branch;
        this.balance = 0.0;
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void applyInterest();

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }

    @Override
    public String toString() {
        return accountNumber + " | Balance: " + balance + " | Branch: " + branch;
    }
}

// ------------------- SavingsAccount -------------------
class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public SavingsAccount(String accountNumber, Customer customer, String branch) {
        super(accountNumber, customer, branch);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals not allowed from Savings Account!");
    }

    @Override
    public void applyInterest() {
        balance += balance * INTEREST_RATE;
    }
}

// ------------------- InvestmentAccount -------------------
class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05; // 5% monthly

    public InvestmentAccount(String accountNumber, Customer customer, String branch, double initialDeposit) {
        super(accountNumber, customer, branch);
        if (initialDeposit >= 500) {
            this.balance = initialDeposit;
        } else {
            throw new IllegalArgumentException("Minimum opening balance is BWP500.00");
        }
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    @Override
    public void applyInterest() {
        balance += balance * INTEREST_RATE;
    }
}

// ------------------- ChequeAccount -------------------
class ChequeAccount extends Account {
    private String employer;
    private String employerAddress;

    public ChequeAccount(String accountNumber, Customer customer, String branch, String employer, String employerAddress) {
        super(accountNumber, customer, branch);
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    @Override
    public void applyInterest() {
        // Cheque accounts usually don't earn interest
    }
}
