package Servlets.Bidding;

import Auctions.*;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

import Servlets.Auctions.AuctionServer;


@WebServlet("/InfoServlet")
public class InfoServlet extends HttpServlet {
    private AuctionServer auctionServer;

    @Override
    public void init() throws ServletException {
        super.init();
        auctionServer = (AuctionServer) getServletContext().getAttribute("auctionServer");
        if (auctionServer == null) {
            throw new ServletException("AuctionServer not initialized.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int auctionId = Integer.parseInt(request.getParameter("selectedAuctionId"));
        System.out.println(auctionId + " ID");
        Auction auction = auctionServer.getAuctionById(auctionId);
        String auctionType = auction.getAuctionType();
        
        // Set auction details as request attributes
        request.setAttribute("auction", auction);
        
        if (auctionType.equals("Forward")) {
    

            request.getRequestDispatcher("auctiondetailsforward.jsp").forward(request, response);
        } else if (auctionType.equals("Dutch")) {
            
            request.getRequestDispatcher("auctiondetailsdutch.jsp").forward(request, response);
        }
    }
}


    
