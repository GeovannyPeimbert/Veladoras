package com.example.giov13.veladoras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giov13.veladoras.classes.Options;

import java.util.List;

public class MyAdapterList extends BaseAdapter
{
    private Context context;
    private int layout;
    private List<Options>names;

    public MyAdapterList(Context context, int layout, List<Options>names)
    {
        this.context=context;
        this.layout=layout;
        this.names=names;
    }

    @Override
    public int getCount() { return this.names.size(); }
    @Override
    public Object getItem(int position) { return this.names.get(position); }
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        view=layoutInflater.inflate(R.layout.layout_item,null);
        final Options giveOptions= (Options) getItem(position);

        TextView textViewListView = view.findViewById(R.id.textViewList);
        ImageView imageViewListView=view.findViewById(R.id.imageViewList);

        textViewListView.setText(giveOptions.getTextOption());
        imageViewListView.setImageResource(giveOptions.getImageOption());
        return view;
    }
}
