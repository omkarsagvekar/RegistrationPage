package com.example.registrationpage;

import static java.lang.Integer.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.registrationpage.db.MyDbHandler;
import com.example.registrationpage.model.User;

import java.time.Year;
import java.time.YearMonth;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etMonth, etDay, etYear, etEmail;
    private Button btnSubmit;
    private String[] monthStr = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private MyDbHandler myDbHandler;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initObj();
        allListeners();
    }

    private void initObj() {
        etName = findViewById(R.id.et_name);
        etMonth = findViewById(R.id.et_month);
        etDay = findViewById(R.id.et_day);
        etYear = findViewById(R.id.et_year);
        etEmail = findViewById(R.id.et_emailId);
        btnSubmit = findViewById(R.id.btn_submit);

        myDbHandler = new MyDbHandler(MainActivity.this);
        user = new User();
    }

    private void allListeners() {
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    validUsername();
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    validEmail();
                }
            }
        });

        etYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    validYear();
                }
            }
        });
        etDay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    validDay();
                }
            }
        });

        etMonth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    validMonth();
                }
            }
        });

        btnSubmit.setOnClickListener(view -> {
            if (validUsername() && validMonth() && validDay() && validYear() && validEmail()){
                int year = parseInt(etYear.getText().toString());
                int month = monthNum(etMonth.getText().toString().trim());
                int day = parseInt(etDay.getText().toString().trim());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int currentYear = 0;

                    currentYear = Integer.parseInt(String.valueOf(Year.now()));
                    if (Integer.parseInt(etYear.getText().toString()) > currentYear) {
                        etYear.setError("Enter valid year");
                        return;
                    }

                    if (YearMonth.of(year, month).isValidDay(day)) {
                        postDataToSqlite();
                    } else {
                        etDay.setError("Invalid day");
                    }
                }
            }
        });
    }

    private void postDataToSqlite() {

        user.setName(etName.getText().toString());
        user.setDateOfBirth(etMonth.getText().toString().trim() + " " + etDay.getText().toString().trim() + getSuffix() + " " + etYear.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        myDbHandler.addAllUsers(user);
        emptyInputFields();
        startActivity(new Intent(MainActivity.this, UserListTable.class));
    }

    private String getSuffix() {
        int day = Integer.parseInt(etDay.getText().toString().trim());
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return"th";
        }
    }

    private void emptyInputFields() {
        etName.setText("");
        etMonth.setText("");
        etDay.setText("");
        etYear.setText("");
        etEmail.setText("");
    }

    private boolean validMonth() {
        String month = etMonth.getText().toString().trim();
        if (month.equals("")){
            etMonth.setError("Month should not be empty");
            return false;
        }else{
            if (!(month.equalsIgnoreCase(monthStr[0]) || month.equalsIgnoreCase(monthStr[1]) || month.equalsIgnoreCase(monthStr[2])
                    || month.equalsIgnoreCase(monthStr[3])|| month.equalsIgnoreCase(monthStr[4])|| month.equalsIgnoreCase(monthStr[5])
                    || month.equalsIgnoreCase(monthStr[6])|| month.equalsIgnoreCase(monthStr[7])|| month.equalsIgnoreCase(monthStr[8])
                    || month.equalsIgnoreCase(monthStr[9])|| month.equalsIgnoreCase(monthStr[10])|| month.equalsIgnoreCase(monthStr[11]))){
                etMonth.setError("Enter valid month");
                return false;
            }else{
                etMonth.setError(null);
                return true;
            }
        }
    }

    private boolean validYear() {
        if (etYear.getText().toString().equals("")) {
            etYear.setError("Year should not be empty");
            return false;
        }
        etYear.setError(null);
        return true;
    }

    private int monthNum(String month){
        if (month.equalsIgnoreCase(monthStr[0])){
            return 1;
        }else if (month.equalsIgnoreCase(monthStr[1])){
            return 2;
        }else if (month.equalsIgnoreCase(monthStr[2])){
            return 3;
        }else if (month.equalsIgnoreCase(monthStr[3])){
            return 4;
        }else if (month.equalsIgnoreCase(monthStr[4])){
            return 5;
        }else if (month.equalsIgnoreCase(monthStr[5])){
            return 6;
        }else if (month.equalsIgnoreCase(monthStr[6])){
            return 7;
        }else if (month.equalsIgnoreCase(monthStr[7])){
            return 8;
        }else if (month.equalsIgnoreCase(monthStr[8])){
            return 9;
        }else if (month.equalsIgnoreCase(monthStr[9])){
            return 10;
        }else if (month.equalsIgnoreCase(monthStr[10])){
            return 11;
        }else if (month.equalsIgnoreCase(monthStr[11])){
            return 12;
        }
        return 0;
    }

    private boolean validDay() {
        if (etDay.getText().toString().equals("")) {
            etDay.setError("Invalid day");
            return false;
        }
        if (etDay.getText().toString().equals("0")) {
            etDay.setError("Invalid day");
            return false;
        }
        etDay.setError(null);
        return true;
    }

    private boolean validEmail() {
        String emailText = etEmail.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            etEmail.setError("Invalid Email Id");
            return false;
        }else if (etEmail.getText().toString().equals("")){
            etEmail.setError("Email should not be empty");
            return false;
        }else{
            return true;
        }

    }

    private boolean validUsername() {
        if(etName.getText().toString().equals("")){
            etName.setError("Invalid Name");
           return false;
        }else if (etName.getText().toString().equals("")){
            etName.setError("Username should not be empty");
            return false;
        }else{
            etName.setError(null);
            return true;
        }
    }

}