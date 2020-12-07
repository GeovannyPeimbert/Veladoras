package com.example.giov13.veladoras.classes;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class templatePDF {
    private Context contex;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    //Diseños para los distintos textos
    /*El primer argumento es para establecer el tipo de letra
    * el segundo para establecer el tamaño
    * el tercero para el estilo
    * el cuarto el color*/
    private Font fTtitle = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
    private Font fSubTtitle = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD, BaseColor.RED);

    public templatePDF (Context context)
    {
            this.contex=context;
    }
    public void openPDF()
    {
        createPDF();
        try
        {
            //Creamos el documento, pageSize es para especificar el tamaño de la hoja
            document=new Document(PageSize.A4);
            //Decimos que el PDF pueda editarse
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            //Abrimos el documento
            document.open();

        }catch (Exception e)
        {
            Log.e("openPDF",e.toString());
        }
    }
    public void createPDF()
    {
        //Creamos la carpeta donde se almacenarán los PDF
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists())
            folder.mkdir();
        pdfFile=new File(folder.getAbsolutePath(),"Pedidos.pdf");
        try
        {
            pdfFile.createNewFile();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        //Se especifica donde se guardaran los pdf creados y el nombre que éstos llevarán

    }
    public void closeDocument()
    {
        document.close();
    }
    public void addMetaData(String title, String subject,String author)
    {
        //Agregamos los meta datos al documento
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }
    public void addTitles(String title,String subject,String date)
    {
        try
        {
        //Creamos un parrafo
        paragraph=new Paragraph();
        //Agregamos los parrafos
        childP(new Paragraph(title,fTtitle));
        childP(new Paragraph(subject,fSubTtitle));
        childP(new Paragraph("Generado: "+date,fHighText));
        paragraph.setSpacingAfter(30);
        document.add(paragraph);
        }catch (Exception e)
        {
            Log.e("addTitles",e.toString());
        }
    }
    private void childP(Paragraph childParagraph)
    {
        //Establecemos que, todos los parrafos hijos, se alinien al centro del párrafo padre
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        //Asociamos el parrafo hijo, al párrafo padre
        paragraph.add(childParagraph);
    }
    //Método para agregar una tabla, con los datos de las ordenes
    public void addTable(String[] header, ArrayList<String[]> orders)
    {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            //Se le indica a la tabla, cuantas columnas tendrá
            PdfPTable pdfPTable = new PdfPTable(header.length);
            //Se establece el ancho de la tabla
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                //Creamos las columnas y les establecemos un texto
                pdfPCell = new PdfPCell(new Phrase(header[indexC], fSubTtitle));
                indexC++;
                //Establecemos la posicion del texto dentro de la columna y el color del campo
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.PINK);
                pdfPTable.addCell(pdfPCell);
            }//Se agregan los valores a la tabla
            for (int indexR = 0; indexR < orders.size(); indexR++) {
                String[] row = orders.get(indexR);
                for (int indexCell = 0; indexCell < header.length; indexCell++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexCell]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //Para indicar ancho de tabla
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);

                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e)
        {
            Log.e("addTable",e.toString());
        }
    }
}
