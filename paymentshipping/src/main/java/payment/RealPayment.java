package main.java.payment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import main.java.BEANS.Paymentbean;



public class RealPayment  implements Ipayment{
    private String url = "jdbc:sqlite:";
    int Expiryfound = 0;
    int Securityfound = 0;
  
    public void setdbpath(String dbpath){
        this.url = url + dbpath + "/Payment.db";
    }


    @Override
    public boolean validate(String CC, String SecCode, String Expiry) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        // TODO Auto-generated method stub
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            stmt = con.createStatement();
            String sql = "Select Expiry, Security from payment where CardNumber = " + CC;
            rs = stmt.executeQuery(sql);
            while(rs.next()){

                
                Expiryfound = rs.getInt("Expiry");
                Securityfound = rs.getInt("Security");
            }
        }catch(Exception e){
            e.printStackTrace();
        }  finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(stmt != null){
                    stmt.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        
        if(Expiryfound == Integer.parseInt(Expiry) && Securityfound == Integer.parseInt(SecCode)){
            return true;
        }else{
            return false;
        }
    }

}
