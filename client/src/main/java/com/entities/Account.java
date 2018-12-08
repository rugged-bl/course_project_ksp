package com.entities;

public class Account {
    protected Account() {
    }

    public Account(long balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    private long balance;

    private AccountType accountType;
    //relates to client

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", accountType=" + accountType +
                ", id=" + id +
                '}';
    }
}
