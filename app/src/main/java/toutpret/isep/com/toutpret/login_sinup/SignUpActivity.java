package toutpret.isep.com.toutpret.login_sinup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputPasswordConfirmation, inputPhoneNumber, inputFirstName, inputLastName;
    private FirebaseAuth auth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("S'inscrire");

        auth = FirebaseAuth.getInstance();

        Button button_SignUp = findViewById(R.id.button_Inscription);

        inputEmail = findViewById(R.id.text_email);
        inputPassword = findViewById(R.id.text_mdp);
        inputPasswordConfirmation = findViewById(R.id.text_mdp_confirmation);

        inputPhoneNumber = findViewById(R.id.text_telephone);
        inputFirstName = findViewById(R.id.text_prenom);
        inputLastName = findViewById(R.id.text_nom);


        button_SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                String passwordConfirmation = inputPasswordConfirmation.getText().toString().trim();

                final String lastName = inputLastName.getText().toString().trim();
                final String firstName = inputFirstName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

                String MobilePattern = "[0-9]{10}";


                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Entrez une adresse mail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Entrez une adresse mail valide !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre nom !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre prénom !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!inputPhoneNumber.getText().toString().matches(MobilePattern)) {

                    Toast.makeText(getApplicationContext(), "Verifiez votre numéro de téléphone", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Entrez un numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Entrez un mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirmation)) {
                    Toast.makeText(getApplicationContext(), "Les deux mots de passe ne sont pas identiques !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mot de passe trop court, entrez 6 caractères au minimum !", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Cette adresse mail existe déjà !",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    userID = auth.getUid();

                                    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users").push();

                                    User user = new User(userID, email, firstName, lastName, phone, "client");

                                    usersDatabase.setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Votre compte a bien été créé !", Toast.LENGTH_SHORT).show();

                                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(login);
                                    finish();
                                }
                            }
                        });
            }
        });
    }


}