package com.example.giov13.veladoras.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.classes.orderClass;

import java.util.List;

public class AdapterViewClientSimple extends BaseAdapter {

    private Context context;
    private int layout;
    private List<orderClass> orders;
    private orderClass order;
    public AdapterViewClientSimple(Context context, int layout, List<orderClass> orders)
    {
        this.context=context;
        this.layout=layout;
        this.orders=orders;
    }
    @Override
    public int getCount() {
        return this.orders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.orders.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(this.context);
            convertView=layoutInflater.inflate(R.layout.adapter_listview_view,null);
            holder=new ViewHolder();
            holder.textViewClient=convertView.findViewById(R.id.textViewShowClient);
            holder.textViewDate=convertView.findViewById(R.id.textViewDate);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        order=orders.get(position);
        String client=order.getNameOrder();
        String date=order.getDate();
        holder.textViewClient.setText(client);
        holder.textViewDate.setText(date);
        return convertView;
    }
    static class ViewHolder
    {
        private TextView textViewClient,textViewDate;
    }
}
