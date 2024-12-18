package Servlets.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import Beans.UserBean;



@WebServlet("/setNameServlet")
    
public class setNameServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String name = user.getFirstName();
        System.out.println(name + " HELLO");
        request.setAttribute("firstName", name);
        request.getRequestDispatcher("/itemSearch.jsp").forward(request, response);
    }
    
}
