package com.hari.aag.monthsort;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthSortActivity extends AppCompatActivity
    implements View.OnClickListener {

    private static final String LOG_TAG = MonthSortActivity.class.getSimpleName();
    private static final String PREFS_NAME = MonthSortActivity.class.getSimpleName();

    private static final String IS_ASC_SORT = "isAscSort";

    private String[] ascMonthStr = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };
    private String[] dscMonthStr = {
            "December", "November", "October", "September",
            "August", "July", "June", "May",
            "April", "March", "February", "January"
    };

    private boolean isAscSortBool = true;

    @BindView(R.id.id_asc_btn) Button ascBtn;
    @BindView(R.id.id_dsc_btn) Button dscBtn;
    @BindView(R.id.id_list_month) ListView monthLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_sort);
        ButterKnife.bind(this);

        ascBtn.setOnClickListener(this);
        dscBtn.setOnClickListener(this);

        Log.d(LOG_TAG, "Inside - onCreate");
        readValuesFromPrefs();
        updateValueToUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Inside - onPause");
        saveValuesToPrefs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_asc_btn:
                isAscSortBool = true;
                saveValuesToPrefs();
                updateValueToUI();
                Toast.makeText(this, "You have selected 'Ascending' sort.!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_dsc_btn:
                isAscSortBool = false;
                saveValuesToPrefs();
                updateValueToUI();
                Toast.makeText(this, "You have selected 'Descending' sort.!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateValueToUI(){
        updateListView();
        if (isAscSortBool){
            ascBtn.setClickable(false);
            dscBtn.setClickable(true);

            ascBtn.setEnabled(false);
            dscBtn.setEnabled(true);
        } else {
            ascBtn.setClickable(true);
            dscBtn.setClickable(false);

            ascBtn.setEnabled(true);
            dscBtn.setEnabled(false);
        }
    }

    private void updateListView(){
        ArrayAdapter<String> monthAdapter;
        if (isAscSortBool){
            monthAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, ascMonthStr);
        } else {
            monthAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, dscMonthStr);
        }
        monthLV.setAdapter(monthAdapter);
    }

    private void readValuesFromPrefs(){
        SharedPreferences mySharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        isAscSortBool = mySharedPrefs.getBoolean(IS_ASC_SORT, true);

        Log.d(LOG_TAG, "Values Read from Prefs.");
        dumpPrefValues();
    }

    private void saveValuesToPrefs(){
        SharedPreferences.Editor prefsEditor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        prefsEditor.putBoolean(IS_ASC_SORT, isAscSortBool);
        prefsEditor.commit();

        Log.d(LOG_TAG, "Values Saved to Prefs.");
        dumpPrefValues();
    }

    private void dumpPrefValues(){
        Log.d(LOG_TAG, IS_ASC_SORT + " - " + isAscSortBool);
    }

}
