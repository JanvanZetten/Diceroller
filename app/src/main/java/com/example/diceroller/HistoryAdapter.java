package com.example.diceroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class HistoryAdapter extends ArrayAdapter<HistoryItem> {
    private int Cellresource;
    private Context _context;
    private HistoryItem[] historyItems;
    private ImageHelper imageHelper = new ImageHelper();

    /**
     * Constructor. This constructor will result in the underlying data collection being
     * immutable, so methods such as {@link #clear()} will throw an exception.
     *
     * @param context  The current context.
     * @param objects  The objects to represent in the ListView.
     */
    public HistoryAdapter(Context context, HistoryItem[] objects) {
        super(context, R.layout.history_item, objects);
        Cellresource = R.layout.history_item;
        _context = context;
        historyItems = objects;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater li = (LayoutInflater) _context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(this.Cellresource, null);
        }

        HistoryItem historyItem = historyItems[position];

        TextView historyView = v.findViewById(R.id.txtHistory);
        String time = new SimpleDateFormat("dd/MM HH:mm:ss").format(historyItem.getTimestamp());
        String historyText = "rolled on: " + time + " ";
        historyView.setText(historyText);

        LinearLayout llHistoryItem = v.findViewById(R.id.llHistoryItem);
        llHistoryItem.removeAllViews();
        for (int value: historyItem.getValues()) {
            ImageView imgView = new ImageView(_context);
            imgView.setImageResource(imageHelper.getImageId(value));
            llHistoryItem.addView(imgView);
            setUpDiceImageView(imgView);
        }
        return v;
    }

    private void setUpDiceImageView(ImageView diceImageView) {
        diceImageView.getLayoutParams().height = 50;
        diceImageView.getLayoutParams().width = 50;
        ((ViewGroup.MarginLayoutParams) diceImageView.getLayoutParams()).leftMargin = 3;
        ((ViewGroup.MarginLayoutParams) diceImageView.getLayoutParams()).rightMargin = 3;
    }
}
