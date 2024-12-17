package servlets;

import controllers.AuthenticationController;
import db.DatabaseHandler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.UserBean;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet(name = "SignupServlet", urlPatterns = { "/signup" })
public class SignupServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		UserBean user = new UserBean();
        
        // Set the properties using the request parameters
		String usernamer = request.getParameter("username");
		int id = DatabaseHandler.getUserIdByUsername(usernamer);
		String ID = String.valueOf(id);
		System.out.println(ID);

        user.setUsername(request.getParameter("username"));
		user.setID(ID);
		System.out.println(user.getID());
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setStreetName(request.getParameter("streetAddress"));
        user.setStreetNumber(request.getParameter("streetNumber"));
        user.setPostalCode(request.getParameter("postalCode"));
        user.setCity(request.getParameter("city"));
        user.setCountry(request.getParameter("country"));

		 if (AuthenticationController.usernameExists(username)) {
	            
	            request.setAttribute("statusMessage", "Username is already taken. Please choose a different username.");
	            request.setAttribute("statusType", "error");
	            request.getRequestDispatcher("signup.jsp").forward(request, response);
	        } else {
	          
	            boolean userCreated = AuthenticationController.createUserAccount(username, user.getPassword(), user.getFirstName(), user.getLastName(),
	                    user.getStreetName(), user.getStreetNumber(), user.getPostalCode(), user.getCity(), user.getCountry());

	            if (userCreated) {
	                request.setAttribute("statusMessage", "User created successfully!");
	                request.setAttribute("statusType", "success");
					HttpSession session = request.getSession();
            		session.setAttribute("user", user);

                // Redirect to the SigninServlet or another servlet
	                request.getRequestDispatcher("welcome.jsp").forward(request, response);
	            } else {
	                request.setAttribute("statusMessage", "Error creating user. Please try again.");
	                request.setAttribute("statusType", "error");
	                request.getRequestDispatcher("signup.jsp").forward(request, response);
	            }
	        }

	    
	       
	}
}
