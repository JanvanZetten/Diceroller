package com.example.diceroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {
    Button btnGoBack;
    Button btnClear;
    ListView historyView;
    HistoryItem[] historyItems;
    boolean cleared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);

        refrenceSetup();
        actionsSetup();
        setInitalState();
    }

    private void refrenceSetup(){
        btnGoBack = findViewById(R.id.btnBack);
        btnClear = findViewById(R.id.btnClear);
        historyView = findViewById(R.id.lstvwHistory);
    }

    private void actionsSetup(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void setInitalState(){
        historyItems = (HistoryItem[]) getIntent().getSerializableExtra("history");
        fillHistoryListView();
    }

    private void fillHistoryListView() {
        historyView.setAdapter(new ArrayAdapter<HistoryItem>(this, android.R.layout.simple_list_item_1, historyItems));
    }

    private void clearList(){
        if (!cleared) {
            cleared = true;
            historyItems = new HistoryItem[0];
            fillHistoryListView();
        }
    }

    private void goBack(){
        Intent rslt_int = new Intent();
        rslt_int.putExtra("cleared", cleared);
        setResult(RESULT_OK, rslt_int);
        finish();
    }
}
