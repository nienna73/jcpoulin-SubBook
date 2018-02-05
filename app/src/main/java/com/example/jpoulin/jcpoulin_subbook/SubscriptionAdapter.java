package com.example.jpoulin.jcpoulin_subbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by jpoulin on 2018-02-04.
 * Citing:
 * https://stackoverflow.com/questions/19775527/android-listview-custom-listitem-not-displayed-correctly
 *
 * This is a custom adapter for properly displaying the subscription items in a list view
 *
 */

public class SubscriptionAdapter extends ArrayAdapter<Subscription> {
    private ArrayList<Subscription> subs;
    public LayoutInflater vi;
    private Context context;

    public SubscriptionAdapter(Context context, int textViewResourceId, ArrayList<Subscription> subs) {
        super(context, textViewResourceId, subs);
        this.subs = subs;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }

        Subscription sub = subs.get(position);
        if (sub != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView amount = (TextView) v.findViewById(R.id.amount);

            if (name != null) {
                name.setText(sub.getName());
            }

            if (date != null) {
                String dateStr = sub.getDate().toString();
                date.setText(dateStr);
            }

            if (amount != null) {
                /* For displaying using the currency format:
                   https://stackoverflow.com/questions/13791409/java-format-double-value-as-dollar-amount
                 */
                Float amountStr = sub.getAmount();
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                amount.setText(formatter.format(amountStr));
            }
        }
        return v;
    }

}
