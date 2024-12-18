package Servlets.Auctions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Auctions.Auction;
import Auctions.DutchAuction;
import Auctions.ForwardAuction;
import Auctions.Observer;
import Beans.UserBean;
import DatabaseWorker.*;


/**
 * Handles auctions in the app.
 * Allows for adding, removing and updating auctions.
 *
 */
public class AuctionServer{
    private static AuctionServer instance;
    private Map<Integer, Auction> auctions;
    private ScheduledExecutorService scheduler;
    

    private AuctionServer() {
        this.auctions = new HashMap<>();
    }
    
    public static AuctionServer getInstance(){
        if(instance == null){
            instance = new AuctionServer();
        }
        return instance;
    }
//      
    
    
    public void saveAuction(Auction auction) {
        String sql = "INSERT INTO auctions (item_name, auction_duration, start_price, reserve_price, decrement_amount, decrement_interval_mins, start_time, end_time, auction_type, description, shipping_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, auction.getItemName());
            pstmt.setLong(2, auction.getRemainingTime());
            pstmt.setDouble(3, auction.getStartPrice());
            pstmt.setDouble(4, auction.getReservePrice());
            pstmt.setInt(5, auction.getDecrementAmount());
            pstmt.setInt(6, auction.getDecrementIntervalMins());
            pstmt.setString(7, auction.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setString(8, auction.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setString(9, auction instanceof DutchAuction ? "Dutch" : "Forward");
            pstmt.setString(10, auction.getDescription());
            pstmt.setDouble(11, auction.getShippingprice());


            pstmt.executeUpdate();
            System.out.println("Auction saved to database: " + auction.getItemName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAuctions() {
        String sql = "SELECT * FROM auctions";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String itemName = rs.getString("item_name");
                int startPrice = rs.getInt("start_price");
                int reservePrice = rs.getInt("reserve_price");
                int decrementAmount = rs.getInt("decrement_amount");
                int decrementIntervalMins = rs.getInt("decrement_interval_mins");
                int auctionDurationMins = rs.getInt("auction_duration");
                LocalDateTime startTime = LocalDateTime.parse(rs.getString("start_time"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime endTime = LocalDateTime.parse(rs.getString("end_time"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String auctionType = rs.getString("auction_type");
                String description = rs.getString("description");
                int shippingprice = rs.getInt("shipping_price");

                Auction auction;
                if ("Dutch".equalsIgnoreCase(auctionType)) {
                    auction = new DutchAuction(id, itemName, startPrice, reservePrice, decrementAmount, auctionDurationMins, decrementIntervalMins, startTime, endTime, description, shippingprice);
                } else {
                    auction = new ForwardAuction(id, itemName, startPrice, auctionDurationMins, startTime, endTime, description, shippingprice);
                }

                addAuction(auction);
                System.out.println("Loaded auction: " + itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Auction> searchAuctions(String keyword) {
        List<Auction> results = new ArrayList<>();
        String sql = "SELECT * FROM auctions WHERE item_name LIKE ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Set the search keyword with '%' wildcards for partial match
            pstmt.setString(1, "%" + keyword + "%");
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String itemName = rs.getString("item_name");
                    int startPrice = rs.getInt("start_price");
                    int reservePrice = rs.getInt("reserve_price");
                    int decrementAmount = rs.getInt("decrement_amount");
                    int decrementIntervalMins = rs.getInt("decrement_interval_mins");
                    int auctionDurationMins = rs.getInt("auction_duration");
                    LocalDateTime startTime = LocalDateTime.parse(rs.getString("start_time"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime endTime = LocalDateTime.parse(rs.getString("end_time"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String auctionType = rs.getString("auction_type");
                    String description = rs.getString("description");
                    int shippingprice = rs.getInt("shipping_price");
    
                    // Create an auction object based on the type of auction
                    Auction auction;
                    if ("Dutch".equalsIgnoreCase(auctionType)) {
                        auction = new DutchAuction(id, itemName, startPrice, reservePrice, decrementAmount, auctionDurationMins, decrementIntervalMins, startTime, endTime, description, shippingprice);
                    } else {
                        auction = new ForwardAuction(id, itemName, startPrice, auctionDurationMins, startTime, endTime, description, shippingprice);
                    }
    
                    results.add(auction); // Add auction to the result list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return results; // Return the list of auctions matching the keyword
    }
    
    
    public boolean deleteAllAuctions() {
        String sql = "DELETE FROM auctions"; // SQL query to delete all rows

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int rowsDeleted = pstmt.executeUpdate(); // Execute the delete query

            System.out.println(rowsDeleted + " auctions deleted.");
            return true; // Return true if successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    
    public void addAuction(Auction auction) {
        System.out.println("added auction: " + auction.getItemName() + " with id " + auction.getId());
        auctions.put(auction.getId(), auction);
    }

    public boolean placeBid(int itemId, int bidAmount, String bidderId, String name, UserBean user) {
        Auction auction = auctions.get(itemId);

        if (auction != null && !auction.isEnded()) {
            boolean bidAccepted = auction.placeBid(bidAmount, bidderId, name, user);

            if (auction.isExpired() || auction.isEnded()) {
                auction.endAuction();
                System.out.println("Auction ended for item " + itemId);
            }

            return bidAccepted;
        } else {
            System.out.println("Auction not found or already ended for item " + itemId);
            return false;
        }
    }
    

    public void checkAndEndAuctions() {
        for (Auction auction : auctions.values()) {
            if (auction.isExpired() && !auction.isEnded()) {
                auction.endAuction();
                auction.delete();
                System.out.println("Auction for item " + auction.getId() + " has ended."); // Close all auction that have expired time-wise
            } else if (auction instanceof DutchAuction) {
                    auction.setCurrentBid(auction.getCurrentPrice());
                
            }
        }
    }

    public void removeAuction(int key){
        auctions.remove(key);
    }

	public Map<Integer, Auction> getAuctions() {
		return auctions;
	}
    public Auction getAuctionById(int id) {
       return auctions.get(id);
    }
    public void printAllAuctions() {
        System.out.println("All Auctions:");
        for (Map.Entry<Integer, Auction> entry : auctions.entrySet()) {
            Integer id = entry.getKey();
            Auction auction = entry.getValue();
            System.out.println("ID: " + id + " => " + auction.getItemName()); // This will call the toString() method of Auction
        }
    }
	
	public void stop() {
		scheduler.shutdown();
	}
}

   
