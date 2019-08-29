package com.dartmedia.nuansakucing;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dartmedia.nuansakucing.Forum.ForumMainActivity;
import com.dartmedia.nuansakucing.Nearby.MapsActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
//    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        CheckPermission();


        final Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilogin = new Intent(LoginActivity.this, ForumMainActivity.class);
                        startActivity(ilogin);
            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(validate()){
//                    String Username = username.getText().toString();
//                    String Password = password.getText().toString();
//
////                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Username, Password));
////                    if (currentUser != null) {
//                    if (username == null) {
//                        Snackbar.make(login, "Successfully Logged in", Snackbar.LENGTH_LONG).show();
//                        Intent ilogin = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(ilogin);
//                        finish();
//
//                    }else{
//
//                        Snackbar.make(login, "Failed to log in, check your Username or Password", Snackbar.LENGTH_LONG).show();
//
//                    }
//                }
//            }
//        });


    }

    private void initViews(){
        username = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.login);
    }

    public boolean validate() {
        boolean valid;

        String Username = username.getText().toString();
        String Password = password.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(Username).matches()) {
            username.setError("Please enter valid email!");
        } else {
            username.setError(null);
        }

        if (!Password.isEmpty()){
            valid = false;
            password.setError("Please enter valid password");
        }else{
            if (Password.length()>=8){
                valid = true;
                password.setError(null);
            }else {
                valid = true;
                password.setError("8 character Minimal");
            }
        }
        return valid;
    }

    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }
}
