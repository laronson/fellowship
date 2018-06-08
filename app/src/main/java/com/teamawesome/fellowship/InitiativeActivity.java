package com.teamawesome.fellowship;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InitiativeActivity extends AppCompatActivity {
    public static final String INITIATIVE_ID_PARAM = "initiativeId";
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;
    private Date startDate;
    private Date endDate;

    @BindView(R.id.initiative_title) TextView _initiativeTitle;
    @BindView(R.id.initiative_description) TextView _initiativeDescription;
    @BindView(R.id.starts_in_or_time_remaining) TextView _starts_in_or_time_remaining;
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
                    startDate = documentSnapshot.getDate("startDate");
                    endDate = documentSnapshot.getDate("endDate");

                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    Date now = new Date();
                                    long diff;
                                    if (startDate.after(now)) {
                                        _starts_in_or_time_remaining.setText(R.string.starts_in);
                                        diff = startDate.getTime() - now.getTime();
                                    } else {
                                        _starts_in_or_time_remaining.setText(R.string.time_remaining);
                                        diff = now.getTime() - endDate.getTime();
                                    }
                                    long days = TimeUnit.MILLISECONDS.toDays(diff);
                                    _days.setText(String.format("%d", days));
                                    long hours = TimeUnit.MILLISECONDS.toHours(diff)
                                            - TimeUnit.DAYS.toHours(days);
                                    _hours.setText(String.format("%02d", hours));
                                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) -
                                            TimeUnit.DAYS.toMinutes(days) -
                                            TimeUnit.HOURS.toMinutes(hours);
                                    _minutes.setText(String.format("%02d", minutes));
                                    long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) -
                                            TimeUnit.DAYS.toSeconds(days) -
                                            TimeUnit.HOURS.toSeconds(hours) -
                                            TimeUnit.MINUTES.toSeconds(minutes);
                                    _seconds.setText(String.format("%02d", seconds));
                                }
                            });
                        }
                    }, 0, 1000);
                }
            }
        });
    }
}
