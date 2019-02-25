package com.example.diceroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_ONE_ID = "imageOneId";
    private static final String IMAGE_TWO_ID = "imageTwoId";
    private static final String HISTORY_MESSAGES = "historyMessages";
    private static final int NUMBER_OF_HISTORY_ITEMS = 5;
    private static final int NUMBER_OF_DICE_SIDES = 6;

    private final ImageHelper imageHelper = new ImageHelper();
    private final LayoutHelper layoutHelper = new LayoutHelper(this);
    private final HistoryHelper historyHelper = new HistoryHelper();
    private final Random random = new Random();

    private int imgOneId = -1;
    private int imgTwoId = -1;
    private String[] historyMessages= new String[NUMBER_OF_HISTORY_ITEMS];

    private LinearLayout llHistoryView;
    private TextView[] HistoryTextViews = new TextView[NUMBER_OF_HISTORY_ITEMS];
    private ImageView imgFirst;
    private ImageView imgSecond;

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
        state.putInt(IMAGE_ONE_ID, imgOneId);
        state.putInt(IMAGE_TWO_ID, imgTwoId);
        state.putStringArray(HISTORY_MESSAGES, historyMessages);
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

            String[] historyMessagesTemp = savedInstanceState.getStringArray(HISTORY_MESSAGES);

            for (int i = historyMessagesTemp.length -1; i >= 0; i-- ){
                if (historyMessagesTemp[i] != null)
                    updateHistory(historyMessagesTemp[i]);
            }
        }
    }

    private void setupViewDependencies(){
        llHistoryView = findViewById(R.id.llHistoryView);
        imgFirst = findViewById(R.id.imgFirst);
        imgSecond = findViewById(R.id.imgSecond);
        Button rollBtn = findViewById(R.id.btnRoll);
        rollBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                rollDices();
            }
        });
        Button clearBtn = findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearHistory();
            }
        });
    }

    private void rollDices(){
        int number1 = random.nextInt(NUMBER_OF_DICE_SIDES) + 1;
        int number2 = random.nextInt(NUMBER_OF_DICE_SIDES) + 1;

        setImages(number1, number2);

        String historyText = historyHelper.generateHistoryText(number1, number2);

        updateHistory(historyText);
    }

    private void setImages(int number1, int number2) {
        imgOneId = imageHelper.getImageId(number1); //For state management
        imgFirst.setImageResource(imgOneId);
        imgTwoId = imageHelper.getImageId(number2); //For state management
        imgSecond.setImageResource(imgTwoId);
    }

    private void updateHistory(String historyText) {
        for (int i = NUMBER_OF_HISTORY_ITEMS - 1; i > 0; i--) {
            HistoryTextViews[i] = HistoryTextViews[i-1];
            historyMessages[i] = historyMessages[i-1];
        }

        HistoryTextViews[0] = new TextView(this);
        HistoryTextViews[0].setText(historyText);
        historyMessages[0] = historyText;
        HistoryTextViews[0].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        llHistoryView.removeAllViews();
        for(int i = 0; i < NUMBER_OF_HISTORY_ITEMS; i++){
            if (HistoryTextViews[i] != null) {
                llHistoryView.addView(HistoryTextViews[i]);
            }
        }
    }

    private void clearHistory(){
        for(int i = 0; i < NUMBER_OF_HISTORY_ITEMS; i++){
            HistoryTextViews[i] = null;
            historyMessages[i] = null; //State management
        }
        llHistoryView.removeAllViews();
    }


}
