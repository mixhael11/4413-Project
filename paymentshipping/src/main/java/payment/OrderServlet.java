
package main.java.payment;
import java.io.IOException;
import java.io.PrintWriter;
import main.java.BEANS.Itembean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class MyServlet1
 */
@WebServlet("/OrderServlet")

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			             throws ServletException, IOException {
	     try{ 
			response.setContentType("text/html");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String streetName = request.getParameter("streetName");
            String streetNumber = request.getParameter("streetNumber");
            String postalCode = request.getParameter("postalCode");
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            String ID = request.getParameter("id");
            String firstname = request.getParameter("firstName");
            String totalPaid = request.getParameter("totalPaid");
            Itembean bean = new Itembean();

            bean.setFirstName(firstname);
            bean.setLastName(lastName);
            bean.setStreet(streetName);
            bean.setStreetNumber(streetNumber);
            bean.setCity(city);
            bean.setCountry(country);
            bean.setPostalCode(postalCode);
            bean.setTotalPaid(totalPaid);
           
            request.setAttribute("firstName", firstname);
            request.setAttribute("lastName", lastName);
            request.setAttribute("street", streetName);
            request.setAttribute("streetNumber", streetNumber);
            request.setAttribute("city", city);
            request.setAttribute("country", country);
            request.setAttribute("postalCode", postalCode);
            request.setAttribute("totalPaid", totalPaid);

            HttpSession session = request.getSession();
            session.setAttribute("itemBean", bean);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Payment.jsp");
            dispatcher.forward(request, response);

			
			
	       }catch(Exception exp){
	          System.out.println(exp);}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
