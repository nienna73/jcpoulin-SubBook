package com.example.jpoulin.jcpoulin_subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

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
import java.util.Date;
import java.util.Iterator;

/**
 * Created by jpoulin on 2018-02-04.
 */

public class InspectSub extends AppCompatActivity {

    private static final String FILENAME = "savedata.json";
    private String name;
    private String date;
    private String amount;
    private String comments;
    private String listIndex;

    private Integer passed = 0;

    private ArrayList<Subscription> subsList = new ArrayList<Subscription>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspect_sub);
        loadFromFile();
        updateInfo();

        Button editButton = (Button) findViewById(R.id.edit);
        Button deleteButton = (Button) findViewById(R.id.delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* For list iteration and removal:
                https://stackoverflow.com/questions/16084279/trying-to-remove-an-object-with-list-iterator
                 */
                Iterator itr = subsList.iterator();
                while (itr.hasNext()) {
                    Subscription x = (Subscription) itr.next();
                    if (x.getName().equals(name)) {
                        itr.remove();
                    }
                }
                saveInFile();
                Intent intent = new Intent(InspectSub.this, MainActivity.class);
                Toast.makeText(InspectSub.this, "You've deleted " + name, Toast.LENGTH_LONG ).show();
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSub();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateInfo() {
        MainActivity position = new MainActivity();
        passed = getIntent().getIntExtra(listIndex, 0);
        SubscriptionAdapter adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);
        Subscription testSub = subsList.get(passed);

        name = testSub.getName();
        date = testSub.getDate().toString();
        amount = testSub.getAmount().toString();
        comments = testSub.getComment();

        TextView nameTxtVw = findViewById(R.id.name_display);
        nameTxtVw.setText("Name: " + name);

        TextView dateTxtVw = findViewById(R.id.date_display);
        dateTxtVw.setText("Date started: " + date);

        TextView amountTxtVw = findViewById(R.id.amount_display);
        amountTxtVw.setText("Amount: " + amount);

        TextView commentsTxtVw = findViewById(R.id.comments_display);
        commentsTxtVw.setText("Comments: " + comments);
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
            // check file add method and sub save method
            // either overwriting previous one or only loading last-saved on

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

    public void editSub() {
        if (comments == null) {
            comments = " ";
        }

        /* For passing more than one element between intents:
           https://stackoverflow.com/questions/8452526/android-can-i-use-putextra-to-pass-multiple-values
         */

        Bundle extras = new Bundle();
        extras.putString("passedName", name);
        extras.putString("passedDate", date);
        extras.putString("passedAmount", amount);
        extras.putString("passedComments", comments);
        extras.putInt("passedIndex", passed);
        Intent intent = new Intent(InspectSub.this, EditSub.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
