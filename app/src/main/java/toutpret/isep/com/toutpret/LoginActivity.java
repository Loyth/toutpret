package toutpret.isep.com.toutpret;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Se connecter");

        Button button_SignUp = findViewById(R.id.button_SignUp);
        button_SignUp.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent SignUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(SignUpActivity);
            }
        });
    }
}
