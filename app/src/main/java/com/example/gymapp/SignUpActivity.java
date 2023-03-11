package com.example.gymapp;

import static android.text.TextUtils.isEmpty;

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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private TextView txtStatus;
    private Button btnRegister;

    private String URL = "http://10.0.2.2/gymapp_login/login.php";
    private String name, email, password, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        TextView btnBackToLogin = findViewById(R.id.txtBackToLogin);
        inputUsername = findViewById(R.id.txtInputUsername);
        inputEmail = findViewById(R.id.txtInputEmail);
        inputPassword = findViewById(R.id.txtInputPassword);
        inputConfirmPassword = findViewById(R.id.txtInputConfirmPassword);
        txtStatus = findViewById(R.id.txtStatus);
        name = email = password = confirmPassword = "";
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInformation();
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
    private void checkInformation(){
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if(username.isEmpty() || username.length() < 7){
            showError(inputUsername, "Your username is not valid!");
        }
        else if (email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "Email is not valid!");
        }
        else if (password.isEmpty() || password.length() < 7){
            showError(inputPassword, "Password is not valid!");
        }
        else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(inputConfirmPassword, "Confirm password is not valid!");
        }
        else{
            Toast.makeText(this, "Call Registration Method", Toast.LENGTH_SHORT).show();
        }
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
        input.setText(null);
    }

    public void save(View view) {
        name = inputUsername.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        confirmPassword = inputConfirmPassword.getText().toString().trim();
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password dont match", Toast.LENGTH_SHORT).show();
        }else if(!name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        txtStatus.setText("Success registered");
                        btnRegister.setClickable(false);
                    } else if (response.equals("failure")) {
                        txtStatus.setText("Something wrong");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
