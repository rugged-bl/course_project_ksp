package com.entities;

public class Operation {
    public enum Type {
        CHARGE, WITHDRAW
    }

    protected Operation() {
    }

    public Operation(Type type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    private Long id;

    private Type type;
    private long amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
