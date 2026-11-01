package safeBank;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitExceededException;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private final String accountHolder;
    private double balance;

    private final List<Transaction> transactions = new ArrayList<>();

    private static final double WITHDRAWAL_LIMIT = 100000;


    public BankAccount(String accountHolder, double openingBalance)
            throws InvalidAmountException {

        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException(
                    "Account holder name cannot be blank"
            );
        }

        if (openingBalance < 0) {
            throw new InvalidAmountException(
                    "Opening balance cannot be negative"
            );
        }

        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }


    public String getAccountHolder() {
        return accountHolder;
    }


    public double getBalance() {
        return balance;
    }


    public void deposit(double amount) throws InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit must be greater than zero"
            );
        }

        balance += amount;

        transactions.add(new Transaction("DEPOSIT", amount));
    }

    public void withdraw(double amount)
            throws InvalidAmountException,
            InsufficientFundsException,
            WithdrawalLimitExceededException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal must be greater than zero"
            );
        }

        if (amount > WITHDRAWAL_LIMIT) {
            throw new WithdrawalLimitExceededException(
                    "Maximum withdrawal per transaction is ₦100,000"
            );
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient account balance"
            );
        }

        balance -= amount;

        transactions.add(new Transaction("WITHDRAWAL", amount));
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}