package com.example.jpoulin.jcpoulin_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by jpoulin on 2018-02-01.
 */

public class AddNewSub extends Activity {

    private static final String FILENAME = "data.sav";
    private EditText name;
    private EditText amount;
    private EditText date;
    private EditText comment;
    private ListView subsView;

    private ArrayList<Subscription> subsList = new ArrayList<Subscription>();
    private SubscriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub);
        adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);

        /* Get parent view info taken from:
        https://stackoverflow.com/questions/17879743/get-parents-view-from-a-layout
         */

       // RelativeLayout parent = (RelativeLayout) ((ViewGroup) this.getParent()).getParent();

        name = (EditText) findViewById(R.id.name);
        amount = (EditText) findViewById(R.id.amount);
        date = (EditText) findViewById(R.id.date);
        comment = (EditText) findViewById(R.id.comments);
        Button saveButton = (Button) findViewById(R.id.save);
        subsView = (ListView) findViewById(R.id.allSubsList);


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                setResult(RESULT_OK);
                String nameStr = name.getText().toString();
                String commStr = comment.getText().toString();

                String dateStr = date.getText().toString();
                String amountStr = amount.getText().toString();

                /* Date conversion taken from:
                   https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
                 */

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date dateFormatted = new Date();
                try {
                    dateFormatted = format.parse(dateStr);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                /* String to float conversion taken from:
                   https://stackoverflow.com/questions/7552660/java-convert-float-to-string-and-string-to-float
                 */

                Float amountFl = Float.parseFloat(amountStr);


                Subscription newSub = new Subscription(nameStr, dateFormatted, amountFl, commStr);

                subsList.add(newSub);

                adapter.notifyDataSetChanged();

                saveInFile();

                goHome(v);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    private void loadFromFile() {
        ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from a stack overflow answer found in lab
            // https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-24

            Type listType = new TypeToken<ArrayList<NewSub>>(){}.getType();

            subsView = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            subsList = new ArrayList<Subscription>();
        }  /* catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */

    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(subsView, out);

            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* Called when save button is pressed */
    public void goHome(View view) {
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }
}
