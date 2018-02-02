package com.example.jpoulin.jcpoulin_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when user taps "add" button */
    public void addSub(View view) {
        Intent intent = new Intent(this, AddNewSub.class);
        startActivity(intent);
    }
}
