package com.example.giov13.veladoras.Activities;



import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giov13.veladoras.DataBase.ordersOpenHelper;
import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.classes.orderClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    //valores
    private EditText editName,editDirection,editQuantity,editEsoterica;
    private Spinner valueCandles,valueEssence,valueTypes;
    private ArrayAdapter<CharSequence> spinnerCandles,spinnerEssence,spinnerTypes;
    private Button btnMake;
    private TextView textViewEssence,textViewTypes,textEsoterica;
    private boolean isFirst=true,isEsoterica=false;
    private AlertDialog.Builder alertDialogAgree;
    private boolean isActive=false;
    private int isFirstClick=1;
    private int verify=0;
    private Intent intent;
    private ordersOpenHelper orderOH;
    //Valores datos a ingresar
    private String client,direction,candle,essence,types,quantityV,esoterica,fecha;
    private int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //Instancias
        editName=findViewById(R.id.editTextOrder);
        editDirection=findViewById(R.id.editTextDirection);
        editQuantity=findViewById(R.id.editTextNumber);
        editEsoterica=findViewById(R.id.editTextEsoterica);
        valueCandles=findViewById(R.id.spinnerCandlesTypes);
        valueEssence=findViewById(R.id.spinnerEssence);
        valueTypes=findViewById(R.id.spinnerTypes);
        textViewEssence=findViewById(R.id.textViewEssence);
        textViewTypes=findViewById(R.id.textViewType);
        textEsoterica=findViewById(R.id.textViewEsoterica);
        btnMake=findViewById(R.id.buttonMakeOrder);
        orderOH=new ordersOpenHelper(getApplicationContext());
        //Adapters
        this.spinnerCandles=ArrayAdapter.createFromResource(this,R.array.optionscandles,R.layout.spinner_item_candles);
        this.spinnerEssence=ArrayAdapter.createFromResource(this,R.array.optionsEssence,R.layout.spinner_item_candles);
        this.spinnerTypes=ArrayAdapter.createFromResource(this,R.array.candleTypes,R.layout.spinner_item_candles);
        //Establecemos los adapters
        valueCandles.setAdapter(spinnerCandles);
        valueEssence.setAdapter(spinnerEssence);
        valueTypes.setAdapter(spinnerTypes);
        //Click item spinnerCandleTypes
        valueCandles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                other(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAgree();
            }
        });

    }
//Crea el ícono de ver pedidos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_options,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.view_order:
                intent=new Intent(OrderActivity.this,PopUpViewActivity.class);
                intent.putExtra("value_first",isFirstClick);
                intent.putExtra("value_name",client);
                intent.putExtra("value_date",fecha);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Método para verificar selección de vaso 100
    public void other(int position)
    {
        if(isFirst)
        {
            isFirst=false;
        }else{
            if(position==6)
            {
                show();
            }else
            {
                if(position==7)
                {
                    showEso();
                }else {
                    notShow();
                }
            }
        }
    }
    public void show()
    {
        valueEssence.setVisibility(View.VISIBLE);
        valueTypes.setVisibility(View.VISIBLE);
        textViewEssence.setVisibility(View.VISIBLE);
        textViewTypes.setVisibility(View.VISIBLE);
        textEsoterica.setVisibility(View.GONE);
        editEsoterica.setVisibility(View.GONE);
        isActive=true;
    }
    public void notShow()
    {
        valueEssence.setVisibility(View.GONE);
        valueTypes.setVisibility(View.GONE);
        textViewEssence.setVisibility(View.GONE);
        textViewTypes.setVisibility(View.GONE);
        textEsoterica.setVisibility(View.GONE);
        editEsoterica.setVisibility(View.GONE);
    }
    public void showEso()
    {
        textEsoterica.setVisibility(View.VISIBLE);
        editEsoterica.setVisibility(View.VISIBLE);
        valueEssence.setVisibility(View.GONE);
        valueTypes.setVisibility(View.GONE);
        textViewEssence.setVisibility(View.GONE);
        textViewTypes.setVisibility(View.GONE);
        isEsoterica=true;

    }

    //Método alertDialogo confirmación pedido
    public void alertDialogAgree()
    {
        this.alertDialogAgree=new AlertDialog.Builder(OrderActivity.this);
        alertDialogAgree.setTitle("Confirmación").
                setMessage("¿Hacer pedido?").
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertOrder();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OrderActivity.this,"El pedido ha sido cancelado",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = alertDialogAgree.create();
        alert.show();
    }
    public void insertOrder()
    {
        //checar
        giveData();
        if(isActive)
        {
            if(!client.isEmpty() && !quantityV.isEmpty() && !candle.equals("Seleccione") && !essence.equals("Seleccione") && !types.equals("Seleccione"))
            {
                orderOH.insertData(new orderClass(client,direction,candle,quantityV,essence,types,fecha,""));
                Toast.makeText(OrderActivity.this,"El pedido ha sido guardado satisfactoriamente",Toast.LENGTH_LONG).show();
                isActive=false;
                if(isFirstClick==1)
                    isFirstClick=0;
                deleteData();
            }else
            {
                Toast.makeText(OrderActivity.this,"Favor de llenar todos los campos correspondientes",Toast.LENGTH_LONG).show();
            }
        }else
        {
            if(isEsoterica)
            {
                if(!client.isEmpty() && !quantityV.isEmpty() && !candle.equals("Seleccione") && !esoterica.isEmpty())
                {
                    orderOH.insertData(new orderClass(client,direction,candle,quantityV,"","",fecha,esoterica));
                    Toast.makeText(OrderActivity.this,"El pedido ha sido guardado satisfactoriamente",Toast.LENGTH_LONG).show();
                    isEsoterica=false;
                    if(isFirstClick==1)
                        isFirstClick=0;
                    deleteData();
                }else
                {
                    Toast.makeText(OrderActivity.this,"Favor de llenar todos los campos correspondientes",Toast.LENGTH_LONG).show();
                }
            }else
            {
                if(!client.isEmpty() && !quantityV.isEmpty() && !candle.equals("Seleccione"))
                {
                    orderOH.insertData(new orderClass(client,direction,candle,quantityV,"","",fecha,""));
                    Toast.makeText(OrderActivity.this,"El pedido ha sido guardado satisfactoriamente",Toast.LENGTH_LONG).show();
                    if(isFirstClick==1)
                        isFirstClick=0;
                    deleteData();
                }else
                {
                    Toast.makeText(OrderActivity.this,"Favor de llenar todos los campos correspondientes",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void giveData()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        client=editName.getText().toString();
        direction=editDirection.getText().toString();
        candle=valueCandles.getSelectedItem().toString();
        quantityV=editQuantity.getText().toString();
        essence=valueEssence.getSelectedItem().toString();
        types=valueTypes.getSelectedItem().toString();
        esoterica=editEsoterica.getText().toString();
        fecha = dateFormat.format(date);
    }
    public void deleteData()
    {
        editName.setText(client);
        editDirection.setText(direction);
        valueCandles.setSelection(0);
        editQuantity.setText("");
        editEsoterica.setText("");
        valueEssence.setSelection(0);
        valueTypes.setSelection(0);
    }
}
