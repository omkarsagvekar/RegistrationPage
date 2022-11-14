package com.example.registrationpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.registrationpage.db.MyDbHandler;
import com.example.registrationpage.model.User;

import java.util.ArrayList;

public class UserListTable extends AppCompatActivity {

    private static ArrayList<User> arrayList;
    @SuppressLint("StaticFieldLeak")
    private static UserAdapter userAdapter;
    private MyDbHandler myDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list_table);
        initObj();
    }

    private void initObj() {
        arrayList = new ArrayList<>();
        userAdapter = new UserAdapter(UserListTable.this, arrayList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserListTable.this));
        recyclerView.setAdapter(userAdapter);
        myDbHandler = new MyDbHandler(UserListTable.this);
        arrayList.addAll(myDbHandler.getAllUsers());
    }
}