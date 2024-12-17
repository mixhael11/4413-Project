package main.java.Cards;

public class AMEX implements Icard{

    @Override
    public String dopay(int amount) {
        String w = "simulate payed with AMEX!";
       return w;
    }

}
