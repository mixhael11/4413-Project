package main.java.payment;

import javax.servlet.annotation.WebServlet;

public class PaymentProxy implements Ipayment {
    private final Ipayment real;
    private String CCnumbers;
    private String expdate;
    private String SecCode;
    private String name;
    private String type;

    public PaymentProxy(String CCnum, String epdate, String ScCode, String name, String type, Ipayment real){
        
        this.CCnumbers = CCnum;
        this.expdate = epdate;
        this.SecCode = ScCode;
        this.name = name;
        this.real = real;
        this.type = type;
    }

    @Override
    public boolean validate(String CC, String SecCode, String Expiry) {
        if(isValidFormat(CC, SecCode, Expiry)){
            return real.validate(CC, SecCode, Expiry);
        }
        return false;

       
    }

    private boolean isValidFormat(String CC, String SecCode, String Expiry){
        if(CC != null && CC.length() == 16 && SecCode.length() == 3 && Expiry.length() == 4){
            return true;
        }
        if(this.type.equals("S3") && CC != null && CC.length() == 15 && SecCode.length() == 3 && Expiry.length() == 4){
            return true;
        }
        return false;
    }


}
