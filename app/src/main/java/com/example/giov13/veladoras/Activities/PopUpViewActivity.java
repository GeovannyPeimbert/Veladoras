package com.example.giov13.veladoras.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.giov13.veladoras.DataBase.ordersOpenHelper;
import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.classes.orderClass;

import java.util.List;

public class PopUpViewActivity extends AppCompatActivity {
    //variables
    private TextView txtShowOrder;
    private int recovered_value;
    private String recovered_date,recovered_name;
    private List<orderClass> orders;
    private ordersOpenHelper orderOH;
    private String sentence,sentenceFinal="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_view);
        //Traemos datos de OrderActivity
        recovered_value=getIntent().getExtras().getInt("value_first");
        recovered_name=getIntent().getExtras().getString("value_name");
        recovered_date=getIntent().getExtras().getString("value_date");
        //instancias
        txtShowOrder=findViewById(R.id.textViewSeeOrder);
        orderOH=new ordersOpenHelper(getApplicationContext());
        int height,width;
        //Medidas de popup, con estas líneas se manda a traer la medida original del activity
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //instanciamos las variables
        height=displayMetrics.heightPixels;
        width=displayMetrics.widthPixels;
        //establecemos las medidas que queremos que el activity tenga, en este caso 85% de la medida actual de ancho y 50% de la medida actual de altura
        getWindow().setLayout((int)(width * 0.85),(int)(height * 0.5));
        //verificamos que texto se le pondrá al textView de los pedidos
        setText();

    }
    public void setText()
    {
            if(recovered_value==1)
            {
                txtShowOrder.setText("Aun no hay pedidos realizados");
            }else
            {
                orders=orderOH.findOrder(recovered_name,recovered_date);
                for(int i=0;i<orders.size();i++)
                {
                    sentence=orders.get(i).toStringM();
                    sentenceFinal=sentenceFinal+sentence;

                }
                txtShowOrder.setText(sentenceFinal);
            }
    }
}
