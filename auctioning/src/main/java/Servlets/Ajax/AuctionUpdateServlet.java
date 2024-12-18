package Servlets.Ajax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import Auctions.*;
import Beans.UserBean;
import Servlets.Auctions.AuctionServer;

/**
 * Servlet to provide filtered auction data in JSON format.
 */
@WebServlet("/AuctionUpdateServlet")
public class AuctionUpdateServlet extends HttpServlet {

    private AuctionServer auctionServer;

    @Override
    public void init() throws ServletException {
        super.init();
        auctionServer = (AuctionServer) getServletContext().getAttribute("auctionServer");
        if (auctionServer == null) {
            throw new ServletException("AuctionServer not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
             HttpSession session = request.getSession(false);
            String auctionStatus = "Unknown"; 
            if (session != null) {
                UserBean userBean = (UserBean) session.getAttribute("user");
                if (userBean != null) {
                    auctionStatus = userBean.getAuctionStatus();
                }
            }
            List<Auction> activeAuctions = new ArrayList<>();
            List<Map<String, String>> notifications = new ArrayList<>();
        
            auctionServer.checkAndEndAuctions(); // End expired auctions
        
            // Use the filtered auctions instead of all auctions
            List<Auction> filteredAuctions = auctionServer.searchAuctions(query);
            Map<Integer, Auction> allAuctions = auctionServer.getAuctions();
            List<Auction> endedAuction = new ArrayList<Auction>();
            for (Auction auction : filteredAuctions) {
                if (!auction.isEnded() && !auction.isExpired()) {
                    auction.getTimeRemaining();
                    if(auction instanceof DutchAuction){
                        DutchAuction dutchAuction = (DutchAuction) auction;
                        int h = auction.getCurrentPrice();
                    }
                    activeAuctions.add(auction);
                } 
            }

            for (Auction auction : allAuctions.values()) {
                if (auction.isEnded() && auction.getHighestBidder() != null && !auction.notified) {
                    // Add notification for ended auction
                    Map<String, String> notification = new HashMap<>();
                    notification.put("auctionId", String.valueOf(auction.getId()));
                    notification.put("bidderId", auction.getHighestBidder());
                    notification.put("itemName", auction.getItemName());
                    notifications.add(notification);
                    auction.notified = true;
                    endedAuction.add(auction);
                   
                }
            }
            
        
            // Create response object
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("activeAuctions", activeAuctions);
            responseData.put("notifications", notifications);
            responseData.put("auctionStatus", auctionStatus);
            if (!endedAuction.isEmpty()) {
                Auction wonAuction = endedAuction.get(0);
                responseData.put("auctionEnded", true);
                responseData.put("auctionId", wonAuction.getId());
            } 
            
        
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
        
            String json = gson.toJson(responseData);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal Server Error\"}");
        }
}
}
