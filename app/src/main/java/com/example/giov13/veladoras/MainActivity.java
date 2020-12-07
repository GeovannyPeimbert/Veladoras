package com.example.giov13.veladoras;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.giov13.veladoras.Activities.OrderActivity;
import com.example.giov13.veladoras.Activities.optionsActivity;
import com.example.giov13.veladoras.classes.Options;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Options> options;
    private MyAdapterList myAdapterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        options=getOptions();
        //listview
        listView =findViewById(R.id.listView);
        this.myAdapterList = new MyAdapterList(this,R.layout.layout_item,options);
        listView.setAdapter(myAdapterList);
        //Seleccionar opción del list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent orderActivity = new Intent(MainActivity.this,OrderActivity.class);
                    startActivity(orderActivity);
                }
                if(position==1)
                {
                    startActivity(new Intent(MainActivity.this, optionsActivity.class));
                }
            }
        });
    }

    private List<Options> getOptions() {
        List<Options> listOptions = new ArrayList<Options>() {{
            add(new Options("Pedidos", R.mipmap.view_final_true));
            add(new Options("Ver", R.mipmap.view_true));
        }};
        return listOptions;
    }
}

