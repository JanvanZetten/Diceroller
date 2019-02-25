package com.example.diceroller;

public class ImageHelper {

    public int getImageId(int number) {
        if (number == 1)
            return R.drawable.dice1;
        if (number == 2)
            return R.drawable.dice2;
        if (number == 3)
            return R.drawable.dice3;
        if (number == 4)
            return R.drawable.dice4;
        if (number == 5)
            return R.drawable.dice5;
        return R.drawable.dice6;
    }
}
