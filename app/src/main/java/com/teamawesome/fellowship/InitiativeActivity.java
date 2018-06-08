package com.teamawesome.fellowship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InitiativeActivity extends AppCompatActivity {
    public static final String INITIATIVE_ID_PARAM = "initiativeId";
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @BindView(R.id.initiative_title) TextView _initiativeTitle;
    @BindView(R.id.initiative_description) TextView _initiativeDescription;
    @BindView(R.id.days) TextView _days;
    @BindView(R.id.hours) TextView _hours;
    @BindView(R.id.minutes) TextView _minutes;
    @BindView(R.id.seconds) TextView _seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative);
        ButterKnife.bind(this);
        String initiativeId = getIntent().getStringExtra(INITIATIVE_ID_PARAM);
        if (initiativeId == null || initiativeId.isEmpty()) {
            Toast toast = Toast.makeText(
                    getApplicationContext(), "InitiativeId was empty", Toast.LENGTH_LONG);
            toast.show();
        }
        mDocRef = FirebaseFirestore.getInstance().document("initiatives/" + initiativeId);
        populateTextViews();
    }

    private void populateTextViews() {
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    _initiativeTitle.setText(documentSnapshot.getString("title"));
                    _initiativeDescription.setText(documentSnapshot.getString("description"));
                }
            }
        });
    }
}
