package com.example.jpoulin.jcpoulin_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jpoulin on 2018-02-04.
 */

public class SingleListItem extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.list_item);

        TextView subName = (TextView) findViewById(R.id.name);
        String nameStr = subName.getText().toString();

    }
}
