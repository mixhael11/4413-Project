package Servlets.Payment;

import Auctions.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import Beans.UserBean;
import Servlets.Auctions.AuctionServer;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet{
    private AuctionServer auctionServer;

    @Override
    public void init() throws ServletException {
        super.init();
        auctionServer = (AuctionServer) getServletContext().getAttribute("auctionServer");
        if (auctionServer == null) {
            throw new ServletException("AuctionServer not initialized.");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        Auction auction = auctionServer.getAuctionById(auctionId);
        String s = auction.getHighestBidder();
        String ds = String.valueOf(auction.getCurrentBid());
        auctionServer.removeAuction(auctionId);
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        if(auction.getWinnerId() == Integer.valueOf(user.getID())){
            String redirect = "http://localhost:8083/payment/OrderServlet?firstName=" + user.getFirstName() + "&lastName=" + user.getLastName() 
            + "&streetName=" + user.getStreetNumber() + "&streetNumber=" + user.getStreetName() 
            + "&postalCode=" + user.getPostalCode() + "&city=" + user.getCity() + "&country=" + user.getCountry() + "&totalPaid=" + ds;
           response.sendRedirect(redirect);
        }else{
            System.out.println("MASSIVE L");
        }
    
    }

}
