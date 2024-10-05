package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IntroductoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        DatabaseReference a = FirebaseDatabase.getInstance().getReference();

    a.child("dat").child("name").setValue("ton tine");
        new Handler().postDelayed(() -> {
            startActivity(new Intent(IntroductoryActivity.this,MainActivity.class));
            finish();
        },2100);

    }
}
