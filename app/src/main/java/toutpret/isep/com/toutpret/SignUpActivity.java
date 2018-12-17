package toutpret.isep.com.toutpret;


import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsic;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputPasswordConfirmation, inputPhoneNumber, inputFirstName, inputLastName;
    private FirebaseAuth auth;
    private String userID;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        auth = FirebaseAuth.getInstance();

        Button button_SignUp = (Button) findViewById(R.id.button_Inscription);

        inputEmail = (EditText) findViewById(R.id.text_email);
        inputPassword = (EditText) findViewById(R.id.text_mdp);
        inputPasswordConfirmation = findViewById(R.id.text_mdp_confirmation);

        inputPhoneNumber = (EditText) findViewById(R.id.text_telephone);
        inputFirstName = findViewById(R.id.text_prenom);
        inputLastName = findViewById(R.id.text_nom);


        button_SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String passwordConfirmation = inputPasswordConfirmation.getText().toString().trim();

                final String lastName = inputLastName.getText().toString().trim();
                final String firstName = inputFirstName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();


                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (!password.equals(passwordConfirmation)) {
                    Toast.makeText(getApplicationContext(), "Enter same password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Enter a validate email !", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "You already have an account with this email",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    userID = auth.getUid();

                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").push();


                                    Log.d("logID", "logID " + userID);

                                    User user = new User(userID, email, firstName, lastName, phone);

                                    mDatabase.setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }


}