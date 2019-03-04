package com.example.diceroller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class HistoryItem implements Serializable {
    private int[] values;
    private Date timestamp;

    public HistoryItem(int[] values, Date timestamp) {
        this.values = values;
        this.timestamp = timestamp;
    }

    public int[] getValues() {
        return values;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "values=" + Arrays.toString(values) +
                ", timestamp=" + timestamp +
                '}';
    }
}
