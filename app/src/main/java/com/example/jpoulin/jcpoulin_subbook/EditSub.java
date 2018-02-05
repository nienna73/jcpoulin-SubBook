package com.example.jpoulin.jcpoulin_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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


/**
 * Created by jpoulin on 2018-02-05.
 *
 * This class allows the user to edit an existing subscription.
 * It opens the add_sub screen and fills it in with the existing subscription information
 * Upon clicking save, the pre-existing subscription is replaced with the edited subscription
 *
 */

public class EditSub extends AppCompatActivity {

    private static final String FILENAME = "savefile.json";
    private String passedName;
    private String passedDate;
    private String passedAmount;
    private String passedComments;
    private String listIndex;

    private TextView nameField;
    private TextView amountField;
    private TextView dateField;
    private TextView commentsField;

    private Integer passedIndex = 0;
    private ArrayList<Subscription> subsList = new ArrayList<Subscription>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub);
        loadFromFile();
        updateFields();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateFields() {

        Intent intent = getIntent();
        passedName = intent.getStringExtra("passedName");
        passedDate = intent.getStringExtra("passedDate");
        passedAmount = intent.getStringExtra("passedAmount");
        passedComments = intent.getStringExtra("passedComments");
        passedIndex = intent.getIntExtra("passedIndex", 0);

        nameField = (EditText) findViewById(R.id.name);
        amountField = (EditText) findViewById(R.id.amount);
        dateField = (EditText) findViewById(R.id.date);
        commentsField = (EditText) findViewById(R.id.comments);
        Button saveButton = (Button) findViewById(R.id.save);

        nameField.setText(passedName);
        amountField.setText(passedAmount);
        dateField.setText(passedDate);
        commentsField.setText(passedComments);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editedName = nameField.getText().toString();
                String editedAmount = amountField.getText().toString();
                String editedDate = dateField.getText().toString();
                String editedComments = commentsField.getText().toString();

                Float amountFl = Float.parseFloat(editedAmount);

                Subscription newSub = new Subscription(editedName, editedDate, amountFl, editedComments);

                /* For replacing an element at a given index:
                   https://docs.oracle.com/javase/7/docs/api/java/util/List.html#set%28int,%20E%29
                 */

                subsList.set(passedIndex, newSub);

                saveInFile();

                goHome(view);

            }
        });

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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

    public void goHome(View view) {
        /* Called when save button is pressed */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
