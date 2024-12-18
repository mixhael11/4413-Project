package Servlets.Bidding;

import Auctions.*;
import Beans.UserBean;
import Servlets.Auctions.AuctionServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/BidServlet")
public class BidServlet extends HttpServlet {
    private AuctionServer auctionServer;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the AuctionServer from the servlet context
        auctionServer = (AuctionServer) getServletContext().getAttribute("auctionServer");
        if (auctionServer == null) {
            throw new ServletException("AuctionServer not initialized.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters

        int bidAmount = Integer.parseInt(request.getParameter("bidAmount"));
        String auctionId = request.getParameter("auctionId");
        int AID = Integer.valueOf(auctionId);
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        
        Auction auction = auctionServer.getAuctionById(AID);
        double currentPrice = auction.getCurrentPrice();

        if (bidAmount <= currentPrice && !auction.getAuctionType().equals("Dutch")) {
            request.setAttribute("errorMessage", "Bid must be higher than the current price.");
            request.getRequestDispatcher("auctiondetailsforward.jsp").forward(request, response);
            return;
        }

        // Handle Forward Auction bidding
        if (auction.getAuctionType().equals("Forward")) {
            // Place the bid (update auction with new bid)
            String first = user.getFirstName();
            System.out.println(first);
            auction.placeBid(bidAmount, user.getID(), user.getFirstName(), user); 

        
            request.setAttribute("auctionStatus", user.getAuctionStatus());
            request.setAttribute("firstName", user.getFirstName());
            request.getRequestDispatcher("/itemSearch.jsp").forward(request, response);
        } 
        // Handle Dutch Auction buy now
        else if (auction.getAuctionType().equals("Dutch")) {
            System.out.println("HERE!");
            auction.placeBid(bidAmount, user.getID(), user.getFirstName(), user);
            System.out.println("dispatch to BiddingEndServlet");
            request.getRequestDispatcher("/BiddingEndServlet").forward(request, response);
        }
    }
}
