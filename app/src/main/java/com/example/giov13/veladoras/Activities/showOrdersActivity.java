package com.example.giov13.veladoras.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.giov13.veladoras.DataBase.ordersOpenHelper;
import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.classes.orderClass;

import java.util.List;

public class showOrdersActivity extends AppCompatActivity {

    private TextView txtClient,txtDate,txtOrders;
    private List<orderClass> orders;
    private String name,date,sentence,sentenceFinal="";
    private ordersOpenHelper orderOH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);
        //Instancia
        orderOH=new ordersOpenHelper(getApplicationContext());
        txtClient=findViewById(R.id.textViewName);
        txtDate=findViewById(R.id.textViewDateShow);
        txtOrders=findViewById(R.id.textViewShowOrders);
        name=getIntent().getExtras().getString("name");
        date=getIntent().getExtras().getString("date");
        showOrders();
    }
    private void showOrders()
    {
        txtClient.setText("Cliente: "+name);
        txtDate.setText("Fecha: "+date);
        orders=orderOH.findOrder(name,date);
        for(int i=0;i<orders.size();i++)
        {
                sentence=orders.get(i).toStringM();
                sentenceFinal=sentenceFinal+sentence;
        }
        txtOrders.setText(sentenceFinal);
    }
}
