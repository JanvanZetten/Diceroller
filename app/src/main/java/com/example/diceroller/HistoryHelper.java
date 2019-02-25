package com.example.diceroller;

public class HistoryHelper {

    public String generateHistoryText(int number1, int number2) {
        int total = number1 + number2;
        return (number1 + " and " + number2 + " total amount " + total);
    }
}
