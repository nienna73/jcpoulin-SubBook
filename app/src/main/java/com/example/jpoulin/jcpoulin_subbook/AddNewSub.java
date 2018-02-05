package com.example.jpoulin.jcpoulin_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by jpoulin on 2018-02-01.
 *
 * This activity states what happens when a new subscription is added.
 * It loads the add_sub screen and gets then stores user input
 *
 */

public class AddNewSub extends AppCompatActivity {

    private static final String FILENAME = "savefile.json";
    private EditText name;
    private EditText amount;
    private EditText date;
    private EditText comment;
    private ListView subsView;
    public Subscription newSub;

    private ArrayList<Subscription> subsList;
    private SubscriptionAdapter adapter;

    AddNewSub() {

    }

    public AddNewSub(Subscription newSub) {
        // In order to make newSub accessible outside of AddNewSub:
        this.newSub = newSub;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub);
        adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);

        name = (EditText) findViewById(R.id.name);
        amount = (EditText) findViewById(R.id.amount);
        date = (EditText) findViewById(R.id.date);
        comment = (EditText) findViewById(R.id.comments);
        Button saveButton = (Button) findViewById(R.id.save);


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                setResult(RESULT_OK);
                String nameStr = name.getText().toString();
                String commStr = comment.getText().toString();

                String dateStr = date.getText().toString();
                String amountStr = amount.getText().toString();

                /* String to float conversion taken from:
                   https://stackoverflow.com/questions/7552660/java-convert-float-to-string-and-string-to-float
                 */

                Float amountFl = Float.parseFloat(amountStr);

                newSub = new Subscription(nameStr, dateStr, amountFl, commStr);

                if (subsList != null) {
                    subsList.add(newSub);
                } else {
                    subsList = new ArrayList<Subscription>();
                    subsList.add(newSub);
                }

                adapter.notifyDataSetChanged();

                saveInFile();

                goHome(v);

            }

        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(subsList, out);

            out.flush();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        ArrayList<Subscription> subs = new ArrayList<Subscription>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-24

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            subsList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            subsList = new ArrayList<Subscription>();
        }
    }


    public void goHome(View view) {
        /* Called when save button is pressed
         * Runs MainActivity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }

    public Subscription getNewSub() { return this.newSub; }

}
