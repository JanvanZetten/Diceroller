package com.example.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_ONE_ID = "imageOneId";
    private static final String IMAGE_TWO_ID = "imageTwoId";
    private static final String HISTORY = "history";
    private static final int HISTORY_REQUEST_CODE = 1;
    private static final int NUMBER_OF_DICE_SIDES = 6;

    private final ImageHelper imageHelper = new ImageHelper();
    private final LayoutHelper layoutHelper = new LayoutHelper(this);
    private final Random random = new Random();

    private int imgOneId = -1;
    private int imgTwoId = -1;
    private List<HistoryItem> history;

    private ImageView imgFirst;
    private ImageView imgSecond;
    private NumberPicker nrPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutHelper.getLayout());

        setupViewDependencies();
        loadState(savedInstanceState);

        nrPick = findViewById(R.id.nrPicker);
        nrPick.setMinValue(1);
        nrPick.setMaxValue(6);
    }

    @Override
    protected void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        state.putInt(IMAGE_ONE_ID, imgOneId);
        state.putInt(IMAGE_TWO_ID, imgTwoId);
        state.putSerializable(HISTORY, history.toArray());
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
        {
            imgOneId = savedInstanceState.getInt(IMAGE_ONE_ID);
            imgTwoId = savedInstanceState.getInt(IMAGE_TWO_ID);
            if (imgOneId > 0) {
                imgFirst.setImageResource(imgOneId);
            }
            if (imgTwoId > 0) {
                imgSecond.setImageResource(imgTwoId);
            }

            HistoryItem[] historyArray = (HistoryItem[]) savedInstanceState.getSerializable(HISTORY);
            history = new ArrayList<>(Arrays.asList(historyArray));
        }
        else {
            history = new ArrayList<>();
        }
    }

    private void setupViewDependencies(){
        imgFirst = findViewById(R.id.imgFirst);
        imgSecond = findViewById(R.id.imgSecond);
        Button rollBtn = findViewById(R.id.btnRoll);
        rollBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rollDices();
            }
        });
        Button historyBtn = findViewById(R.id.btnGoToHistory);
        historyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToHistory();
            }
        });
    }

    private void rollDices(){
        int number1 = random.nextInt(NUMBER_OF_DICE_SIDES) + 1;
        int number2 = random.nextInt(NUMBER_OF_DICE_SIDES) + 1;

        setImages(number1, number2);

        int[] results = {number1, number2};
        history.add(new HistoryItem(results, new Date()));
    }

    private void setImages(int number1, int number2) {
        imgOneId = imageHelper.getImageId(number1); //For state management
        imgFirst.setImageResource(imgOneId);
        imgTwoId = imageHelper.getImageId(number2); //For state management
        imgSecond.setImageResource(imgTwoId);
    }

    private void goToHistory(){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(HISTORY, history.toArray(new HistoryItem[0]));
        startActivityForResult(intent,HISTORY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HISTORY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                boolean clear = data.getBooleanExtra("cleared", false);
                if (clear){
                    clearHistory();
                }
            }
        }
    }

    private void clearHistory() {
        history.clear();
    }
}
