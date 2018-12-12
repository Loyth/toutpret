package toutpret.isep.com.toutpret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.models.User;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();

        // writeNewUser("Thomas Loyau", "thomas.loyau@isep.fr");

        setListeners();
    }

    private void setListeners() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("bonjour-aurevoir", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                User user = dataSnapshot.getValue(User.class);

                // Log.v("bonjour-aurevoir", user.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.v("bonjour-aurevoir", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                User newUser = dataSnapshot.getValue(User.class);
                String userKey = dataSnapshot.getKey();

                Log.v("bonjour-aurevoir", userKey);
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("bonjour-aurevoir", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String userKey = dataSnapshot.getKey();
                Log.v("bonjour-aurevoir", userKey);
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("bonjour-aurevoir", "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                User movedUser = dataSnapshot.getValue(User.class);
                String userKey = dataSnapshot.getKey();
                Log.v("bonjour-aurevoir", userKey);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("bonjour-aurevoir", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.getReference("users").addChildEventListener(childEventListener);
    }

    private void writeNewUser(String name, String email) {
        DatabaseReference usersRef = mDatabase.getReference("users").push();

        User user = new User(name, email);

        usersRef.setValue(user);

        Log.v("bonjour-aurevoir", "User added");
    }
}
