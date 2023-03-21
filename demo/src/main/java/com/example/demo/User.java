package com.example.demo;

public class User {
    private double balance;
    private String userid;

    public User(String userid, double balance) {
        this.balance = balance;
        this.userid = userid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double addBalance(double amount) {
        this.balance += amount;
        return this.balance;
    }

    public boolean withdrawBalance(double amount){
        if((this.balance - amount) < 0){
            return false;
        }

        this.balance -= amount;

        return true;
    }

    public void deleteBalance(){
        this.balance = 0;
    }
}
