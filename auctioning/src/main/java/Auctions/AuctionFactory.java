package Auctions;
import java.util.HashMap;

//Factory class to create auctions
public class AuctionFactory {
    private HashMap<String, String> auctions = new HashMap<String, String>();
    public AuctionFactory(){
        auctions.put("Forward", "ForwardAuction");
        auctions.put("Dutch", "DutchAuction");
    }

    public Auction createAuction(String auctiontype, String itemName, int initialBid, String description, int shippingprice, Object... params){
            String type = auctions.get(auctiontype);
            System.out.println("Parameters Length: " + params.length);
            if (type.equals("ForwardAuction")) {
                if (params.length != 1 || !(params[0] instanceof Integer)) {
                    throw new IllegalArgumentException("ForwardAuction requires 1 parameter: Duration");
                }
                return new ForwardAuction(itemName, initialBid, description, shippingprice, (Integer) params[0]);
    
            } else if (type.equals("DutchAuction")) {
                if (params.length != 4 || 
            !(params[0] instanceof Integer) || 
            !(params[1] instanceof Integer) || 
            !(params[2] instanceof Integer) || 
            !(params[3] instanceof Integer)) {
            throw new IllegalArgumentException("DutchAuction requires 4 parameters: Duration, Reserve Price, Decrement Amount, and Decrement Interval");
        }

                return new DutchAuction(itemName, initialBid, description, shippingprice, (Integer) params[0], (Integer) params[1], (Integer) params[2], (Integer) params[3]);
    
            } else {
                throw new IllegalArgumentException("Invalid Auction type: " + type);
            }


    }
    



}
