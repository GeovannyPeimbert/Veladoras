package com.example.giov13.veladoras.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giov13.veladoras.DataBase.ordersOpenHelper;
import com.example.giov13.veladoras.R;
import com.example.giov13.veladoras.adapters.AdapterViewClientSimple;
import com.example.giov13.veladoras.adapters.AdapterViewListOptions;
import com.example.giov13.veladoras.classes.orderClass;
import com.example.giov13.veladoras.classes.templatePDF;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class optionsActivity extends AppCompatActivity {

    private MenuItem deleteOption,sendOption,deleteAction,sendAction;
    private ListView listViewClient,listViewOptions;
    private List<orderClass> orders;
    private ordersOpenHelper orderOH;
    private AdapterViewClientSimple adapter;
    private AdapterViewListOptions adapterOptions;
    private ArrayList<String> listName,listDate;
    private ArrayList<Integer> listNum;
    private String name,date;
    private templatePDF templatePDF;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static String[] PERMISOS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        listViewClient = findViewById(R.id.listViewSee);
        listViewOptions = findViewById(R.id.listViewOptionSee);
        orderOH = new ordersOpenHelper(getApplicationContext());
        templatePDF=new templatePDF(getApplicationContext());
        //Traemos los datos de la bd
        orders=orderOH.giveAll();
        //Inflamos el adapter
        adapter = new AdapterViewClientSimple(this,R.layout.adapter_listview_view,orders);
        adapterOptions= new AdapterViewListOptions(this,R.layout.list_view_check,orders);
        listViewClient.setAdapter(adapter);
        listViewOptions.setAdapter(adapterOptions);
        //Evento click listViewClientes
        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name=orders.get(position).getNameOrder();
                date=orders.get(position).getDate();
                Intent intentToShow =new Intent(optionsActivity.this,showOrdersActivity.class);
                intentToShow.putExtra("name",name);
                intentToShow.putExtra("date",date);
                startActivity(intentToShow);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuI = getMenuInflater();
        menuI.inflate(R.menu.action_bar_other,menu);
        deleteOption=menu.findItem(R.id.delete);
        sendOption=menu.findItem(R.id.send_pdf);
        deleteAction=menu.findItem(R.id.delete_order);
        sendAction=menu.findItem(R.id.send);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete:
                //Opcion eliminar
                deleteOption.setVisible(false);
                sendOption.setVisible(false);
                deleteAction.setVisible(true);
                switchListViewOptions();
                return true;
            case R.id.send_pdf:
                //Opcion enviar
                deleteOption.setVisible(false);
                sendOption.setVisible(false);
                sendAction.setVisible(true);
                switchListViewOptions();
                return true;
            case R.id.delete_order:
                //Eliminar
                select();
                return true;
            case R.id.send:
                //Enviar
                permission();
                selectSend();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String message="";
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            message="Permiso concedido";
        }else{
            message="Permiso denegado";
        }
        Toast.makeText(optionsActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    public void select()
    {
        giveClients();
    }
    public void selectSend()
    {
        createFilePDF();
    }
    public void switchListViewOptions()
    {
        listViewClient.setVisibility(View.GONE);
        listViewOptions.setVisibility(View.VISIBLE);
    }
    public void switchListViewClient()
    {
        listViewOptions.setVisibility(View.GONE);
        listViewClient.setVisibility(View.VISIBLE);
    }
    public void giveClients()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(optionsActivity.this);
        alert.setTitle("Confirmacion").
                setMessage("¿Eliminar datos?").
                setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOrders();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(optionsActivity.this,"La acción ha sido cancelada",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
    public void deleteOrders()
    {
        boolean isNothing=true;
        this.listName=new ArrayList<>();
        this.listDate=new ArrayList<>();
        this.listNum=new ArrayList<>();
        for(int i =0; i<AdapterViewListOptions.clients.size();i++)
        {
            if(AdapterViewListOptions.clients.get(i).getSelected())
            {
                listName.add(AdapterViewListOptions.clients.get(i).getNameOrder());
                listDate.add(AdapterViewListOptions.clients.get(i).getDate());
                listNum.add(i);
                isNothing=false;
            }
        }
        //Valida si se seleccionó alguna casilla
        if(isNothing)
        {
            Toast.makeText(optionsActivity.this,"No hay ninguna casilla seleccionada",Toast.LENGTH_SHORT).show();
        }else
        {
            orderOH.delete(listName,listDate);
            update();
            Toast.makeText(optionsActivity.this,"El/Los pedidos han sido eliminados satisfactoriamente",Toast.LENGTH_LONG).show();
            deleteAction.setVisible(false);
            deleteOption.setVisible(true);
            sendOption.setVisible(true);
            switchListViewClient();

        }

    }
    public void update()
    {
        int num=listNum.size()-1,numarray;
        numarray=orders.size();
        for(int i = numarray-1;i>=0;i--)
        {
            if(i==listNum.get(num))
            {
                orders.remove(i);
                this.adapterOptions.notifyDataSetChanged();
                this.adapter.notifyDataSetChanged();
                if(num==0)
                {
                    break;
                }else
                {
                    num--;
                }
            }
        }
    }
    public void createFilePDF()
    {
        boolean isNothing = true;
        String[] header={"Cantidad","Veladora","Escencia","Tipo","Detalles"};
        this.listName=new ArrayList<>();
        this.listDate=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        for(int i =0; i<AdapterViewListOptions.clients.size();i++)
        {
            if(AdapterViewListOptions.clients.get(i).getSelected())
            {
                listName.add(AdapterViewListOptions.clients.get(i).getNameOrder());
                listDate.add(AdapterViewListOptions.clients.get(i).getDate());
                isNothing=false;
            }
        }
        if(isNothing)
        {
            Toast.makeText(optionsActivity.this,"No hay ninguna casilla seleccionada",Toast.LENGTH_SHORT).show();

        }else
        {
            ArrayList<String[]> ordersArray;
            ordersArray=orderOH.findInfo(listName,listDate);
            templatePDF.openPDF();
            templatePDF.addMetaData("Clientes","Pedidos","Luz de Luz");
            templatePDF.addTitles("Luz de Luz","Pedidos",fecha);
            templatePDF.addTable(header,ordersArray);
            templatePDF.closeDocument();
            sendText();
        }
    }
    //Método de prueba
    public void sendText()
    {
        //Las primeras dos líneas, son para permitir que se pueda buscar en la memoria externa, en caso de no ponerse, sale error
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String pdf="Pedidos.pdf";
        Uri uri= Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDF/",pdf));
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
        sendIntent.setType("application/pdf");
        startActivity(sendIntent);
    }
    private void permission()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            }else
            {
                ActivityCompat.requestPermissions(this,PERMISOS,MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

    }
}
