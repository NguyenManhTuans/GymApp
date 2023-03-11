package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private String email, password;
    private String URL = "http://10.0.2.2/gymapp_login/login.php";
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = password = "";

        TextView btnRegister = findViewById(R.id.txtRegister);
        btnLogin=findViewById(R.id.btnLogin);

        inputEmail = findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInformation();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void login(View view){
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        if(!email.equals("") || !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Field empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    private void checkInformation(){
        String email = inputEmail.getText().toString();
//        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

//        if(username.isEmpty() || username.length() < 7){
//            showError(inputUsername, "Your username is not valid!");
//        }else
        if (email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "Email is not valid!");
        }
        else if (password.isEmpty() || password.length() < 7){
            showError(inputPassword, "Password is not valid!");
        }
        else{
            Toast.makeText(this, "Call Registration Method", Toast.LENGTH_SHORT).show();
        }
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}