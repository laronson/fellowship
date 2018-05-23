package com.teamawesome.fellowship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInitiativeActivity extends AppCompatActivity {

    @BindView(R.id.input_initiativeTitle) EditText _initiativeTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_initiative);
        ButterKnife.bind(this);

        _initiativeTitle.setOnFocusChangeListener(new TextFocusChangeListener(getString(R.string.title_instruction), _initiativeTitle));
    }


}
