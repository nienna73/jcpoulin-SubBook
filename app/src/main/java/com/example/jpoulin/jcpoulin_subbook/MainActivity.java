package com.example.jpoulin.jcpoulin_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Jolene Poulin on 2018-01-29
 *
 * The main activity for the app, controls what happens on most clicks, displays subscriptions
 *
 */


public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "savefile.json";
    private EditText bodyText;
    private ListView allSubs;
    private ListView subsView;
    private String listIndex;

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
        loadFromFile();
        Float totalChargeAmt = calcTotalCharge();
        adapter = new SubscriptionAdapter(this, R.layout.list_item, subsList);
        subsView = findViewById(R.id.allSubsList);


        /* For how to add an onClickListener to each list item:
        https://stackoverflow.com/questions/20778181/how-to-make-custom-listview-to-open-other-activities-when-clicking-list-item
         */

        TextView totalCharge = (TextView) findViewById(R.id.totalAmount);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        totalCharge.setText("Total monthly charge: " + formatter.format(totalChargeAmt));

        subsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InspectSub.class);
                intent.putExtra(listIndex, position);
                startActivity(intent);
            }
        });


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

    private float calcTotalCharge() {
        Float total = 0.0f;
        Iterator itr = subsList.iterator();
        while (itr.hasNext()) {
            Subscription x = (Subscription) itr.next();
            total = total + x.getAmount();
        }
        return total;
    }


}
