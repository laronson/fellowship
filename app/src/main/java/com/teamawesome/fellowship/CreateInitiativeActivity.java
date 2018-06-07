package com.teamawesome.fellowship;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
    @BindView(R.id.spinner_taskInterval) Spinner _taskIntervalSpinner;
    @BindView(R.id.open_closed_group) RadioGroup _openClosedGroup;


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
        Date startDate = new Date();
        Date endDate = new Date();
        boolean isOpen = true;
        HashMap<String, Object> initiativeData = new HashMap<>();

        if (!validate()) {
            onSubmitFailed();
            return;
        }

        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(_startDate.getText().toString());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(_endDate.getText().toString());

            RadioButton selectedButton = (RadioButton) findViewById(_openClosedGroup.getCheckedRadioButtonId());
            if(selectedButton.getText().toString().equals("Closed Group")) {
                isOpen = false;
            }
        } catch (Exception e) {
            onSubmitFailed();
        }

        initiativeData.put("title", _initiativeTitle.getText().toString());
        initiativeData.put("description", "default for now");
        initiativeData.put("startDate", startDate);
        initiativeData.put("endDate", endDate);
        initiativeData.put("taskInterval", _taskIntervalSpinner.getSelectedItem().toString());
        initiativeData.put("groupSize", _groupSizeSpinner.getSelectedItem().toString());
        initiativeData.put("isOpen", isOpen);

        DocumentReference mDocRef = fireStore.document("initiatives/" + _initiativeTitle.getText().toString());
        mDocRef.set(initiativeData);

    }

    public boolean validate() {
        return true;
    }

    public void onSubmitFailed() {

    }
}
