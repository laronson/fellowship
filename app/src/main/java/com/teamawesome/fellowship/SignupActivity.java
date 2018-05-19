package com.teamawesome.fellowship;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private static final String TAG = "EmailPassword";

    @BindView(R.id.input_name) EditText name;
    @BindView(R.id.input_email) EditText email;
    @BindView(R.id.input_password) EditText password;
    @BindView(R.id.btn_signup) Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(View v){
        System.out.println("FUCK THIS SHIT!!!!!!!!!!!");
        System.out.println(email.getText().toString());
        System.out.println(password.getText().toString());

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            DocumentReference mDocRef = fireStore.document("users/" + name.getText().toString());
                            HashMap<String, Object> dataToSave = new HashMap<>();
                            dataToSave.put("Name", name.getText().toString());
                            mDocRef.set(dataToSave);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
