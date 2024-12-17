package main.java.Cards;

public class Visa implements Icard{

    @Override
    public String dopay(int amount) {
       String w = "simulate payed with VISA!";
       return w;
    }

}
