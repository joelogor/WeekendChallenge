
package safeBank;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;


import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("       WELCOME TO SAFEBANK");
        System.out.println("================================");

        BankAccount account = createAccount(scanner);

        boolean running = true;

        while (running) {

            System.out.println("\n========== SAFEBANK MENU ==========");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.println("===================================");

            int choice = readInteger(scanner, "Choose an option: ");

            switch (choice) {

                case 1:
                    depositMoney(scanner, account);
                    break;

                case 2:
                    withdrawMoney(scanner, account);
                    break;

                case 3:
                    System.out.printf(
                            "Current Balance: ₦%,.2f%n",
                            account.getBalance()
                    );
                    break;

                case 4:
                    displayTransactions(account);
                    break;

                case 5:
                    running = false;
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid menu selection. Choose 1-5.");
            }
        }

        scanner.close();
    }


    private static BankAccount createAccount(Scanner scanner) {

        while (true) {

            System.out.print("Enter account holder's name: ");
            String name = scanner.nextLine();

            double openingBalance = readDouble(
                    scanner, "Enter opening balance: ₦"
            );

            try {
                BankAccount account =
                        new BankAccount(name, openingBalance);

                System.out.println("\nAccount created successfully!");
                System.out.println("Welcome, " + account.getAccountHolder());

                return account;

            } catch (InvalidAmountException |
                     IllegalArgumentException exception) {

                System.out.println("Account creation failed: " + exception.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }


    private static void depositMoney(
            Scanner scanner, BankAccount account) {

        double amount = readDouble(scanner, "Enter deposit amount: ₦");

        try {
            account.deposit(amount);

            System.out.printf(
                    "Deposit successful! New balance: ₦%,.2f%n",
                    account.getBalance()
            );

        } catch (InvalidAmountException exception) {
            System.out.println("Deposit failed: " + exception.getMessage());
        }
    }


    private static void withdrawMoney(
            Scanner scanner, BankAccount account) {

        double amount = readDouble(scanner, "Enter withdrawal amount: ₦");

        try {
            account.withdraw(amount);

            System.out.printf(
                    "Withdrawal successful! New balance: ₦%,.2f%n",
                    account.getBalance()
            );

        } catch (InvalidAmountException |
                 InsufficientFundsException |
                 WithdrawalLimitExceededException exception) {

            System.out.println("Withdrawal failed: " + exception.getMessage());
        }
    }

    private static void displayTransactions(BankAccount account) {

        List<Transaction> transactions = account.getTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        System.out.println("=========================================");
    }


    private static double readDouble(
            Scanner scanner, String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Double.parseDouble(input);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a numeric amount."
                );
            }
        }
    }


    private static int readInteger(
            Scanner scanner, String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number."
                );
            }
        }
    }
}
