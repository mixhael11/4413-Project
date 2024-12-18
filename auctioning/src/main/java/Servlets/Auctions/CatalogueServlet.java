package Servlets.Auctions;

import Auctions.*;
import Beans.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/CatalogueServlet")
public class CatalogueServlet extends HttpServlet {

    private AuctionServer auctionServer;

    @Override
    public void init() throws ServletException {
        super.init();
        // Retrieve the shared AuctionServer instance from ServletContext
        auctionServer = (AuctionServer) getServletContext().getAttribute("auctionServer");
        if (auctionServer == null) {
            throw new ServletException("AuctionServer not initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Auction> filteredAuctions = auctionServer.searchAuctions(query);
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        request.setAttribute("auctions", filteredAuctions);
        request.setAttribute("query", query);
        request.setAttribute("firstName", user.getFirstName());

        request.getRequestDispatcher("/catalogue.jsp").forward(request, response);
        
}
}
