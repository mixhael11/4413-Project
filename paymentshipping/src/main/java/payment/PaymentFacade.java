package main.java.payment;

import main.java.BEANS.Itembean;
import main.java.BEANS.Paymentbean;
import main.java.Cards.Icard;
import main.java.Cards.PaymentFactory;

public class PaymentFacade {
    private String response;
    PaymentFacade(){}
    public String getResponse(){
        return response;
    }

    public boolean doit(Paymentbean bean){
        RealPayment real = new RealPayment();
        real.setdbpath(bean.getDbPath());
        Ipayment paymentProxy = new PaymentProxy(bean.getCC(), bean.getexp(), bean.getsec(), bean.getName(), bean.gettype(), real);
        boolean isValid = paymentProxy.validate(bean.getCC(), bean.getsec(), bean.getexp());
        if(isValid){
            PaymentFactory factory = new PaymentFactory();
            Icard card = factory.createPayment(bean.gettype());
            String response = card.dopay(bean.getamount());
            this.response = response;
            PaymentWriter pay = new PaymentWriter();
            pay.setPath(bean.getDbPath());
            pay.write(bean.getName(), bean.getCC(), response);
            pay.fetchAllRecords();
            
            
            return true;
        }
        else{
            return false;
        }
        


    }

}
