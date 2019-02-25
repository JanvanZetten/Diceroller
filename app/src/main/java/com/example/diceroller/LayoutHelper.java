package com.example.diceroller;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class LayoutHelper {

    private Context _context;

    public LayoutHelper(Context context) {
        _context = context;
    }

    public int getLayout(){
        if (isPortrait()){
            return (R.layout.activity_main);
        }
        else{
            return (R.layout.activity_main_horisontal);
        }


    }

    private boolean isPortrait(){
        Configuration configuration = _context.getResources().getConfiguration();
        return (configuration.orientation == configuration.ORIENTATION_PORTRAIT);
    }
}
