package DatabaseWorker;
import Auctions.*;
import Servlets.Auctions.AuctionServer;

import java.nio.channels.AcceptPendingException;

/**
 * Run this file to populate the database with some example auctions
 * In order to add new auctions, follow this format:
 * DutchAuction name = new DutchAuction(String itemName, int startPrice, int reservePrice, int decrementAmount, int auctionDurationMins, int decrementIntervalMins)
 * ForwardAuction name = new ForwardAuction(String itemName, int initialBid, int auctionDurationMins)
 * And then add this auction to the database by "performing server.saveAuction(name)"
 */
public class populateDatabase {
		public populateDatabase(){
			AuctionServer server = AuctionServer.getInstance();
			AuctionFactory factory = new AuctionFactory();
			server.loadAuctions();
				Auction newForwardAuction = factory.createAuction("Forward", "Phone", 500,  "Iphone 16 PRO MAX OMEGA", 10, 1);
		        Auction dutchAuction = factory.createAuction("Dutch", "Computer", 1000, "4080!", 25, 500, 10, 30, 1);
		        Auction newForwardAuction2 = factory.createAuction("Forward", "Car", 2000, "honda civic", 50, 1);
		        Auction dutchAuction2 = factory.createAuction("Dutch", "Bike", 300, "dirt bike", 25, 200, 10, 20, 10);
		        Auction newForwardAuction3 = factory.createAuction("Forward", "TV", 1000, "65 inch", 25, 20);
		        Auction dutchAuction3 = factory.createAuction("Dutch", "Jacket", 200, "OVO jacket", 150, 50, 25, 20, 10);
		        Auction newForwardAuction4 = factory.createAuction("Forward", "Car", 200000, "Porshe", 5000, 2);
				//DECLARE AUCTION VARIABLES ABOVE THIS LINE
		        
		        
				server.saveAuction(newForwardAuction);
				server.saveAuction(dutchAuction);
				server.saveAuction(newForwardAuction2);
				server.saveAuction(dutchAuction2);
				server.saveAuction(newForwardAuction3);
				server.saveAuction(dutchAuction3);
				server.saveAuction(newForwardAuction4);
		}
}
