package com.example.giov13.veladoras.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.classes.orderClass;

import java.util.List;

public class AdapterViewListOptions extends BaseAdapter{
    private Context context;
    private int layout;
    public static List<orderClass> clients;
    private orderClass order;

    public AdapterViewListOptions(Context context,int layout,List<orderClass> clients)
    {
        this.context=context;
        this.layout=layout;
        this.clients=clients;
    }
    @Override
    public int getCount() {
        return this.clients.size();
    }

    @Override
    public Object getItem(int position) {
        return this.clients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final ViewHolder holder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(this.context);
            convertView=layoutInflater.inflate(R.layout.list_view_check,null);
            holder=new ViewHolder();
            holder.textViewDate=convertView.findViewById(R.id.textViewDateOption);
            holder.checkBox=convertView.findViewById(R.id.checkboxRow);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        order=clients.get(position);
        final String client=order.getNameOrder();
        String date=order.getDate();
        holder.checkBox.setText(client);
        holder.textViewDate.setText(date);
        holder.checkBox.setChecked(order.getSelected());
        holder.checkBox.setTag(R.integer.btnplusview,convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Integer pos= (Integer) holder.checkBox.getTag();
               if(clients.get(pos).getSelected())
               {
                   clients.get(pos).setSelected(false);
               }else
               {
                   clients.get(pos).setSelected(true);
               }

            }
        });
        return convertView;
    }

    static class ViewHolder
    {
        private TextView textViewDate;
        private CheckBox checkBox;
    }
}
