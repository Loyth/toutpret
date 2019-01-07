package toutpret.isep.com.toutpret.Profil;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.models.User;
import toutpret.isep.com.toutpret.orderpicker.OrderPicker_Commandes;

public class ProfilActivity extends AppCompatActivity {

    private EditText  inputPassword, inputPasswordConfirmation, inputPhoneNumber, inputFirstName, inputLastName;
    private FirebaseAuth auth;
    private String userID;
    private FirebaseDatabase mDatabase;
    private Button button_modifier;
    private TextView inputEmail;
    private String userKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mDatabase = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mon profil");

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.text_email);
        inputPassword = findViewById(R.id.text_mdp);
        inputPasswordConfirmation = findViewById(R.id.text_mdp_confirmation);

        inputPhoneNumber = findViewById(R.id.text_telephone);
        inputFirstName = findViewById(R.id.text_prenom);
        inputLastName = findViewById(R.id.text_nom);
        button_modifier = findViewById(R.id.button_modifier_info);


        button_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String MobilePattern = "[0-9]{10}";


                if (TextUtils.isEmpty(inputLastName.getText())) {
                    Toast.makeText(getApplicationContext(), "Entrez votre nom !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputFirstName.getText())) {
                    Toast.makeText(getApplicationContext(), "Entrez votre prénom !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!inputPhoneNumber.getText().toString().matches(MobilePattern)) {

                    Toast.makeText(getApplicationContext(), "Verifiez votre numéro de téléphone", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(inputPhoneNumber.getText())) {
                    Toast.makeText(getApplicationContext(), "Entrez un numéro de téléphone !", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("users").child(userKey);
                updateData.child("firstName").setValue(inputFirstName.getText().toString());
                updateData.child("lastName").setValue(inputLastName.getText().toString());
                updateData.child("phoneNumber").setValue(inputPhoneNumber.getText().toString());
                }
            }
        });


        userID = auth.getUid();

        getInfo();

    }
    public void getInfo(){

        userID = auth.getUid();

        DatabaseReference myRef = mDatabase.getReference("users");

        myRef.orderByChild("userID").equalTo(userID).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                userKey = dataSnapshot.getKey();
                User user = dataSnapshot.getValue(User.class);
                inputEmail.setText(user.email);
                inputFirstName.setText(user.firstName);
                inputLastName.setText(user.lastName);
                inputPhoneNumber.setText(user.phoneNumber);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }



}
