package Servlets.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import Beans.UserBean;
@WebServlet("/UserServlet")
    
public class UserServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String streetName = request.getParameter("streetName");
        String streetNumber = request.getParameter("streetNumber");
        String postalCode = request.getParameter("postalCode");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String ID = request.getParameter("id");
        UserBean user = new UserBean(firstName, lastName, streetName, streetNumber, postalCode, city, country, ID);

        HttpSession session = request.getSession(true);
        System.out.println(session.getId());
        session.setAttribute("user", user);
        request.setAttribute("firstName", firstName);
        String currentPage = request.getRequestURI();
        session.setAttribute("previous", currentPage);
        request.getRequestDispatcher("/itemSearch.jsp").forward(request, response);
    }
    
}
