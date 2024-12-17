package servlets;

import db.DatabaseHandler;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.UserBean;
import controllers.AuthenticationController;

@WebServlet(name = "SigninServlet", urlPatterns = { "/SigninServlet" })
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int id = DatabaseHandler.getUserIdByUsername(username);
		String IDs = String.valueOf(id);
		System.out.println(IDs + " ID");

		boolean isValidUser = DatabaseHandler.validateLogin(username, password);

		if (isValidUser) {
			 request.setAttribute("statusMessage", "User loggedin successfully!");
             request.setAttribute("statusType", "success");
             
			 UserBean user = AuthenticationController.getUserinfo(username);
			
			 
			 String redirectURL = "http://localhost:8081/auctioning/UserServlet?firstName=" + user.getFirstName() + "&lastName=" + user.getLastName() 
					 + "&streetName=" + user.getStreetNumber() + "&streetNumber=" + user.getStreetName() 
					 + "&postalCode=" + user.getPostalCode() + "&city=" + user.getCity() + "&country=" + user.getCountry() + "&id=" + IDs;
			 
			 response.sendRedirect(redirectURL);
		} else {
			
			 request.setAttribute("statusMessage", "Invalid username or password");
             request.setAttribute("statusType", "error");
            
			request.getRequestDispatcher("signin.jsp").forward(request, response);
		}

	}
}
