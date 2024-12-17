package main.java.payment;


public interface Ipayment {
    boolean validate(String CC, String SecCode, String expiry);


}
