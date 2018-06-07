package com.teamawesome.fellowship;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInitiativeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private static final String TAG = "EmailPassword";

    @BindView(R.id.input_initiativeTitle) EditText _initiativeTitle;
    @BindView(R.id.startDate) EditText _startDate;
    @BindView(R.id.endDate) EditText _endDate;
    @BindView(R.id.spinner_groupsize) Spinner _groupSizeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_initiative);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        _groupSizeSpinner.setAdapter(adapter);

        _initiativeTitle.setOnFocusChangeListener(new TextFocusChangeListener(getString(R.string.title_instruction), _initiativeTitle));
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(_startDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(_endDate);
        newFragment.show(getSupportFragmentManager(), "datePicker2");
    }

    public void onSubmit(View v) {

    }
}
