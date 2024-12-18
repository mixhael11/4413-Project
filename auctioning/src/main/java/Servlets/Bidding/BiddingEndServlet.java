package Servlets.Bidding;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import java.io.IOException;
import Auctions.*;
import Beans.UserBean;
import Servlets.Auctions.AuctionServer;

@WebServlet("/BiddingEndServlet")
public class BiddingEndServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the auction object that was forwarded
        String aID =  request.getParameter("auctionId");
        int ID = Integer.valueOf(aID);
        System.out.println("GOT " + ID);
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        Auction auction = auctionServer.getAuctionById(ID);

       if (auction != null) {
            System.out.println(user.getID() + "user id");
            int id = Integer.valueOf(user.getID());
            System.out.println(user.getFirstName() + " first name");
            System.out.println(auction.getWinnerId());
            System.out.println(id);
            System.out.println(auction.getHighestBidder() + " winner name");
                    
            if (user != null && auction.getWinnerId() == (id)) {
                request.setAttribute("auction", auction);  // Set auction details for winner
                System.out.println("dispatch to biddingend.jsp");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/biddingend.jsp");  // Forward to "Pay Now" page
                dispatcher.forward(request, response);
            } else {
                String previousPage = (String) request.getSession().getAttribute("previousPage");
                if (previousPage != null) {
                    response.sendRedirect(previousPage);  // Redirect to the previous page
                } else {
                    request.setAttribute("firstName", user.getFirstName());
                    // If there's no previous page, redirect to a default page (e.g., home or auction list)
                    request.getRequestDispatcher("/itemSearch.jsp").forward(request, response);
                }
            }
        } else {
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
