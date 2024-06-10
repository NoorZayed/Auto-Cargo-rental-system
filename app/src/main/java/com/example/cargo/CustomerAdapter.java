package com.example.cargo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> customers;
    private LayoutInflater inflater;

    public CustomerAdapter(Context context, ArrayList<HashMap<String, String>> customers) {
        this.context = context;
        this.customers = customers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_customer, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.customer_name);
            holder.email = convertView.findViewById(R.id.customer_email);
            holder.phone = convertView.findViewById(R.id.customer_phone);
            holder.details = convertView.findViewById(R.id.customer_details);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> customer = customers.get(position);
        holder.name.setText(customer.get("fullName"));
        holder.email.setText("Email: " + customer.get("email"));
        holder.phone.setText("Phone: " + customer.get("phone"));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.details.getVisibility() == View.GONE) {
                    holder.details.setVisibility(View.VISIBLE);
                } else {
                    holder.details.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView email;
        TextView phone;
        View details;
    }
}
