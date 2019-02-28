package com.example.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;


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

//    private int imgOneId = -1;
//    private int imgTwoId = -1;
    private List<HistoryItem> history;

//    private ImageView imgFirst;
//    private ImageView imgSecond;
    private ImageView[] diceImageViews;
    private NumberPicker nrPick;
    private LinearLayout llDiceUpper;
    private LinearLayout llDiceLower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutHelper.getLayout());

        setupViewDependencies();
        loadState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        //state.putInt(IMAGE_ONE_ID, imgOneId);
        //state.putInt(IMAGE_TWO_ID, imgTwoId);
        state.putSerializable(HISTORY, history.toArray(new HistoryItem[0]));
    }

    private void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
        {
//            imgOneId = savedInstanceState.getInt(IMAGE_ONE_ID);
//            imgTwoId = savedInstanceState.getInt(IMAGE_TWO_ID);
//            if (imgOneId > 0) {
//                imgFirst.setImageResource(imgOneId);
//            }
//            if (imgTwoId > 0) {
//                imgSecond.setImageResource(imgTwoId);
//            }

            HistoryItem[] historyArray = (HistoryItem[]) savedInstanceState.getSerializable(HISTORY);
            history = new ArrayList<>(Arrays.asList(historyArray));
        }
        else {
            history = new ArrayList<>();
        }
    }

    private void setupViewDependencies(){
//        imgFirst = findViewById(R.id.imgFirst);
//        imgSecond = findViewById(R.id.imgSecond);
        llDiceUpper = findViewById(R.id.llDicesUpper);
        llDiceLower = findViewById(R.id.llDicesLower);
        Button rollBtn = findViewById(R.id.btnRoll);
        nrPick = findViewById(R.id.nrPicker);
        nrPick.setMinValue(1);
        nrPick.setMaxValue(6);
        nrPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                createDiceImagesViews(newVal);
            }
        });
        createDiceImagesViews(1);
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

    private void createDiceImagesViews(int numberOfDice) {
        emptyViews();
        diceImageViews = new ImageView[numberOfDice];
        for(int i = 0; i < numberOfDice; i++){
            diceImageViews[i] = new ImageView(this);
        }
        setDicesOnView();
    }

    private void emptyViews(){
            llDiceLower.removeAllViews();
            llDiceUpper.removeAllViews();
    }

    private void setUpDiceImageView(ImageView diceImageView) {
        diceImageView.getLayoutParams().height = 150;
        diceImageView.getLayoutParams().width = 150;
        ((ViewGroup.MarginLayoutParams) diceImageView.getLayoutParams()).leftMargin = 3;
        ((ViewGroup.MarginLayoutParams) diceImageView.getLayoutParams()).rightMargin = 3;
    }

    private void setDicesOnView() {
        if (diceImageViews.length < 4){
            for (ImageView diceView : diceImageViews) {
                llDiceUpper.addView(diceView);
            }
        }else if(diceImageViews.length == 4){
            for (int i = 0; i < 4; i ++){
                if (i < 2){
                    llDiceUpper.addView(diceImageViews[i]);
                }else{
                    llDiceLower.addView(diceImageViews[i]);
                }
            }
        }else{
            for (int i = 0; i < diceImageViews.length; i ++){
                if (i < 3){
                    llDiceUpper.addView(diceImageViews[i]);
                }else{
                    llDiceLower.addView(diceImageViews[i]);
                }
            }
        }
    }

    private void rollDices(){

        int[] numbers = new int[diceImageViews.length];

        for(int i = 0; i < diceImageViews.length; i ++){
            numbers[i] = random.nextInt(NUMBER_OF_DICE_SIDES) + 1;
            setUpDiceImageView(diceImageViews[i]);
        }

        setImages(numbers);
        history.add(new HistoryItem(numbers, new Date()));
    }

    private void setImages(int[] numbers) {
        for(int i = 0; i < diceImageViews.length; i++){
            diceImageViews[i].setImageResource(imageHelper.getImageId(numbers[i]));
        }
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
