package com.example.jpoulin.jcpoulin_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends Activity {

    private static final String FILENAME = "savedata.json";
    private EditText bodyText;
    private ListView allSubs;
    private ListView subsView;

    private ArrayList<Subscription> subsList = new ArrayList<Subscription>();
    private SubscriptionAdapter adapter;



    /**
     * States what should be done when a new subscription is created.
     *
     * @param savedInstanceState holds the last saved state of the app
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);
        subsView = findViewById(R.id.allSubsList);


        AddNewSub testSubSuper = new AddNewSub();
        Subscription testSub = testSubSuper.getNewSub();

        if (testSub != null) {
            subsList.add(testSub);
        }

        loadFromFile();
        adapter.notifyDataSetChanged();

    }


    /** Called when user taps "add" button */
    public void addSub(View view) {
        Intent intent = new Intent(this, AddNewSub.class);
        startActivity(intent);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);
        subsView.setAdapter(adapter);

    }

    private void loadFromFile() {
        ArrayList<String> subs = new ArrayList<String>();

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from a stack overflow answer found in lab
            // https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-24

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            subsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
