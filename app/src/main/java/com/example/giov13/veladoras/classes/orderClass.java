package com.example.giov13.veladoras.classes;

public class orderClass {
   private String nameOrder, Direction, candleTypes, candleEssence, Types,Date,Esoterica;
   private String quantity;
   private String sentence;
   private boolean check;

    //Constructorres
    public  orderClass() { }

    public orderClass(String nameOrder, String Direction, String candleTypes, String quantity,String candleEssence, String Types, String date,String esoterica) {
        setNameOrder(nameOrder);
        setDirection(Direction);
        setCandleTypes(candleTypes);
        setQuantity(quantity);
        setCandleEssence(candleEssence);
        setTypes(Types);
        setDate(date);
        setEsoterica(esoterica);
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCandleTypes(String candleTypes) {
        this.candleTypes = candleTypes;
    }

    public void setCandleEssence(String candleEssence) {
        this.candleEssence = candleEssence;
    }

    public void setTypes(String types) {
        Types = types;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setEsoterica(String esoterica){Esoterica=esoterica;}

    public  void  setSelected (boolean check){this.check=check;}

    public boolean getSelected(){return check; }

    public String getNameOrder() {
        return nameOrder;
    }

    public String getDirection() {
        return Direction;
    }

    public String getCandleTypes() {
        return candleTypes;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCandleEssence() {
        return candleEssence;
    }

    public String getTypes() {
        return Types;
    }

    public String getDate() {
        return Date;
    }
    public String getEsoterica(){
        return Esoterica;
    }

    public String toStringM()
    {
        sentence="* "+getQuantity()+" de "+getCandleTypes()+" "+getEsoterica()+getCandleEssence()+" "+getTypes()+"\n";
        return sentence;
    }
}

