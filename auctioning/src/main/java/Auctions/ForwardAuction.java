package Auctions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Beans.UserBean;
import DatabaseWorker.*;

/**
 * Represents a forward auction.
 * A forward auction is a typical auction with a starting price.
 * Highest bidder wins when auction ends.
 * @author adamm
 *
 */
public class ForwardAuction extends Auction implements ObserverController{
	private List<Observer> observers = new ArrayList<>();
	// Constructor for loading existing auction
	public ForwardAuction(int id, String itemName, int initialBid, int auctionDurationMins, LocalDateTime startTime, LocalDateTime endTime, String description, int shippingprice) {
		super(id, itemName, initialBid, auctionDurationMins, startTime, endTime, description, shippingprice);
		this.setAuctionType("Forward");
		this.setEndTimeText(endTime);
	}
	// Constructor for creating new auction (no id)
	public ForwardAuction(String itemName, int initialBid, String description, int shippingprice, int auctionDurationMins) {
		super(itemName, initialBid, auctionDurationMins,description, shippingprice);
		this.setAuctionType("Forward");
		this.setEndTimeText(endTime);
	}
	
	@Override
	public boolean placeBid(int bidAmount, String bidderId, String name, UserBean User) {
		if (bidAmount <= currentBid) {
			System.out.println("Bid too low");
			return false;
		}
        
        String sql = "INSERT INTO bids (auction_id, bidder_id, bid_amount, bid_time, name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, bidderId);
            pstmt.setInt(3, bidAmount);
            pstmt.setString(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            pstmt.setString(5, name);
			pstmt.executeUpdate();

            // Update the current bid in the auction instance and database
            this.currentBid = bidAmount;//set this bid as current and this user as winning
            this.highestBidder = name;
			this.winnerid = Integer.valueOf(bidderId);
            updateCurrentBid(conn);//update starting bid in database
            System.out.println("Bid placed successfully! from" + name + " with bidder id " + bidderId);
			registerObserver(User);//register bidder
			notifyObserver();//notify all bidders
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	private void updateCurrentBid(Connection conn) throws SQLException {//update auction price 
        String updateSql = "UPDATE auctions SET start_price = ? WHERE id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setInt(1, currentBid);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        }
    }
	
	public String getHighestBidder() {
		return highestBidder;
	}

	@Override
	public int getCurrentPrice() {
		return getCurrentBid();
	}

	@Override
	public double getStartPrice() {
		return currentBid;
	}

	@Override
	public double getReservePrice() {
		return 0;
	}

	@Override
	public int getDecrementAmount() {
		return 0;
	}

	@Override
	public int getDecrementIntervalMins() {
		return 0;
	}
	@Override//add user to observers
	public void registerObserver(Observer obs) {
		System.out.println("added " + obs + " to observerlist");
		observers.add(obs);
	}
	@Override///remove user to observers
	public void removeObserver(Observer obs) {
		observers.remove(obs);
	}
	@Override//notifyall
	public void notifyObserver() {
		String auctionStatus = "New bid placed: $" + currentBid + " by " + highestBidder +  " on " + itemName;
        for (Observer observer : observers) {
            observer.update(auctionStatus);
        }
	}

}
