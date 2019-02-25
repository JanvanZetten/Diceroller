package com.example.diceroller;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);
        btnGoBack = findViewById(R.id.btnBack);
        btnClear = findViewById(R.id.btnClear);

        historyView = findViewById(R.id.lstvwHistory);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fillHistoryListView();

    }

    private void fillHistoryListView() {
        historyItems = (HistoryItem[]) getIntent().getSerializableExtra("history");
        historyView.setAdapter(new ArrayAdapter<HistoryItem>(this, android.R.layout.simple_list_item_1, historyItems));

    }

}
