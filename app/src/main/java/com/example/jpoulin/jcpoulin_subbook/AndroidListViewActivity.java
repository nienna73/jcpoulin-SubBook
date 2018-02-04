package com.example.jpoulin.jcpoulin_subbook;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jpoulin on 2018-02-04.
 */

public class AndroidListViewActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] numbers = {"one", "two", "three", "four"};

        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, numbers));
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String num = ((TextView) view).getText().toString();

                Intent intent = new Intent(getApplicationContext(), SingleListItem.class);

                intent.putExtra("number", num);
                startActivity(intent);

            }
        });
    }
    }

