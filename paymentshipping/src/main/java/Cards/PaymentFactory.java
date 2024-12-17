package main.java.Cards;

public class PaymentFactory {
    public Icard createPayment(String checked){
        Icard card= null;

        if(checked.equals("S1")){
            card = new Visa();
        } else if(checked.equals("S2")){
            card = new MasterCard();
        } else if (checked.equals("S3")){
            card = new AMEX();
        }
        return card;

  

}
}
