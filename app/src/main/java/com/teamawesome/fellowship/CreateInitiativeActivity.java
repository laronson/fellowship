package com.teamawesome.fellowship;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInitiativeActivity extends AppCompatActivity {

    @BindView(R.id.startDate) EditText _startDate;
    @BindView(R.id.startDate) EditText _endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_initiative);
        ButterKnife.bind(this);
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(_startDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(_endDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
