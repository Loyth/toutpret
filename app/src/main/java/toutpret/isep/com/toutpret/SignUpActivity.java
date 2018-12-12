package toutpret.isep.com.toutpret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.models.User;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void writeNewUser(String name, String email) {
        DatabaseReference usersRef = mDatabase.getReference("users").push();

        User user = new User(name, email);

        usersRef.setValue(user);

        Log.v("bonjour-aurevoir", "User added");


    }
}

