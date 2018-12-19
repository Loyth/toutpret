package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String userID;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String type;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String email, String firstName, String lastName, String phone, String type) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
        this.type = type;
    }
}
