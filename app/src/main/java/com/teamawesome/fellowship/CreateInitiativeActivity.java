package com.teamawesome.fellowship;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInitiativeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

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

        Intent i = new Intent(this, InitiativeActivity.class);
        startActivity(i);

    }

    public boolean validate() {
        boolean valid = true;
        String initiativeTitle = _initiativeTitle.getText().toString();
        String startDateStr = _startDate.getText().toString();
        String endDateStr = _endDate.getText().toString();

        DateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        Date now = new Date();

        // TODO: also check that initiative title isn't already taken
        if (initiativeTitle.isEmpty()) {
            _initiativeTitle.setError("enter an initiative title");
            valid = false;
        } else {
            _initiativeTitle.setError(null);
        }

        Date startDate = null;
        try {
            startDate = sdf.parse(startDateStr);
            if (startDate.compareTo(now) < 0) {
                _startDate.setError("Start date cannot be before the current date.");
                valid = false;
            } else {
                _startDate.setError(null);
            }
        } catch (ParseException e) {
            _startDate.setError("select a start date");
            valid = false;
        }

        try {
            Date endDate = sdf.parse(endDateStr);
            if (startDate != null && endDate.before(startDate)) {
                _endDate.setError("End date cannot be before start date.");
                valid = false;
            } else {
                _endDate.setError(null);
            }
        } catch (ParseException e) {
            _endDate.setError("select an end date");
            valid = false;
        }

        return valid;
    }

    public void onSubmitFailed() {

    }
}
