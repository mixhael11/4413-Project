package main.java.BEANS;

public class Paymentbean {
    private String CC;
    private String exp;
    private String sec;
    private String type;
    private String name;
    private String dbpath;
    private int amount;
    private String response;
    public Paymentbean(){}

    public void setCC(String CCnum){
        this.CC = CCnum;
    }

    public void setexp(String expString){
        this.exp = expString;
    }

    public void setsec(String secN){
        this.sec = secN;
    }

    public void settype(String type){
        this.type= type;
    }
    public void setname(String name){
        this.name = name;
    }

    public void setdbpath(String db){
        this.dbpath = db;
    }
    public void setamount(int amount){
        this.amount = amount;
    }
    public void setresponse(String resp){
        this.response = resp;
    }

    public String getCC(){
        return CC;
    }
    public String getexp(){
        return exp;
    }
    public String getsec(){
        return sec;
    }
    public String gettype(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getDbPath(){
        return dbpath;
    }
    public int getamount(){
        return amount;
    }
    public String getResponse(){
        return response;
    }
}

