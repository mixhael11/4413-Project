package DatabaseWorker;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Servlets.Auctions.AuctionServer;

import java.io.File;

@WebListener
public class UnifiedInitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Database setup
        String dbPath = sce.getServletContext().getRealPath("/WEB-INF/auction.db");

        System.out.println("Database Path: " + dbPath);

        try {
            DatabaseConnection.initialize(dbPath);
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
            return;  // Stop initialization if DB connection fails
        }
        
        try {
            populateDatabase p = new populateDatabase();
            System.out.println("populated");
        } catch (Exception e) {
            System.err.println("Error populating database: " + e.getMessage());
            e.printStackTrace();
            return;  // Stop further initialization
        }    

        // AuctionServer setup
        AuctionServer auctionServer = AuctionServer.getInstance();
        System.out.println("Auction server created ");

        auctionServer.loadAuctions();
        System.out.println("Auction server loaded");
        sce.getServletContext().setAttribute("auctionServer", auctionServer);

        System.out.println("AuctionServer initialized and stored in ServletContext.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application context destroyed.");
    }
}
